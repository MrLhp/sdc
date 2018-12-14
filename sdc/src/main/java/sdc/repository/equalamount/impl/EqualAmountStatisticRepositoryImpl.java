package sdc.repository.equalamount.impl;

import com.leadingsoft.bizfuse.common.jpa.repository.AbstractRepository;
import org.springframework.stereotype.Component;
import sdc.model.equalamount.EqualAmountStatistic;
import sdc.model.equalamount.QEqualAmountResult;
import sdc.model.equalamount.QEqualAmountStatistic;
import sdc.repository.equalamount.EqualAmountStatisticRepositoryCustom;

import java.util.List;

@Component
public class EqualAmountStatisticRepositoryImpl extends AbstractRepository implements EqualAmountStatisticRepositoryCustom {
    @Override
    protected Class<?> getModelClass() {
        return EqualAmountStatistic.class;
    }

    @Override
    public int countByEqualAmountResultNo(long statisticId, int no) {
        QEqualAmountStatistic qEqualAmountStatistic = QEqualAmountStatistic.equalAmountStatistic;
        QEqualAmountResult qEqualAmountResult = QEqualAmountResult.equalAmountResult;

        List<Long> res = this.query().select(qEqualAmountResult.isPayment.count()).from(qEqualAmountStatistic, qEqualAmountResult)
                .where(qEqualAmountResult.equalAmountStatistic.id.eq(qEqualAmountStatistic.id)
                        .and(qEqualAmountStatistic.id.eq(statisticId))
                        .and(qEqualAmountResult.no.lt(no))
                        .and(qEqualAmountResult.isPayment.isFalse())
                ).fetch();
        if (res != null) {
            return res.get(0).intValue();
        }
        return -1;
    }
}
