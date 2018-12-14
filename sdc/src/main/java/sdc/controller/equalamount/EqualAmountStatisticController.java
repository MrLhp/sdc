package sdc.controller.equalamount;

import javax.validation.Valid;

import com.leadingsoft.bizfuse.common.web.utils.json.JsonUtils;
import com.leadingsoft.bizfuse.common.webauth.annotation.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.leadingsoft.bizfuse.common.web.dto.result.ResultDTO;
import com.leadingsoft.bizfuse.common.web.dto.result.PageResultDTO;

import sdc.convertor.equalamount.EqualAmountStatisticConvertor;
import sdc.dto.equalamount.EqualAmountPreviewDTO;
import sdc.dto.equalamount.EqualAmountStatisticDTO;
import sdc.dto.equalamount.PaymentEqualAmountDTO;
import sdc.model.authentication.User;
import sdc.model.equalamount.EqualAmountResult;
import sdc.model.equalamount.EqualAmountStatistic;
import sdc.repository.equalamount.EqualAmountResultRepository;
import sdc.service.equalamount.EqualAmountStatisticService;
import sdc.repository.equalamount.EqualAmountStatisticRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * EqualAmountStatistic的管理接口
 *
 * @author auto
 */
 @Slf4j
@RestController
@RequestMapping("/w/equalAmountStatistics")
@Api(tags = {"EqualAmountStatistic管理API" })
public class EqualAmountStatisticController {
    @Autowired
    private EqualAmountStatisticService equalAmountStatisticService;
	@Autowired
    private EqualAmountStatisticRepository equalAmountStatisticRepository;
    @Autowired
    private EqualAmountStatisticConvertor equalAmountStatisticConvertor;

    /**
     * 获取分页数据
     *
     * @param pageable 分页+排序参数
     * @return 分页数据
     */
    @Timed
    @ApiOperation(value = "获取分页数据", notes = "")
    @RequestMapping(value = "/s", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public PageResultDTO<EqualAmountStatisticDTO> search(final Pageable pageable) {
        final Page<EqualAmountStatistic> models = this.equalAmountStatisticRepository.findAll(pageable);
        return this.equalAmountStatisticConvertor.toResultDTO(models);
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
    public ResultDTO<EqualAmountStatisticDTO> get(@PathVariable final Long id) {
        final EqualAmountStatistic model = this.equalAmountStatisticService.get(id);
        return this.equalAmountStatisticConvertor.toResultDTO(model);
    }

    /**
     * 新建操作
     *
     * @param previewDTO 新建资源的DTO
     * @return 新建资源
     */
    @Timed
    @ApiOperation(value = "新建操作", notes = "")
    @RequestMapping(method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResultDTO<Void> create(@RequestBody @Valid final EqualAmountPreviewDTO previewDTO, @CurrentUser User user) {

        final EqualAmountStatistic model = this.equalAmountStatisticConvertor.toModel(previewDTO,user);

        this.equalAmountStatisticService.create(model);

        return ResultDTO.success();
    }
    
    /**
     * 更新操作
     *
     * @param dto 更新资源的DTO
     * @return 更新后资源
     */
    @Timed
    @ApiOperation(value = "更新操作", notes = "")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResultDTO<Void> update(@RequestBody @Valid final PaymentEqualAmountDTO dto) {
        EqualAmountStatistic model = this.equalAmountStatisticRepository.findOne(dto.getId());
        if (model != null) {
            this.equalAmountStatisticService.update(model,dto);
        }
        return ResultDTO.success();
    }

    /**
     * 删除操作
     *
     * @param id 资源ID
     * @return 操作结果
     */
    @Timed
    @ApiOperation(value = "删除操作", notes = "")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResultDTO<Void> delete(@PathVariable final Long id) {
        this.equalAmountStatisticService.delete(id);
        if (log.isInfoEnabled()) {
            log.info("{} instance {} was deleted.", EqualAmountStatistic.class.getSimpleName(), id);
        }
        return ResultDTO.success();
    }
}
