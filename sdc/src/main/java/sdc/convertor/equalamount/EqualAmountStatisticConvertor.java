package sdc.convertor.equalamount;

import com.leadingsoft.bizfuse.common.web.dto.AbstractConvertor;
import lombok.NonNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sdc.bean.EqualAmountBean;
import sdc.dto.equalamount.EqualAmountPreviewDTO;
import sdc.dto.equalamount.EqualAmountStatisticDTO;
import sdc.enums.EqualAmountType;
import sdc.model.authentication.User;
import sdc.model.equalamount.EqualAmountResult;
import sdc.model.equalamount.EqualAmountStatistic;
import sdc.service.equalamount.EqualAmountStatisticService;
import sdc.util.EqualAmountOfInterest;
import sdc.util.EqualAmountOfPrincipal;

import java.util.ArrayList;
import java.util.List;

/**
 * EqualAmountStatisticConvertor
 */
@Component
public class EqualAmountStatisticConvertor extends AbstractConvertor<EqualAmountStatistic, EqualAmountStatisticDTO> {

    @Autowired
    private EqualAmountStatisticService equalAmountStatisticService;

    @Override
    public EqualAmountStatistic toModel(@NonNull final EqualAmountStatisticDTO dto) {
        if (dto.isNew()) {//新增
            return constructModel(dto);
        } else {//更新
            return updateModel(dto);
        }
    }

    @Override
    public EqualAmountStatisticDTO toDTO(@NonNull final EqualAmountStatistic model, final boolean forListView) {
        final EqualAmountStatisticDTO dto = new EqualAmountStatisticDTO();
        dto.setId(model.getId());
        dto.setUsername(model.getUser().getDetails().getName());
        dto.setUserNo(model.getUser().getNo());
        dto.setEqualAmountName(model.getEqualAmountName());
        dto.setAmountTotalMoney(model.getAmountTotalMoney());
        dto.setEqualAmountType(model.getEqualAmountType());
        dto.setEqualAmountSource(model.getEqualAmountSource());

        return dto;
    }

    // 构建新Model
    private EqualAmountStatistic constructModel(final EqualAmountStatisticDTO dto) {
        EqualAmountStatistic model = new EqualAmountStatistic();
        model.setEqualAmountName(dto.getEqualAmountName());
        model.setAmountTotalMoney(dto.getAmountTotalMoney());
        model.setEqualAmountType(dto.getEqualAmountType());
        model.setEqualAmountSource(dto.getEqualAmountSource());

        return model;
    }

    // 更新Model
    private EqualAmountStatistic updateModel(final EqualAmountStatisticDTO dto) {
        EqualAmountStatistic model = equalAmountStatisticService.get(dto.getId());
        model.setEqualAmountName(dto.getEqualAmountName());
        model.setAmountTotalMoney(dto.getAmountTotalMoney());
        model.setEqualAmountType(dto.getEqualAmountType());
        model.setEqualAmountSource(dto.getEqualAmountSource());

        return model;
    }

    public EqualAmountStatistic toModel(EqualAmountPreviewDTO dto, User user) {
        EqualAmountStatistic model = new EqualAmountStatistic();
        List<EqualAmountResult> equalAmountResults = new ArrayList<>();
        Integer totalAmountMoney= new Integer(0);

        model.setUser(user);
        model.setEqualAmountName(dto.getEqualAmountName());
        model.setEqualAmountType(dto.getEqualAmountType());
        model.setEqualAmountSource(dto.getEqualAmountSource());
        model.setResult(equalAmountResults);

        List<EqualAmountBean> equalAmountBeans;
        if (dto.getEqualAmountType().equals(EqualAmountType.interest)) {
            equalAmountBeans = EqualAmountOfInterest.execEqualAmountOfInterest(dto.getQuota(), dto.getInterestRate(), dto.getInterestRebate(),
                    dto.getDateOfLoan(), dto.getStageNumberOfMonth());
        } else {
            equalAmountBeans = EqualAmountOfPrincipal.execEqualAmountPrincipal(dto.getQuota(), dto.getInterestRate(), dto.getInterestRebate(),
                    dto.getDateOfLoan(), dto.getStageNumberOfMonth());
        }


        for (EqualAmountBean equalAmountBean : equalAmountBeans) {
            EqualAmountResult equalAmountResult = new EqualAmountResult();

            BeanUtils.copyProperties(equalAmountBean,equalAmountResult);

            totalAmountMoney+=Integer.parseInt(String.valueOf(equalAmountResult.getRepaymentMoney()));
            equalAmountResult.setEqualAmountStatistic(model);
            equalAmountResults.add(equalAmountResult);
        }

        model.setAmountTotalMoney(totalAmountMoney);

        return model;
    }

}
