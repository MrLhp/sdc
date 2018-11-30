package sdc.dto.equalamount;

import com.leadingsoft.bizfuse.common.web.dto.AbstractDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import sdc.enums.EqualAmountSource;
import sdc.enums.EqualAmountType;
import sdc.model.equalamount.EqualAmountResult;

import java.util.Date;
import java.util.List;

/**
 * 贷款统计
 */
@Getter
@Setter
public class EqualAmountStatisticDTO extends AbstractDTO {

    private String username;

    private String userNo;

    /**
     * 贷款名称
     */
    @ApiModelProperty("贷款名称")
    private String equalAmountName;
    /**
     * 待还总金额
     */
    @ApiModelProperty("待还总金额")
    private long amountTotalMoney;
    /**
     * 贷款类型
     */
    @ApiModelProperty("贷款类型")
    private EqualAmountType equalAmountType;
    /**
     * 贷款来源
     */
    @ApiModelProperty("贷款来源")
    private EqualAmountSource equalAmountSource;

    /**
     * 贷款利率
     */
    private double interestRate;
    /**
     * 利息折扣
     */
    private double interestRebate;
    /**
     * 贷款额度
     */
    private double quota;
    /**
     * 贷款开始时间
     */
    private Date dateOfLoan;
    /**
     * 分期数
     */
    private int stageNumberOfMonth;
}
