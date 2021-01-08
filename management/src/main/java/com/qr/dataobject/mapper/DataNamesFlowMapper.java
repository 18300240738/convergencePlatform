package com.qr.dataobject.mapper;

import com.qr.dataobject.entity.DataFlowInfo;
import com.qr.dataobject.entity.DataNamesFlow;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wd
 * @since 2020-09-07
 */
public interface DataNamesFlowMapper extends BaseMapper<DataNamesFlow> {

	@Select("SELECT DFI.* FROM DATA_NAMES_FLOW DNF LEFT JOIN DATA_FLOW_INFO DFI ON DNF.FLOW_CODE = DFI.DATA_FLOW_CODE" +
			" WHERE DATA_CODE = #{DATACODE} AND DNF.IS_DEL = 0 AND DFI.IS_DEL = 0")
	List<DataFlowInfo> queryDataFlowInfo(String dataCode);
}
