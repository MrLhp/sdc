package sdc.service.equalamount;

import sdc.dto.equalamount.PaymentEqualAmountDTO;
import sdc.model.equalamount.EqualAmountStatistic;

/**
 * EqualAmountStatisticService
 */
public interface EqualAmountStatisticService {

    /**
     * 根据ID获取资源
     *
     * @param id 资源实例ID
     * @return Id所指向的资源实例
     * @throws 当Id所指向的资源不存在时，抛CustomRuntimeException异常
     */
    EqualAmountStatistic get(Long id);

    /**
     * 创建
     *
     * @param model 资源实例
     * @return 创建后的对象
     */
    EqualAmountStatistic create(EqualAmountStatistic model);

    /**
     * 更新
     *
     * @param model 编辑后的资源实例
     * @param dto
     * @return 修改后的对象
     */
    EqualAmountStatistic update(EqualAmountStatistic model, PaymentEqualAmountDTO dto);
    
    /**
     * 删除
     *
     * @param id 资源实例ID
     */
    void delete(Long id);

}
