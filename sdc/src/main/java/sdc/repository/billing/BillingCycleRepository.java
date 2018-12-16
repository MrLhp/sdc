package sdc.repository.billing;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import sdc.model.billing.BillingCycle;

import java.util.List;

/**
 * BillingCycleRepository
 */
public interface BillingCycleRepository extends Repository<BillingCycle, Long> {

    Page<BillingCycle> findAll(Pageable pageable);

    List<BillingCycle> findAll();

    BillingCycle findOne(Long id);

    BillingCycle save(BillingCycle model);

    void delete(Long id);

}
