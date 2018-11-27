package sdc.dto.equalamount;
import com.leadingsoft.bizfuse.common.web.dto.AbstractDTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import sdc.model.authentication.User;

import java.util.Date;

/**
 * 贷款结果
 */
@ApiModel("贷款结果")
@Getter
@Setter
public class EqualAmountResultDTO extends AbstractDTO {

    /**
     * 序号
     */
    @ApiModelProperty("序号")
    private int no;
    /**
     * 周期
     */
    @ApiModelProperty("周期")
    private int days;
    /**
     * 还款日
     */
    @ApiModelProperty("还款日")
    private Date repaymentDate;
    /**
     * 还款额（x100）
     */
    @ApiModelProperty("还款额（x100）")
    private long repaymentMoney;
    /**
     * 本金（x100）
     */
    @ApiModelProperty("本金（x100）")
    private long principal;
    /**
     * 利息（x100）
     */
    @ApiModelProperty("利息（x100）")
    private long interest;
    /**
     * 剩下待还（x100）
     */
    @ApiModelProperty("剩下待还（x100）")
    private long amountMoney;
    /**
     * 是否已还款
     */
    @ApiModelProperty("是否已还款")
    private boolean isPayment=false;
}
