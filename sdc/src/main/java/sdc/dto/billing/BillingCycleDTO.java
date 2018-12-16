package sdc.dto.billing;
import com.leadingsoft.bizfuse.common.web.dto.AbstractDTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import sdc.enums.BillingType;

import java.util.Date;

/**
 * 账单周期
 * @author haipei
 */
@Getter
@Setter
public class BillingCycleDTO extends AbstractDTO {
    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;
    /**
     * 用途
     */
    @ApiModelProperty("用途")
    private String useTo;
    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String description;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")

    private Date billingCreateDate;
    /**
     * 是否已结清
     */
    @ApiModelProperty("是否已结清")
    private Boolean isFinish=false;
    /**
     * 账单金额大小
     */
    @ApiModelProperty("账单金额大小")
    private long billingMoney;
    /**
     * 剩余金额
     */
    @ApiModelProperty("剩余金额")
    private long amountMoney;
    /**
     * 账单类型
     */
    @ApiModelProperty("账单类型")

    private BillingType billingType;
}
