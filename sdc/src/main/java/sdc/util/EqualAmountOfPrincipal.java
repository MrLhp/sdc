package sdc.util;

import com.leadingsoft.bizfuse.common.web.utils.json.JsonUtils;
import org.junit.Test;
import sdc.bean.EqualAmountBean;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 等额本金
 */
public class EqualAmountOfPrincipal {
    /**
     * 等额本金计算
     *
     * @param quota              贷款额度
     * @param interestRate       贷款利率
     * @param interestRebate     利息折扣
     * @param dateOfLoan         贷款开始时间
     * @param stageNumberOfMonth 分期数
     */
    public static List<EqualAmountBean> execEqualAmountPrincipal(double quota, double interestRate, double interestRebate, Date dateOfLoan, int stageNumberOfMonth) {
        List<EqualAmountBean> result = new LinkedList<>();
        BigDecimal _quota = new BigDecimal(quota);
        BigDecimal _interestRate = new BigDecimal(interestRate);
        //求平均值
        BigDecimal avgVal = new BigDecimal(String.valueOf(_quota)).divide(new BigDecimal(stageNumberOfMonth), 2, RoundingMode.HALF_UP);
        //求尾差值
        BigDecimal tailVal = new BigDecimal(String.valueOf(_quota)).subtract(avgVal.multiply(BigDecimal.valueOf(stageNumberOfMonth - 1))).setScale(2);
        //日期Date转LocalDate
        LocalDate startDate = dateOfLoan.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        //记录账单周期天数,计算利息。eg: 2018-01-01至2018-02-01
        LocalDate beforeDate;

        //折扣
        if (interestRebate > 0) {
            _interestRate = _quota.multiply(BigDecimal.valueOf(interestRate))
                    .multiply(BigDecimal.valueOf(interestRebate)).setScale(2, RoundingMode.HALF_UP);
        } else {
            _interestRate = _quota.multiply(BigDecimal.valueOf(interestRate)).setScale(2, RoundingMode.HALF_UP);
        }

        for (int i = 1; i <= stageNumberOfMonth; i++) {
            EqualAmountBean equalAmountBean = new EqualAmountBean();
            beforeDate = startDate;
            startDate = startDate.plusMonths(1);
            //计算两个日期之间天数
            long days = beforeDate.until(startDate, ChronoUnit.DAYS);

            if (1 == i) {
                _quota = _quota.subtract(tailVal);
                equalAmountBean.setPrincipal(tailVal.multiply(BigDecimal.valueOf(100)).setScale(0, RoundingMode.HALF_UP).longValue());
                equalAmountBean.setRepaymentMoney(tailVal.add(_interestRate).setScale(2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)).setScale(0, RoundingMode.HALF_UP).longValue());
            } else {
                _quota = _quota.subtract(avgVal);
                equalAmountBean.setPrincipal(avgVal.multiply(BigDecimal.valueOf(100)).setScale(0, RoundingMode.HALF_UP).longValue());
                equalAmountBean.setRepaymentMoney(avgVal.add(_interestRate).setScale(2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)).setScale(0, RoundingMode.HALF_UP).longValue());
            }
            equalAmountBean.setDays(Math.toIntExact(days));
            equalAmountBean.setNo(i);
            equalAmountBean.setRepaymentDate(Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            equalAmountBean.setAmountMoney(_quota.multiply(BigDecimal.valueOf(100)).setScale(0, RoundingMode.HALF_UP).intValue());
            equalAmountBean.setInterest(_interestRate.multiply(BigDecimal.valueOf(100)).setScale(0, RoundingMode.HALF_UP).longValue());

            result.add(equalAmountBean);
        }

        return result;
    }

    @Test
    public void test() {
        List<EqualAmountBean> equalAmountBeans = this.execEqualAmountPrincipal(20000, 0.0062, 0.9, new Date(), 12);
        for (EqualAmountBean equalAmountBean : equalAmountBeans) {
            System.out.println(JsonUtils.pojoToJson(equalAmountBean));
        }
    }
}
