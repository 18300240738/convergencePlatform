package com.qr.signature.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qr.exception.RRException;
import com.qr.signature.entity.DeveloperInfo;
import com.qr.signature.mapper.DeveloperInfoMapper;
import com.qr.signature.param.DeveloperParams;
import com.qr.signature.service.IDeveloperInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qr.utils.AppUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wd
 * @since 2020-09-15
 */
@Service
public class DeveloperInfoServiceImpl extends ServiceImpl<DeveloperInfoMapper, DeveloperInfo> implements IDeveloperInfoService {

	@Value("${server.name}")
	private String serverName;

	@Override
	@Transactional(rollbackFor = RRException.class)
	public Boolean save(DeveloperParams developerParams) {

		//********************* 生成appid appSecret *********************
		String appId = AppUtils.getAppId();
		String appSecret = AppUtils.getAppSecret(appId, serverName);
		DeveloperInfo developerInfo = JSONObject.parseObject(JSON.toJSONString(developerParams), DeveloperInfo.class);
		developerInfo.setAppid(appId);
		developerInfo.setAppSecret(appSecret);

		boolean save = this.save(developerInfo);
		return save;
	}
}
