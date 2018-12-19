package sdc.convertor.billing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.leadingsoft.bizfuse.common.web.dto.AbstractConvertor;
import sdc.dto.billing.BillingCycleDTO;
import sdc.model.billing.BillingCycle;
import sdc.service.billing.BillingCycleService;
import lombok.NonNull;

/**
 * BillingCycleConvertor
 */
@Component
public class BillingCycleConvertor extends AbstractConvertor<BillingCycle, BillingCycleDTO> {

    @Autowired
    private BillingCycleService billingCycleService;
    
    @Override
    public BillingCycle toModel(@NonNull final BillingCycleDTO dto) {
        if (dto.isNew()) {//新增
            return constructModel(dto);
        } else {//更新
            return updateModel(dto);
        }
    }

    @Override
    public BillingCycleDTO toDTO(@NonNull final BillingCycle model, final boolean forListView) {
        final BillingCycleDTO dto = new BillingCycleDTO();
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setUseTo(model.getUseTo());
        dto.setDescription(model.getDescription());
        dto.setBillingCreateDate(model.getBillingCreateDate());
        dto.setIsFinish(model.getIsFinish());
        dto.setBillingMoney(model.getBillingMoney());
        dto.setAmountMoney(model.getAmountMoney());
        dto.setBillingType(model.getBillingType());

        return dto;
    }

    // 构建新Model
    private BillingCycle constructModel(final BillingCycleDTO dto) {
        BillingCycle model = new BillingCycle();
        model.setName(dto.getName());
        model.setUseTo(dto.getUseTo());
        model.setDescription(dto.getDescription());
        model.setBillingCreateDate(dto.getBillingCreateDate());
        model.setIsFinish(dto.getIsFinish());
        model.setBillingMoney(dto.getBillingMoney()*100);
        model.setAmountMoney(dto.getBillingMoney()*100);
        model.setBillingType(dto.getBillingType());

        return model;
    }

    // 更新Model
    private BillingCycle updateModel(final BillingCycleDTO dto) {
        BillingCycle model = billingCycleService.get(dto.getId());
        model.setName(dto.getName());
        model.setUseTo(dto.getUseTo());
        model.setDescription(dto.getDescription());
        model.setBillingCreateDate(dto.getBillingCreateDate());
        model.setIsFinish(dto.getIsFinish());
        model.setBillingMoney(dto.getBillingMoney()*100);
        model.setAmountMoney(dto.getAmountMoney()*100);
        model.setBillingType(dto.getBillingType());

        return model;
    }
}
