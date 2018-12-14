package sdc.service.equalamount.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leadingsoft.bizfuse.common.web.exception.CustomRuntimeException;
import sdc.service.equalamount.EqualAmountResultService;
import sdc.model.equalamount.EqualAmountResult;
import sdc.repository.equalamount.EqualAmountResultRepository;
import lombok.NonNull;

/**
 * EqualAmountResultService 实现类
 */
@Service
@Transactional
public class EqualAmountResultServiceImpl implements EqualAmountResultService {

    @Autowired
    private EqualAmountResultRepository equalAmountResultRepository;

    @Override
    @Transactional(readOnly = true)
    public EqualAmountResult get(@NonNull Long id) {
        final  EqualAmountResult model = equalAmountResultRepository.findOne(id);
        if (model == null) {
            throw new CustomRuntimeException("404", String.format("查找的资源[%s]不存在.", id));
        }
        return model;
    }

    @Override
    public EqualAmountResult create(EqualAmountResult model) {
        // TODO: 业务逻辑
        return equalAmountResultRepository.save(model);
    }

    @Override
    public EqualAmountResult update(EqualAmountResult model) {
        return equalAmountResultRepository.save(model);
    }

    @Override
    public void delete(@NonNull Long id) {
        // TODO: 业务逻辑
        equalAmountResultRepository.delete(id);
    }
}
