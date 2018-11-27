package sdc.repository.equalamount.impl;

import com.leadingsoft.bizfuse.common.jpa.repository.AbstractRepository;
import com.leadingsoft.bizfuse.common.web.support.Searchable;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import sdc.model.equalamount.EqualAmountResult;
import sdc.model.equalamount.QEqualAmountResult;
import sdc.model.equalamount.QEqualAmountStatistic;
import sdc.repository.equalamount.EqualAmountResultRepositoryCustom;

@Component
public class EqualAmountResultRepositoryImpl extends AbstractRepository implements EqualAmountResultRepositoryCustom {
    @Override
    protected Class<?> getModelClass() {
        return EqualAmountResult.class;
    }

    @Override
    public Page<EqualAmountResult> searchPage(Pageable pageable, Searchable searchable) {

        return null;
    }
}
