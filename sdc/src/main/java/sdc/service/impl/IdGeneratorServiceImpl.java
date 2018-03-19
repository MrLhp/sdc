package sdc.service.impl;

import com.leadingsoft.bizfuse.common.web.utils.id.DefaultIdGenerator;
import com.leadingsoft.bizfuse.common.web.utils.id.IdGenerator;
import org.springframework.stereotype.Service;
import sdc.service.IdGeneratorService;

import javax.annotation.PostConstruct;


@Service
public class IdGeneratorServiceImpl implements IdGeneratorService {

    private IdGenerator userNoGenerator;
    
    /**
     * 初始化
     */
    @PostConstruct
    public void init() {
    	userNoGenerator = new DefaultIdGenerator(0, "U");
    }

    @Override
    public String generateUserNo() {
        return this.userNoGenerator.generateCode();
    }

    public static void main(String[] args) {
    	System.out.println(new DefaultIdGenerator(0, "U").generateCode());
    }
}
