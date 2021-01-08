package com.qr.customize;

import com.qr.context.ConvergeContext;
import com.qr.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 *  加载第三方包
 * @Author wd
 * @since 17:58 2020/9/21
 **/
@Slf4j
public class ModuleClassLoader extends URLClassLoader {

	/**
	 * 属于本类加载器加载的jar包
	 */
	private JarFile jarFile;

	//保存已经加载过的Class对象
	private Map<String,Class> cacheClassMap = new HashMap<>();

	//保存本类加载器加载的class字节码
	private Map<String,byte[]> classBytesMap = new HashMap<>();

	//需要注册的spring bean的name集合
	private List<String> registeredBean = new ArrayList<>();

	//构造
	public ModuleClassLoader(URL[] urls, ClassLoader parent) throws IOException {
		super(urls, parent);
		URL url = urls[0];
		String path = url.getPath();

		jarFile = new JarFile(path);
		//初始化类加载器执行类加载
		init();
	}

	//重写loadClass方法
	//改写loadClass方式
	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		if(findLoadedClass(name)==null){
			return super.loadClass(name);
		}else{
			return cacheClassMap.get(name);
		}

	}

	/**
	 * 方法描述 初始化类加载器，保存字节码
	 * @method init
	 */
	private void init() {
		//解析jar包每一项
		Enumeration<JarEntry> en = jarFile.entries();
		InputStream input = null;
		ByteArrayOutputStream baos = null;
		try{
			while (en.hasMoreElements()) {
				JarEntry je = en.nextElement();
				String name = je.getName();
				//这里添加了路径扫描限制
				if (name.endsWith(".class")) {
					String className = name.replace(".class", "").replaceAll("/", ".");
					input = jarFile.getInputStream(je);
					baos = new ByteArrayOutputStream();
					int bufferSize = 4096;
					byte[] buffer = new byte[bufferSize];
					int bytesNumRead = 0;
					while ((bytesNumRead = input.read(buffer)) != -1) {
						baos.write(buffer, 0, bytesNumRead);
					}
					byte[] classBytes = baos.toByteArray();
					classBytesMap.put(className,classBytes);
				}
			}
		} catch (IOException e) {
			log.error("解析第三方jar异常!{}",e);
		} finally {
			if(input!=null){
				try {
					input.close();
				} catch (IOException e) {
					log.error("解析jar 关流失败!{}",e);
				}
			}
			if (baos != null){
				try {
					baos.close();
				} catch (IOException e) {
					log.error("解析jar 关流失败!{}",e);
				}
			}
		}


		//将jar中的每一个class字节码进行Class载入
		for (Map.Entry<String, byte[]> entry : classBytesMap.entrySet()) {
			String key = entry.getKey();
			Class<?> aClass = null;
			try {
				aClass = loadClass(key);
			} catch (ClassNotFoundException e) {
				log.error("载入class文件异常!{}",e);
			}
			cacheClassMap.put(key,aClass);
		}

	}

	/**
	 * 方法描述 初始化spring bean
	 * @method initBean
	 */
	public void initBean(String jarPath){
		//注册bean
		for (Map.Entry<String, Class> entry : cacheClassMap.entrySet()) {
			String className = entry.getKey();
			Class<?> cla = entry.getValue();
			if(isSpringBeanClass(cla)){
				BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(cla);
				BeanDefinition beanDefinition = beanDefinitionBuilder.getRawBeanDefinition();
				//设置当前bean定义对象是单例的
				beanDefinition.setScope("singleton");

				//将变量首字母置小写
				String beanName = StringUtils.uncapitalize(className);

				beanName =  beanName.substring(beanName.lastIndexOf(".")+1);
				beanName = StringUtils.uncapitalize(beanName);
				BeanDefinitionRegistry beanDefinitionRegistry = (BeanDefinitionRegistry) SpringContextUtil.getApplicationContext().getAutowireCapableBeanFactory();
				beanDefinitionRegistry.registerBeanDefinition(beanName,beanDefinition);
				registeredBean.add(beanName);
				log.info("注册bean:"+beanName);
			}
		}

		ConvergeContext.getThirdRegisteredMap().put(jarPath ,registeredBean);
	}

	//获取当前类加载器注册的bean
	//在移除当前类加载器的时候需要手动删除这些注册的bean
	public List<String> getRegisteredBean() {
		return registeredBean;
	}

	//删除第三方jar 注册bean
	public void deleteBean(String jarPath){
		List<String> beanList = ConvergeContext.getThirdRegisteredMap().get(jarPath);
		BeanDefinitionRegistry beanDefinitionRegistry = (BeanDefinitionRegistry) SpringContextUtil.getApplicationContext().getAutowireCapableBeanFactory();
		if (beanList != null && !beanList.isEmpty()) {
			beanList.forEach(beanName -> {
				beanDefinitionRegistry.removeBeanDefinition(beanName);
			});
		}
	}


	/**
	 * 方法描述 判断class对象是否带有spring的注解
	 * @method isSpringBeanClass
	 * @param cla jar中的每一个class
	 * @return true 是spring bean   false 不是spring bean
	 */
	public boolean isSpringBeanClass(Class<?> cla){
		if(cla==null){
			return false;
		}
		//是否是接口
		if(cla.isInterface()){
			return false;
		}

		//是否是抽象类
		if( Modifier.isAbstract(cla.getModifiers())){
			return false;
		}

//		if(cla.getAnnotation(Component.class)!=null){
//			return true;
//		}
//		if(cla.getAnnotation(Repository.class)!=null){
//			return true;
//		}
//		if(cla.getAnnotation(Service.class)!=null){
//			return true;
//		}

		if(cla.getAnnotation(Wrapper.class)!=null){
			return true;
		}

		return false;
	}

}
