package sdc.controller.equalamount;

import javax.validation.Valid;

import com.leadingsoft.bizfuse.common.web.utils.json.JsonUtils;
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

import sdc.bean.EqualAmountBean;
import sdc.convertor.equalamount.EqualAmountResultConvertor;
import sdc.dto.equalamount.EqualAmountPreviewDTO;
import sdc.dto.equalamount.EqualAmountResultDTO;
import sdc.dto.equalamount.EqualAmountStatisticDTO;
import sdc.enums.EqualAmountType;
import sdc.model.equalamount.EqualAmountResult;
import sdc.service.equalamount.EqualAmountResultService;
import sdc.repository.equalamount.EqualAmountResultRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * EqualAmountResult的管理接口
 *
 * @author auto
 */
 @Slf4j
@RestController
@RequestMapping("/w/equalAmountResults")
@Api(tags = {"EqualAmountResult管理API" })
public class EqualAmountResultController {
    @Autowired
    private EqualAmountResultService equalAmountResultService;
	@Autowired
    private EqualAmountResultRepository equalAmountResultRepository;
    @Autowired
    private EqualAmountResultConvertor equalAmountResultConvertor;

    /**
     * 获取分页数据
     *
     * @param pageable 分页+排序参数
     * @return 分页数据
     */
    @Timed
    @ApiOperation(value = "获取分页数据", notes = "")
    @RequestMapping(value = "/s", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public PageResultDTO<EqualAmountResultDTO> search(final Pageable pageable) {
        final Page<EqualAmountResult> models = this.equalAmountResultRepository.findAll(pageable);
        return this.equalAmountResultConvertor.toResultDTO(models);
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
    public ResultDTO<EqualAmountResultDTO> get(@PathVariable final Long id) {
        final EqualAmountResult model = this.equalAmountResultService.get(id);
        return this.equalAmountResultConvertor.toResultDTO(model);
    }

    @RequestMapping(value = "/detail/{id}",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public PageResultDTO<EqualAmountResultDTO> getByStatisticId(final Pageable pageable, @PathVariable final Long id) {
        final Page<EqualAmountResult> models = this.equalAmountResultRepository.findAllByEqualAmountStatistic_Id(pageable, id);
        return this.equalAmountResultConvertor.toResultDTO(models);
    }

    /**
     * 新建操作
     *
     * @param equalAmountResultDTO 新建资源的DTO
     * @return 新建资源
     */
    @Timed
    @ApiOperation(value = "新建操作", notes = "")
    @RequestMapping(method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResultDTO<EqualAmountResultDTO> create(@RequestBody @Valid final EqualAmountResultDTO equalAmountResultDTO) {
        final EqualAmountResult model = this.equalAmountResultConvertor.toModel(equalAmountResultDTO);
        this.equalAmountResultService.create(model);
        if (log.isInfoEnabled()) {
            log.info("{} instance {} was created.", EqualAmountResult.class.getSimpleName(), model.getId());
        }
        return this.equalAmountResultConvertor.toResultDTO(model);
    }
    
    /**
     * 更新操作
     *
     * @param id 更新资源的ID
     * @param equalAmountResultDTO 更新资源的DTO
     * @return 更新后资源
     */
    @Timed
    @ApiOperation(value = "更新操作", notes = "")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResultDTO<EqualAmountResultDTO> update(@PathVariable final Long id, @RequestBody @Valid final EqualAmountResultDTO equalAmountResultDTO) {
        equalAmountResultDTO.setId(id);
        final EqualAmountResult model = this.equalAmountResultConvertor.toModel(equalAmountResultDTO);
        this.equalAmountResultService.update(model);
        if (log.isInfoEnabled()) {
            log.info("{} instance {} was updated.", EqualAmountResult.class.getSimpleName(), model.getId());
        }
        return this.equalAmountResultConvertor.toResultDTO(model);
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
        this.equalAmountResultService.delete(id);
        if (log.isInfoEnabled()) {
            log.info("{} instance {} was deleted.", EqualAmountResult.class.getSimpleName(), id);
        }
        return ResultDTO.success();
    }

    /**
     * 获取资金预算
     * @param dto
     * @return
     */
    @RequestMapping(value = "/preview",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultDTO<List<EqualAmountBean>> getEqualAmountPreview(@RequestBody @Valid final EqualAmountPreviewDTO dto) {
        List<EqualAmountBean> equalAmountResult;
        if (dto.getEqualAmountType().equals(EqualAmountType.interest)) {
            equalAmountResult = this.equalAmountResultConvertor.toInterestPreview(dto);
        } else {
            equalAmountResult = this.equalAmountResultConvertor.toPrincipalPreview(dto);
        }
        return ResultDTO.success(equalAmountResult);

    }
}
