package sdc.dto.equalamount;
import com.leadingsoft.bizfuse.common.web.dto.AbstractDTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.boot.model.source.spi.EmbeddableSource;
import sdc.enums.EqualAmountSource;
import sdc.enums.EqualAmountType;
import sdc.model.authentication.User;
import sdc.model.equalamount.EqualAmountResult;

import javax.persistence.ManyToOne;
import java.util.List;

/**
 * 贷款统计
 */
@Data
public class EqualAmountStatisticDTO extends AbstractDTO {
    @ManyToOne
    private User user;
    private List<EqualAmountResult> result;

    /**
     * 贷款名称
     */
    @ApiModelProperty("贷款名称")
    private String equalAmountName;
    /**
     * 待还总金额
     */
    @ApiModelProperty("待还总金额")
    private long amountTotalMoney;
    /**
     * 贷款类型
     */
    @ApiModelProperty("贷款类型")
    private EqualAmountType equalAmountType;
    /**
     * 贷款来源
     */
    @ApiModelProperty("贷款来源")
    private EqualAmountSource equalAmountSource;
}
