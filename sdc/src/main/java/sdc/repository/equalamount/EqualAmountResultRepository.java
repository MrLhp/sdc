package sdc.repository.equalamount;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import sdc.model.equalamount.EqualAmountResult;

/**
 * EqualAmountResultRepository
 */
public interface EqualAmountResultRepository extends Repository<EqualAmountResult, Long> {

    Page<EqualAmountResult> findAll(Pageable pageable);

    EqualAmountResult findOne(Long id);

    EqualAmountResult save(EqualAmountResult model);

    void delete(Long id);

}
