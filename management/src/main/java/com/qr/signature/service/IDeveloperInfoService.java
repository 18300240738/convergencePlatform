package com.qr.signature.service;

import com.qr.signature.entity.DeveloperInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qr.signature.param.DeveloperParams;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wd
 * @since 2020-09-15
 */
public interface IDeveloperInfoService extends IService<DeveloperInfo> {

	/**
	 *  保存开发者信息
	 * @Author wd
	 * @Description //TODO
	 * @Date 13:58 2020/9/15
	 * @param developerParams
	 * @return boolean
	 **/
	Boolean save(DeveloperParams developerParams);
}
