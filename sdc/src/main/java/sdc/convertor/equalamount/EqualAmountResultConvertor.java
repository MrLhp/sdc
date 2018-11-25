package sdc.convertor.equalamount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.leadingsoft.bizfuse.common.web.dto.AbstractConvertor;
import sdc.bean.EqualAmountBean;
import sdc.dto.equalamount.EqualAmountPreviewDTO;
import sdc.dto.equalamount.EqualAmountResultDTO;
import sdc.model.equalamount.EqualAmountResult;
import sdc.service.equalamount.EqualAmountResultService;
import lombok.NonNull;
import sdc.util.EqualAmountOfInterest;
import sdc.util.EqualAmountOfPrincipal;

import java.util.List;

/**
 * EqualAmountResultConvertor
 */
@Component
public class EqualAmountResultConvertor extends AbstractConvertor<EqualAmountResult, EqualAmountResultDTO> {

    @Autowired
    private EqualAmountResultService equalAmountResultService;

    @Override
    public EqualAmountResult toModel(@NonNull final EqualAmountResultDTO dto) {
        if (dto.isNew()) {//新增
            return constructModel(dto);
        } else {//更新
            return updateModel(dto);
        }
    }

    @Override
    public EqualAmountResultDTO toDTO(@NonNull final EqualAmountResult model, final boolean forListView) {
        final EqualAmountResultDTO dto = new EqualAmountResultDTO();
        dto.setId(model.getId());
        dto.setNo(model.getNo());
        dto.setDays(model.getDays());
        dto.setRepaymentDate(model.getRepaymentDate());
        dto.setRepaymentMoney(model.getRepaymentMoney());
        dto.setPrincipal(model.getPrincipal());
        dto.setInterest(model.getInterest());
        dto.setAmountMoney(model.getAmountMoney());
        dto.setPayment(model.isPayment());

        return dto;
    }

    // 构建新Model
    private EqualAmountResult constructModel(final EqualAmountResultDTO dto) {
        EqualAmountResult model = new EqualAmountResult();
        model.setNo(dto.getNo());
        model.setDays(dto.getDays());
        model.setRepaymentDate(dto.getRepaymentDate());
        model.setRepaymentMoney(dto.getRepaymentMoney());
        model.setPrincipal(dto.getPrincipal());
        model.setInterest(dto.getInterest());
        model.setAmountMoney(dto.getAmountMoney());
        model.setPayment(dto.isPayment());

        return model;
    }

    // 更新Model
    private EqualAmountResult updateModel(final EqualAmountResultDTO dto) {
        EqualAmountResult model = equalAmountResultService.get(dto.getId());
        model.setNo(dto.getNo());
        model.setDays(dto.getDays());
        model.setRepaymentDate(dto.getRepaymentDate());
        model.setRepaymentMoney(dto.getRepaymentMoney());
        model.setPrincipal(dto.getPrincipal());
        model.setInterest(dto.getInterest());
        model.setAmountMoney(dto.getAmountMoney());
        model.setPayment(dto.isPayment());

        return model;
    }

    /**
     * 等额本息计算
     * @param dto
     * @return
     */
    public List<EqualAmountBean> toInterestPreview(EqualAmountPreviewDTO dto) {
        List<EqualAmountBean> equalAmountBeans = EqualAmountOfInterest.execEqualAmountOfInterest(dto.getQuota(), dto.getInterestRate(), dto.getInterestRebate(),
                dto.getDateOfLoan(), dto.getStageNumberOfMonth());

        return equalAmountBeans;
    }

    /**
     * 等额本金计算
     * @param dto
     * @return
     */
    public List<EqualAmountBean> toPrincipalPreview(EqualAmountPreviewDTO dto) {
        List<EqualAmountBean> equalAmountBeans = EqualAmountOfPrincipal.execEqualAmountPrincipal(dto.getQuota(), dto.getInterestRate(), dto.getInterestRebate(),
                dto.getDateOfLoan(), dto.getStageNumberOfMonth());
        return equalAmountBeans;
    }
}
