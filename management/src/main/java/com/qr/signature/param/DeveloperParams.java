package com.qr.signature.param;

import com.baomidou.mybatisplus.annotation.TableField;
import com.qr.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 
 * </p>
 *
 * @author wd
 * @since 2020-09-15
 */
@Data
@ApiModel(value="DeveloperInfo对象", description="")
public class DeveloperParams {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "公司名称")
    @NotNull
    private String name;

    @ApiModelProperty(value = "统一社会信用代码")
    @NotNull
    private String code;

    @ApiModelProperty(value = "contactor	联系人")
    @NotNull
    private String contactor;

    @ApiModelProperty(value = "联系人手机号码")
    @NotNull
    private String mobile;

    @ApiModelProperty(value = "开发方SM2公钥 ")
    @NotNull
    private String publicKey;

    @ApiModelProperty(value = "IP	数据来源IP")
    @TableField("IP")
    private String ip;

    @ApiModelProperty(value = "验签盐值（8~40位）")
    @NotNull
    private String salt;


}
