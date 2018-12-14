package sdc.repository.equalamount;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import sdc.model.equalamount.EqualAmountStatistic;

/**
 * EqualAmountStatisticRepository
 */
public interface EqualAmountStatisticRepository extends Repository<EqualAmountStatistic, Long>,EqualAmountStatisticRepositoryCustom {

    Page<EqualAmountStatistic> findAll(Pageable pageable);

    EqualAmountStatistic findOne(Long id);

    EqualAmountStatistic save(EqualAmountStatistic model);

    void delete(Long id);
}
