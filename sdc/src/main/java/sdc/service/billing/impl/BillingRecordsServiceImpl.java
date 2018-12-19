package sdc.service.billing.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leadingsoft.bizfuse.common.web.exception.CustomRuntimeException;
import sdc.enums.BillingType;
import sdc.enums.RecordType;
import sdc.model.billing.BillingCycle;
import sdc.repository.billing.BillingCycleRepository;
import sdc.service.billing.BillingRecordsService;
import sdc.model.billing.BillingRecords;
import sdc.repository.billing.BillingRecordsRepository;
import lombok.NonNull;

/**
 * BillingRecordsService 实现类
 */
@Service
@Transactional
public class BillingRecordsServiceImpl implements BillingRecordsService {

    @Autowired
    private BillingRecordsRepository billingRecordsRepository;
    @Autowired
    private BillingCycleRepository billingCycleRepository;

    @Override
    @Transactional(readOnly = true)
    public BillingRecords get(@NonNull Long id) {
        final  BillingRecords model = billingRecordsRepository.findOne(id);
        if (model == null) {
            throw new CustomRuntimeException("404", String.format("查找的资源[%s]不存在.", id));
        }
        return model;
    }

    @Override
    public BillingRecords create(BillingRecords model) {
        BillingCycle billingCycle = billingCycleRepository.findOne(model.getBillingCycle().getId());
        if (billingCycle.getBillingType().equals(BillingType.inPay)) {
            if (model.getRecordType().equals(RecordType.increase)) {
                billingCycle.setAmountMoney(billingCycle.getAmountMoney() - model.getPaymentMoney());
            } else if(model.getRecordType().equals(RecordType.reduce)) {
                billingCycle.setAmountMoney(billingCycle.getAmountMoney() + model.getPaymentMoney());
                billingCycle.setBillingMoney(billingCycle.getBillingMoney() + model.getPaymentMoney());
            }
        } else if (billingCycle.getBillingType().equals(BillingType.outPay)) {
            if (model.getRecordType().equals(RecordType.increase)) {
                billingCycle.setAmountMoney(billingCycle.getAmountMoney() + model.getPaymentMoney());
                billingCycle.setBillingMoney(billingCycle.getBillingMoney() + model.getPaymentMoney());
            } else if(model.getRecordType().equals(RecordType.reduce)) {
                billingCycle.setAmountMoney(billingCycle.getAmountMoney() - model.getPaymentMoney());
            }
        }
        if (billingCycle.getAmountMoney() > 0) {
            billingCycle.setIsFinish(false);
        } else {
            billingCycle.setIsFinish(true);
        }
        model.setBillingCycle(billingCycle);
        return billingRecordsRepository.save(model);
    }

    @Override
    public BillingRecords update(BillingRecords model) {
        // TODO: 业务逻辑
        return billingRecordsRepository.save(model);
    }

    @Override
    public void delete(@NonNull Long id) {
        // TODO: 业务逻辑
        billingRecordsRepository.delete(id);
    }
}
