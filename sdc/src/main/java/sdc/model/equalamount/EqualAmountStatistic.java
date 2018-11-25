package sdc.model.equalamount;

import com.leadingsoft.bizfuse.common.jpa.model.AbstractAuditModel;
import lombok.Data;
import sdc.enums.EqualAmountSource;
import sdc.enums.EqualAmountType;
import sdc.model.authentication.User;

import javax.persistence.*;
import java.util.List;

/**
 * 贷款统计
 */
@Data
@Entity
public class EqualAmountStatistic extends AbstractAuditModel {
    @ManyToOne
    private User user;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EqualAmountResult> result;

    /**
     * 贷款名称
     */
    private String equalAmountName;
    /**
     * 待还总金额
     */
    private long amountTotalMoney;
    /**
     * 贷款类型
     */
    @Enumerated(EnumType.STRING)
    private EqualAmountType equalAmountType;
    /**
     * 贷款来源
     */
    @Enumerated(EnumType.STRING)
    private EqualAmountSource equalAmountSource;
}
