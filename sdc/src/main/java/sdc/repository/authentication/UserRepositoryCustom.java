package sdc.repository.authentication;

import com.leadingsoft.bizfuse.common.web.support.Searchable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sdc.model.authentication.User;

import java.util.List;


public interface UserRepositoryCustom {

	Page<User> searchAll(Searchable searchable, Pageable pageable);
	
	List<User> searchAll(Searchable searchable);
}
