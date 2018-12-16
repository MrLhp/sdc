package sdc.dto.billing;
import com.leadingsoft.bizfuse.common.web.dto.AbstractDTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import sdc.model.billing.BillingCycle;

import java.util.Date;

/**
 * 账单支付记录
 * @author haipei
 */
@Getter
@Setter
public class BillingRecordsDTO extends AbstractDTO {
    /**
     * 对应账单
     */
    @ApiModelProperty("对应账单")

    private BillingCycle billingCycle;
    /**
     * 支付日期
     */
    @ApiModelProperty("支付日期")

    private Date paymentBillDate;
    /**
     * 支付金额
     */
    @ApiModelProperty("支付金额")
    private long paymentMoney;
    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String description;

}
