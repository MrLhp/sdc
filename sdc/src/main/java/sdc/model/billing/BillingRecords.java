package sdc.model.billing;

import com.leadingsoft.bizfuse.common.jpa.model.AbstractAuditModel;
import lombok.Getter;
import lombok.Setter;
import sdc.enums.RecordType;

import javax.persistence.*;
import java.util.Date;

/**
 * 账单支付记录
 * @author haipei
 */
@Getter
@Setter
@Entity
public class BillingRecords extends AbstractAuditModel {
    /**
     * 对应账单
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private BillingCycle billingCycle;
    /**
     * 支付日期
     */
    @Temporal(TemporalType.DATE)
    private Date paymentBillDate;
    /**
     * 支付金额
     */
    private long paymentMoney;
    /**
     * 描述
     */
    private String description;
    /**
     * 类型
     */
    private RecordType recordType;
}
