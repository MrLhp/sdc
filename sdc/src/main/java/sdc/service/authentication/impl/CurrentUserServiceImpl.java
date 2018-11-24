package sdc.service.authentication.impl;

import com.leadingsoft.bizfuse.common.webauth.access.CurrentUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sdc.repository.authentication.UserRepository;

@Component
public class CurrentUserServiceImpl implements CurrentUserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public Object loadUserByUsername(String s) {
        return userRepository.findOneByNo(s);
    }
}
