package com.qr.init;

import com.alibaba.fastjson.JSON;
import com.qr.customize.ModuleClassLoader;
import com.qr.entity.RuleInfos;
import com.qr.enums.ConvergenceContentEnum;
import com.qr.enums.RuleTypeEnum;
import com.qr.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 *  加载第三方外部包
 * @Author wd
 * @since 15:10 2020/9/21
 **/
@Order(4)
@Component
@Slf4j
public class ModuleClassLoaderInit implements CommandLineRunner {

	@Autowired
	protected StringRedisTemplate stringRedisTemplate;

	@Override
	public void run(String... args) {
		List<Object> ruleInfoList = RedisUtils.getMapList(stringRedisTemplate, ConvergenceContentEnum.RULEINFOS.name());
		ruleInfoList.forEach( ruleInfoStr -> {
			RuleInfos ruleInfos = JSON.parseObject(String.valueOf(ruleInfoStr), RuleInfos.class);
			if (ruleInfos.getRuleType() == RuleTypeEnum.CUSTOMIZE.getValue()){
				try {
					ModuleClassLoader moduleClassLoader = new ModuleClassLoader(new URL[]{new URL(ruleInfos.getRulePath())},
							Thread.currentThread().getContextClassLoader());
					//*************** bean移除 *******************
					moduleClassLoader.deleteBean(ruleInfos.getRulePath());
					//*************** bean注册 *******************
					moduleClassLoader.initBean(ruleInfos.getRulePath());
				} catch (IOException e) {
					log.error("加载第三方jar异常!",e);
					System.exit(0);
				}
			}
		});
	}
}
