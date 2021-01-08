import com.qr.customize.ModuleClassLoader;
import lombok.SneakyThrows;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class LoadExpJarTest {


	@SneakyThrows
	@Test
	public void test(){
//		new URLClassLoader(new URL[]{new URL(softPath)},Thread.currentThread().getContextClassLoader());
		ModuleClassLoader moduleClassLoader = null;
		try {
			moduleClassLoader = new ModuleClassLoader(new URL[]{new URL("file:C:/Users/Administrator/Desktop/admin-3.0.0.5.jar")}, Thread.currentThread().getContextClassLoader());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		System.err.println(moduleClassLoader.getRegisteredBean());
	}

}
