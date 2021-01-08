package com.qr.init;

import com.qr.listener.CommonListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ExecutorService;


/**
 *  启动队列线程
 * @Author wd
 * @Description //TODO
 * @Date 12:01 2020/9/7
 **/
@Component
@Order(3)
@Slf4j
public class ListenerRunner implements CommandLineRunner {

	@Autowired
	private Map<String, CommonListener> listenerMap;
	@Resource(name = "QueueThreadPool")
	private ExecutorService executorService;

	@Override
	public void run(String... args) throws Exception {
		listenerMap.forEach((k,v) -> {
			executorService.submit(v);
		});
		log.info("********************** 队列监听线程开启成功 **********************");
	}
}
