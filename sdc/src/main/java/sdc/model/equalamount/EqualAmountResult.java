package sdc.model.equalamount;

import com.leadingsoft.bizfuse.common.jpa.model.AbstractAuditModel;
import lombok.Data;
import sdc.model.authentication.User;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * 贷款结果
 */
@Data
@Entity
public class EqualAmountResult extends AbstractAuditModel {

    /**
     * 序号
     */
    private int no;
    /**
     * 周期
     */
    private int days;
    /**
     * 还款日
     */
    private Date repaymentDate;
    /**
     * 还款额（x100）
     */
    private long repaymentMoney;
    /**
     * 本金（x100）
     */
    private long principal;
    /**
     * 利息（x100）
     */
    private long interest;
    /**
     * 剩下待还（x100）
     */
    private long amountMoney;
    /**
     * 是否已还款
     */
    private boolean isPayment=false;

    @ManyToOne
    User user;
}
