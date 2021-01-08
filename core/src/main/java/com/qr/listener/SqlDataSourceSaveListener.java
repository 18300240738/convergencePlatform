package com.qr.listener;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.qr.context.ConvergeContext;
import com.qr.entity.DataInfoMsg;
import com.qr.service.IDataObjectSourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.List;

import static com.qr.context.ConvergeContext.*;

/**
 * @author : wangdong
 * create at:  2020/9/14  15:55
 * @description: 数据源保存数据
 */
@Component
@Slf4j
public class SqlDataSourceSaveListener implements CommonListener{

    @Value("${time.config.sqlQueue}")
    private Long time;

    @Autowired
    private IDataObjectSourceService dataObjectSourceService;

    @Override
    public void run() {
        while (1 > 0) {
            try {
                //**************** 线程休眠 *****************
                Thread.sleep(time);

                dealwith();
            } catch (Exception e) {
                log.error("处理入库 队列数据 异常:{}", e);
            }
        }
    }

    public synchronized void dealwith(){
        List<DataInfoMsg> dataInfoMsgList = Lists.newArrayList();
        int num = SQLDATASAVEQUEUESIZE - ConvergeContext.getSqlDataSaveQueue().remainingCapacity();
//        log.info("队列总容量：{}",num)
        for (int i = 0; i < num; i++) {
            String msg = String.valueOf(ConvergeContext.getSqlDataSaveQueue().poll());
            DataInfoMsg dataInfoMsg = JSON.parseObject(msg, DataInfoMsg.class);
            dataInfoMsgList.add(dataInfoMsg);
        }

//        log.info("数据对象 队列剩余数量：{}",(SQLDATASAVEQUEUESIZE - ConvergeContext.getSqlDataSaveQueue().remainingCapacity()))
        if (!dataInfoMsgList.isEmpty()) {
            Boolean aBoolean = dataObjectSourceService.sqlWarehousing(dataInfoMsgList);
            if (Boolean.FALSE.equals(aBoolean)) {
                log.error("数据保存失败!数据信息:{}", dataInfoMsgList);
            }
        }
    }
}
