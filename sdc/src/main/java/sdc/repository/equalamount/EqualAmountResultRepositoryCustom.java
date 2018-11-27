package sdc.repository.equalamount;

import com.leadingsoft.bizfuse.common.web.support.Searchable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sdc.model.equalamount.EqualAmountResult;

public interface EqualAmountResultRepositoryCustom {
    Page<EqualAmountResult> searchPage(Pageable pageable, Searchable searchable);
}
