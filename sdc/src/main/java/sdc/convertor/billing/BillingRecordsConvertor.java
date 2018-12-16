package sdc.convertor.billing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.leadingsoft.bizfuse.common.web.dto.AbstractConvertor;
import sdc.dto.billing.BillingRecordsDTO;
import sdc.model.billing.BillingRecords;
import sdc.service.billing.BillingRecordsService;
import lombok.NonNull;

/**
 * BillingRecordsConvertor
 */
@Component
public class BillingRecordsConvertor extends AbstractConvertor<BillingRecords, BillingRecordsDTO> {

    @Autowired
    private BillingRecordsService billingRecordsService;
    
    @Override
    public BillingRecords toModel(@NonNull final BillingRecordsDTO dto) {
        if (dto.isNew()) {//新增
            return constructModel(dto);
        } else {//更新
            return updateModel(dto);
        }
    }

    @Override
    public BillingRecordsDTO toDTO(@NonNull final BillingRecords model, final boolean forListView) {
        final BillingRecordsDTO dto = new BillingRecordsDTO();
        dto.setId(model.getId());
        dto.setBillingCycle(model.getBillingCycle());
        dto.setPaymentBillDate(model.getPaymentBillDate());
        dto.setPaymentMoney(model.getPaymentMoney());
        dto.setDescription(model.getDescription());

        return dto;
    }

    // 构建新Model
    private BillingRecords constructModel(final BillingRecordsDTO dto) {
        BillingRecords model = new BillingRecords();
        model.setBillingCycle(dto.getBillingCycle());
        model.setPaymentBillDate(dto.getPaymentBillDate());
        model.setPaymentMoney(dto.getPaymentMoney()*100);
        model.setDescription(dto.getDescription());

        return model;
    }

    // 更新Model
    private BillingRecords updateModel(final BillingRecordsDTO dto) {
        BillingRecords model = billingRecordsService.get(dto.getId());
        model.setBillingCycle(dto.getBillingCycle());
        model.setPaymentBillDate(dto.getPaymentBillDate());
        model.setPaymentMoney(dto.getPaymentMoney()*100);
        model.setDescription(dto.getDescription());

        return model;
    }
}
