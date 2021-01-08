package com.qr.dataobject.mapper;

import com.qr.dataobject.entity.FieldRule;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 字段校验规则中间表 Mapper 接口
 * </p>
 *
 * @author wd
 * @since 2020-08-28
 */
public interface FieldRuleMapper extends BaseMapper<FieldRule> {

	/**
	 *  查询所有属性规则对应信息
	 * @Author wd
	 * @since 10:55 2020/9/25
	 * @return map
	 **/
	@Select(" select df.data_code dataCode,df.data_name dataName,df.field_code fieldCode, " +
			" df.field_name fieldName,ri.rule_code ruleCode " +
			" from data_fields df left join field_rule fr on df.field_code = fr.field_code " +
			" left join rule_infos ri on fr.rule_code = ri.rule_code " +
			" where df.is_del = 0 and fr.is_del = 0 and ri.is_del = 0 ")
	List<Map> queryFieldRuleList();
}
