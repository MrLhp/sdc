package sdc.service.equalamount.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leadingsoft.bizfuse.common.web.exception.CustomRuntimeException;
import sdc.dto.equalamount.PaymentEqualAmountDTO;
import sdc.enums.EqualAmountType;
import sdc.model.equalamount.EqualAmountResult;
import sdc.repository.equalamount.EqualAmountResultRepository;
import sdc.service.equalamount.EqualAmountStatisticService;
import sdc.model.equalamount.EqualAmountStatistic;
import sdc.repository.equalamount.EqualAmountStatisticRepository;
import lombok.NonNull;
import sdc.util.EqualAmountOfInterest;
import sdc.util.EqualAmountOfPrincipal;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * EqualAmountStatisticService 实现类
 */
@Service
@Transactional
public class EqualAmountStatisticServiceImpl implements EqualAmountStatisticService {

    @Autowired
    private EqualAmountStatisticRepository equalAmountStatisticRepository;
    @Autowired
    private EqualAmountResultRepository equalAmountResultRepository;

    @Override
    @Transactional(readOnly = true)
    public EqualAmountStatistic get(@NonNull Long id) {
        final  EqualAmountStatistic model = equalAmountStatisticRepository.findOne(id);
        if (model == null) {
            throw new CustomRuntimeException("404", String.format("查找的资源[%s]不存在.", id));
        }
        return model;
    }

    @Override
    public EqualAmountStatistic create(EqualAmountStatistic model) {
        // TODO: 业务逻辑
        return equalAmountStatisticRepository.save(model);
    }

    @Override
    public EqualAmountStatistic update(EqualAmountStatistic model, PaymentEqualAmountDTO dto) {
        Map<Integer, EqualAmountResult> resultMap = model.getResult().stream().collect(Collectors.toMap(EqualAmountResult::getNo, o -> o, (k1, k2) -> k1, LinkedHashMap::new));

        List<PaymentEqualAmountDTO.ResultDTO> resultDTOS = dto.getResultDTOS();

        for (PaymentEqualAmountDTO.ResultDTO resultDTO : resultDTOS) {
            EqualAmountResult equalAmountResult = resultMap.get(resultDTO.getResultNo());
            equalAmountResult.setPayment(true);
            equalAmountResult.setRepaymentDate(resultDTO.getRealRepaymentDate());
            resultMap.put(resultDTO.getResultNo(), equalAmountResult);
        }

        if (model.getEqualAmountType().equals(EqualAmountType.interest)) {
            EqualAmountOfInterest.calcInterest(model, resultMap);
        } else if (model.getEqualAmountType().equals(EqualAmountType.principal)) {
            EqualAmountOfPrincipal.calcPrincipal(model,resultMap);
        }
        return equalAmountStatisticRepository.save(model);
    }

    @Override
    public void delete(@NonNull Long id) {
        // TODO: 业务逻辑
        equalAmountStatisticRepository.delete(id);
    }
}
