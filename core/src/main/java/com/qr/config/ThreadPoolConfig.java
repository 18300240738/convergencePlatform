package com.qr.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 *  线程池配置
 * @Author wangdong
 * @Date 14:29
 **/
@Configuration
public class ThreadPoolConfig {

	/**
	 *  队列数据消费线程
	 * @Author wd
	 * @Date 13:47 2020/9/7
	 **/
	@Bean(value = "QueueThreadPool")
	public ExecutorService queueExecutorPool(){
		ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("queue-pool-%d").build();
		return new ThreadPoolExecutor(2, 16, 60L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100), threadFactory, new ThreadPoolExecutor.AbortPolicy());
	}

}
