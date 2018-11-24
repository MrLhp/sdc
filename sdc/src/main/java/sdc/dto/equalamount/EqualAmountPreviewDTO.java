package sdc.dto.equalamount;

import lombok.Getter;
import lombok.Setter;
import sdc.enums.EqualAmountType;

import java.util.Date;

/**
 * 贷款预算
 */
@Getter
@Setter
public class EqualAmountPreviewDTO {
    /**
     * 贷款额度
     */
    private double quota;
    /**
     * 贷款利率
     */
    private double interestRate;
    /**
     * 利息折扣
     */
    private double interestRebate;
    /**
     * 贷款开始时间
     */
    private Date dateOfLoan;
    /**
     * 分期数
     */
    private int stageNumberOfMonth;

    private EqualAmountType equalAmountType;
}
