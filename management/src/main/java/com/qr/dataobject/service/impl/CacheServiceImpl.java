package com.qr.dataobject.service.impl;

import com.google.common.collect.Maps;
import com.qr.dataobject.entity.DataFields;
import com.qr.dataobject.entity.DataNames;
import com.qr.dataobject.entity.FieldRule;
import com.qr.dataobject.entity.RuleInfos;
import com.qr.dataobject.service.*;
import com.qr.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.qr.enums.ConvergenceContentEnum.*;

/**
 * @author : wangdong
 * create at:  2020/9/28  14:36
 * @description: 缓存数据操作实现类
 */
@Slf4j
@Service
public class CacheServiceImpl implements ICacheService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private IDataNamesService dataNamesService;
    @Autowired
    private IDataFieldsService dataFieldsService;
    @Autowired
    private IFieldRuleService fieldRuleService;
    @Autowired
    private IRuleInfosService ruleInfosService;
    @Autowired
    private IDataNamesFlowService dataNamesFlowService;

    @Override
    public void updateCache() {
        List<FieldRule> fieldRuleList = fieldRuleService.list();
        List<RuleInfos> ruleInfosList = ruleInfosService.list();
        List<DataNames> dataNamesList = dataNamesService.list();
        List<DataFields> dataFieldsList = dataFieldsService.list();

        //******************************* 数据对象名称处理 *******************************
        RedisUtils.add(stringRedisTemplate,DATANAMES.name(),dataNamesList);

        //******************************* 数据对象属性处理 *******************************
        Map<String, List<DataFields>> dataFieldMap = dataFieldsList.stream().collect(Collectors.groupingBy(DataFields::getDataName));
        dataFieldMap.forEach((k,v)-> {
            RedisUtils.add(stringRedisTemplate,k,v);
        });

        //******************************* 数据对象属性规则处理 *******************************
        Map dataFiledRuleMap = fieldRuleList.stream().collect(Collectors.groupingBy(FieldRule::getDataName));

        RedisUtils.addMap(stringRedisTemplate, FIELDRULES.name(),dataFiledRuleMap);

        //******************************* 校验规则数据处理 *******************************
        Map<String,Object> ruleInfoMap = Maps.newHashMap();
        ruleInfosList.forEach(ruleInfos -> {
            ruleInfoMap.put(ruleInfos.getRuleCode(),ruleInfos);
        });
        RedisUtils.addMap(stringRedisTemplate, RULEINFOS.name(),ruleInfoMap);

        log.info("********************** 数据对象信息写入缓存成功 **********************");
    }
}
