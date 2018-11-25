package sdc.service.equalamount.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leadingsoft.bizfuse.common.web.exception.CustomRuntimeException;
import sdc.service.equalamount.EqualAmountStatisticService;
import sdc.model.equalamount.EqualAmountStatistic;
import sdc.repository.equalamount.EqualAmountStatisticRepository;
import lombok.NonNull;

/**
 * EqualAmountStatisticService 实现类
 */
@Service
@Transactional
public class EqualAmountStatisticServiceImpl implements EqualAmountStatisticService {

    @Autowired
    private EqualAmountStatisticRepository equalAmountStatisticRepository;

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
    public EqualAmountStatistic update(EqualAmountStatistic model) {
        // TODO: 业务逻辑
        return equalAmountStatisticRepository.save(model);
    }

    @Override
    public void delete(@NonNull Long id) {
        // TODO: 业务逻辑
        equalAmountStatisticRepository.delete(id);
    }
}
