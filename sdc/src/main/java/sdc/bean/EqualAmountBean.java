package sdc.bean;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * 分期结果
 */
@Getter
@Setter
public class EqualAmountBean {
    /**
     * 序号
     */
    int no;
    /**
     * 周期
     */
    int days;
    /**
     * 还款日
     */
    @Temporal(TemporalType.DATE)
    Date repaymentDate;
    /**
     * 还款额（x100）
     */
    long repaymentMoney;
    /**
     * 本金（x100）
     */
    long principal;
    /**
     * 利息（x100）
     */
    long interest;
    /**
     * 剩下待还（x100）
     */
    long amountMoney;
}
