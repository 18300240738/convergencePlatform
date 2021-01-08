package com.qr.dataobject.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qr.dataobject.entity.DataFlowInfo;
import com.qr.dataobject.entity.DataNamesFlow;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wd
 * @since 2020-09-07
 */
public interface IDataNamesFlowService extends IService<DataNamesFlow> {

	/**
	 *  新增数据对象流向表
	 * @Author wd
	 * @Description //TODO
	 * @Date 13:58 2020/9/7
	 * @param dataNamesFlowList
	 * @return boolean
	 **/
	boolean saveInfo(List<DataNamesFlow> dataNamesFlowList);

	/**
	 * 查询数据对象流向
	 * @param dataCode
	 * @return
	 */
	List<DataFlowInfo> queryDataFlowInfo(String dataCode);

	/**
	 *  数据对象删除流程
	 * @param ids
	 * @return
	 */
    boolean delInfo(List<Long> ids);
}
