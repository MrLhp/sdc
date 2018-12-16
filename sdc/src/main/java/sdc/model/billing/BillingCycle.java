package sdc.model.billing;

import com.leadingsoft.bizfuse.common.jpa.model.AbstractAuditModel;
import lombok.Getter;
import lombok.Setter;
import sdc.enums.BillingType;

import javax.persistence.*;
import java.util.Date;

/**
 * 账单周期
 * @author haipei
 */
@Getter
@Setter
@Entity
public class BillingCycle extends AbstractAuditModel {
    /**
     * 名称
     */
    private String name;
    /**
     * 用途
     */
    private String useTo;
    /**
     * 描述
     */
    private String description;
    /**
     * 账单产生时间
     */
    @Temporal(TemporalType.DATE)
    private Date billingCreateDate;
    /**
     * 是否已结清
     */
    private Boolean isFinish=false;
    /**
     * 账单金额大小
     */
    private long billingMoney;
    /**
     * 剩余金额
     */
    private long amountMoney;
    /**
     * 账单类型
     */
    @Enumerated(EnumType.STRING)
    private BillingType billingType;
}
