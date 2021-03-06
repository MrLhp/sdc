package sdc.service.impl;

import com.leadingsoft.bizfuse.common.web.exception.CustomRuntimeException;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sdc.model.School;
import sdc.repository.SchoolRepository;
import sdc.service.SchoolService;

/**
 * SchoolService 实现类
 */
@Service
@Transactional
public class SchoolServiceImpl implements SchoolService {

    @Autowired
    private SchoolRepository schoolRepository;

    @Override
    @Transactional(readOnly = true)
    public School get(@NonNull Long id) {
        final  School model = schoolRepository.findOne(id);
        if (model == null) {
            throw new CustomRuntimeException("404", String.format("查找的资源[%s]不存在.", id));
        }
        return model;
    }

    @Override
    public School create(School model) {
        // TODO: 业务逻辑
        return schoolRepository.save(model);
    }

    @Override
    public School update(School model) {
        // TODO: 业务逻辑
        return schoolRepository.save(model);
    }

    @Override
    public void delete(@NonNull Long id) {
        // TODO: 业务逻辑
        schoolRepository.delete(id);
    }
}
