package com.qr.controller;

import com.qr.customize.ModuleClassLoader;
import com.qr.domain.ResultCode;
import com.qr.result.ResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.net.URL;

@RestController
@RequestMapping("/converge/")
@Slf4j
public class ConfigController {

	@RequestMapping("/401")
	public ResultInfo<String> unauthorized(){
		return ResultInfo.setR(ResultCode.UNAUTHORIZED.getCode(),ResultCode.UNAUTHORIZED.getMessage(),"","");
	}

	@RequestMapping("/501")
	public ResultInfo<String> validateFailed(){
		return ResultInfo.setR(ResultCode.VALIDATE_FAILED.getCode(),ResultCode.VALIDATE_FAILED.getMessage(),"","");
	}

	@RequestMapping("/loader")
	public ResultInfo<String> loader(String jarPath){
		try {
			ModuleClassLoader moduleClassLoader = new ModuleClassLoader(new URL[]{new URL(jarPath)},
					Thread.currentThread().getContextClassLoader());
			//移除本jar bean
			moduleClassLoader.deleteBean(jarPath);
			//注册jarbean
			moduleClassLoader.initBean(jarPath);
			log.info("注册bean:{}",moduleClassLoader.getRegisteredBean());

		}catch (IOException e){
			e.printStackTrace();
		}
		return ResultInfo.setTrueObject("");
	}
}
