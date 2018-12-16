package sdc.controller.billing;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.leadingsoft.bizfuse.common.web.dto.result.ResultDTO;
import com.leadingsoft.bizfuse.common.web.dto.result.PageResultDTO;

import sdc.convertor.billing.BillingCycleConvertor;
import sdc.dto.billing.BillingCycleDTO;
import sdc.model.billing.BillingCycle;
import sdc.service.billing.BillingCycleService;
import sdc.repository.billing.BillingCycleRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * BillingCycle的管理接口
 *
 * @author auto
 */
 @Slf4j
@RestController
@RequestMapping("/w/billingCycles")
@Api(tags = {"BillingCycle管理API" })
public class BillingCycleController {
    @Autowired
    private BillingCycleService billingCycleService;
	@Autowired
    private BillingCycleRepository billingCycleRepository;
    @Autowired
    private BillingCycleConvertor billingCycleConvertor;

    /**
     * 获取分页数据
     *
     * @param pageable 分页+排序参数
     * @return 分页数据
     */
    @Timed
    @ApiOperation(value = "获取分页数据", notes = "")
    @RequestMapping(value = "/s", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public PageResultDTO<BillingCycleDTO> search(final Pageable pageable) {
        final Page<BillingCycle> models = this.billingCycleRepository.findAll(pageable);
        return this.billingCycleConvertor.toResultDTO(models);
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
    public ResultDTO<BillingCycleDTO> get(@PathVariable final Long id) {
        final BillingCycle model = this.billingCycleService.get(id);
        return this.billingCycleConvertor.toResultDTO(model);
    }

    /**
     * 新建操作
     *
     * @param billingCycleDTO 新建资源的DTO
     * @return 新建资源
     */
    @Timed
    @ApiOperation(value = "新建操作", notes = "")
    @RequestMapping(method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResultDTO<BillingCycleDTO> create(@RequestBody @Valid final BillingCycleDTO billingCycleDTO) {
        final BillingCycle model = this.billingCycleConvertor.toModel(billingCycleDTO);
        this.billingCycleService.create(model);
        if (log.isInfoEnabled()) {
            log.info("{} instance {} was created.", BillingCycle.class.getSimpleName(), model.getId());
        }
        return this.billingCycleConvertor.toResultDTO(model);
    }
    
    /**
     * 更新操作
     *
     * @param id 更新资源的ID
     * @param billingCycleDTO 更新资源的DTO
     * @return 更新后资源
     */
    @Timed
    @ApiOperation(value = "更新操作", notes = "")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResultDTO<BillingCycleDTO> update(@PathVariable final Long id, @RequestBody @Valid final BillingCycleDTO billingCycleDTO) {
        billingCycleDTO.setId(id);
        final BillingCycle model = this.billingCycleConvertor.toModel(billingCycleDTO);
        this.billingCycleService.update(model);
        if (log.isInfoEnabled()) {
            log.info("{} instance {} was updated.", BillingCycle.class.getSimpleName(), model.getId());
        }
        return this.billingCycleConvertor.toResultDTO(model);
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
        this.billingCycleService.delete(id);
        if (log.isInfoEnabled()) {
            log.info("{} instance {} was deleted.", BillingCycle.class.getSimpleName(), id);
        }
        return ResultDTO.success();
    }
}
