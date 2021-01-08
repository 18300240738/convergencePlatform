package com.qr.log;

import com.qr.entity.QueueMsg;
import com.qr.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import static com.qr.enums.ConvergenceContentEnum.*;

/**
 *  日志接口实现类
 * @Author wd
 * @since 10:21 2020/9/27
 **/
@Service
@Slf4j
public class LogRecordingRequestWrapper implements LogHandler{

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Override
	public void interfaceCount(Boolean flag) {
		log.info("进入请求记录!");
		if (Boolean.TRUE.equals(flag)){
			log.info("进入成功请求记录!");
		}
	}

	@Override
	public void queueBeforeHandle(QueueMsg queueMsg,boolean flag) {
		//获取今日时间
		LocalDateTime now = LocalDateTime.now();
		String dateStr = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		log.info("进入入队请求记录!");
		RedisUtils.incr(stringRedisTemplate,dateStr + "_" + REQUESTCOUNT,30, TimeUnit.DAYS);
		if (flag){
			log.info("进入入队成功请求记录!");
			RedisUtils.incr(stringRedisTemplate,dateStr + "_" + REQUESTCOUNT + "_" + SUCCESS,30, TimeUnit.DAYS);
		}else {
			log.info("进入入队失败请求记录!");
			RedisUtils.incr(stringRedisTemplate,dateStr + "_" + REQUESTCOUNT + "_" + FAIL,30, TimeUnit.DAYS);
		}
	}

	@Override
	public void queueAfterHandle(QueueMsg queueMsg,boolean flag) {
//		LocalDateTime now = LocalDateTime.now();
//		String dateStr = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		if (flag){
			log.info("队列任务处理成功 记录!");
		}else {
			log.info("队列任务处理失败 记录!");
		}
	}

}
