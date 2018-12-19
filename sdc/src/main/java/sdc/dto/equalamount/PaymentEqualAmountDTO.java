package sdc.dto.equalamount;

import com.leadingsoft.bizfuse.common.web.dto.AbstractDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 还款
 */
@Getter
@Setter
public class PaymentEqualAmountDTO extends AbstractDTO {
    private long statisticId;
    private String equalAmountName;

    private List<ResultDTO> resultDTOS = new LinkedList<>();

    @Setter
    @Getter
    public static class ResultDTO{
        private long resultId;
        private int resultNo;
        private Date realRepaymentDate;
        private Boolean realPayment;
    }
}


