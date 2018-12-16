package sdc.controller.billing;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.leadingsoft.bizfuse.common.web.dto.result.ResultDTO;

import sdc.convertor.billing.BillingRecordsConvertor;
import sdc.dto.billing.BillingRecordsDTO;
import sdc.model.billing.BillingRecords;
import sdc.service.billing.BillingRecordsService;
import sdc.repository.billing.BillingRecordsRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * BillingRecords的管理接口
 *
 * @author auto
 */
 @Slf4j
@RestController
@RequestMapping("/w/billingRecords")
@Api(tags = {"BillingRecords管理API" })
public class BillingRecordsController {
    @Autowired
    private BillingRecordsService billingRecordsService;
	@Autowired
    private BillingRecordsRepository billingRecordsRepository;
    @Autowired
    private BillingRecordsConvertor billingRecordsConvertor;

    /**
     * 获取分页数据
     *
     * @return 分页数据
     */
    @Timed
    @ApiOperation(value = "获取分页数据", notes = "")
    @RequestMapping(value = "/s/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultDTO<List<BillingRecords>> search(@PathVariable final Long id) {
        final List<BillingRecords> models = this.billingRecordsRepository.findAllByBillingCycle_Id(id);
        return ResultDTO.success(models);
    }

    /**
     * 取得详细数据
     *
     * @param id 资源ID
     * @return 资源详细
     */
    @Timed
    @ApiOperation(value = "获取详细数据", notes = "")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResultDTO<BillingRecordsDTO> get(@PathVariable final Long id) {
        final BillingRecords model = this.billingRecordsService.get(id);
        return this.billingRecordsConvertor.toResultDTO(model);
    }

    /**
     * 新建操作
     *
     * @param billingRecordsDTO 新建资源的DTO
     * @return 新建资源
     */
    @Timed
    @ApiOperation(value = "新建操作", notes = "")
    @RequestMapping(method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResultDTO<BillingRecordsDTO> create(@RequestBody @Valid final BillingRecordsDTO billingRecordsDTO) {
        final BillingRecords model = this.billingRecordsConvertor.toModel(billingRecordsDTO);
        this.billingRecordsService.create(model);
        if (log.isInfoEnabled()) {
            log.info("{} instance {} was created.", BillingRecords.class.getSimpleName(), model.getId());
        }
        return this.billingRecordsConvertor.toResultDTO(model);
    }
    
    /**
     * 更新操作
     *
     * @param id 更新资源的ID
     * @param billingRecordsDTO 更新资源的DTO
     * @return 更新后资源
     */
    @Timed
    @ApiOperation(value = "更新操作", notes = "")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResultDTO<BillingRecordsDTO> update(@PathVariable final Long id, @RequestBody @Valid final BillingRecordsDTO billingRecordsDTO) {
        billingRecordsDTO.setId(id);
        final BillingRecords model = this.billingRecordsConvertor.toModel(billingRecordsDTO);
        this.billingRecordsService.update(model);
        if (log.isInfoEnabled()) {
            log.info("{} instance {} was updated.", BillingRecords.class.getSimpleName(), model.getId());
        }
        return this.billingRecordsConvertor.toResultDTO(model);
    }

    /**
     * 删除操作
     *
     * @param Id 资源ID
     * @return 操作结果
     */
    @Timed
    @ApiOperation(value = "删除操作", notes = "")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResultDTO<Void> delete(@PathVariable final Long id) {
        this.billingRecordsService.delete(id);
        if (log.isInfoEnabled()) {
            log.info("{} instance {} was deleted.", BillingRecords.class.getSimpleName(), id);
        }
        return ResultDTO.success();
    }
}
