package sdc.service.billing.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leadingsoft.bizfuse.common.web.exception.CustomRuntimeException;
import sdc.service.billing.BillingCycleService;
import sdc.model.billing.BillingCycle;
import sdc.repository.billing.BillingCycleRepository;
import lombok.NonNull;

/**
 * BillingCycleService 实现类
 */
@Service
@Transactional
public class BillingCycleServiceImpl implements BillingCycleService {

    @Autowired
    private BillingCycleRepository billingCycleRepository;

    @Override
    @Transactional(readOnly = true)
    public BillingCycle get(@NonNull Long id) {
        final  BillingCycle model = billingCycleRepository.findOne(id);
        if (model == null) {
            throw new CustomRuntimeException("404", String.format("查找的资源[%s]不存在.", id));
        }
        return model;
    }

    @Override
    public BillingCycle create(BillingCycle model) {
        // TODO: 业务逻辑
        return billingCycleRepository.save(model);
    }

    @Override
    public BillingCycle update(BillingCycle model) {
        // TODO: 业务逻辑
        return billingCycleRepository.save(model);
    }

    @Override
    public void delete(@NonNull Long id) {
        // TODO: 业务逻辑
        billingCycleRepository.delete(id);
    }
}
