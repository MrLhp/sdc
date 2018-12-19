package sdc.repository.billing;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import sdc.model.billing.BillingRecords;

import java.util.List;

/**
 * BillingRecordsRepository
 */
public interface BillingRecordsRepository extends Repository<BillingRecords, Long> {

    Page<BillingRecords> findAll(Pageable pageable);

    List<BillingRecords> findAllByBillingCycle_Id(Long billingCycle_id);

    BillingRecords findOne(Long id);

    BillingRecords save(BillingRecords model);

    void deleteByBillingCycle_Id(Long billingCycle_id);

    void delete(Long id);

}
