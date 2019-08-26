package com.hy.bj.cn.reseller.utils.csv;

import com.hy.bj.cn.reseller.module.aws.AWSBillDaily;
import com.hy.bj.cn.reseller.utils.DatePatterns;
import org.apache.commons.lang3.time.DateFormatUtils;
/**
 * @author zm
 * @Date 2019/8/23
 */
public class AWSBillDailyCSVWriter extends AbCSVWriter<AWSBillDaily> {



    private final Object[] headers = {
            "Bill Id",
            "AWS Payer Account Id",
            "AWS Usage Account Id",
            "Product Name",
            "Location",
            "Usage Type",
            "Description",
            "Operation",
            "Usage Amount",
            "Unit",
            "Public OnDemand Cost",
            "Currency Code",
            "Usage Start Date",
            "Usage End Date",
            "Billing Start Date",
            "Billing End DATE" };


    @Override
    public Object[] t2Row(AWSBillDaily awsBillDaily) throws Exception {
        return new Object[] {
                awsBillDaily.getId(),
                awsBillDaily.getPayerAccountId(),
                awsBillDaily.getUsageAccountId(),
                awsBillDaily.getProductName(),
                awsBillDaily.getLocation(),
                awsBillDaily.getUsageType(),
                awsBillDaily.getLineItemDescription(),
                awsBillDaily.getOperation(),
                awsBillDaily.getUsageAmount(),
                awsBillDaily.getUnit(),
                awsBillDaily.getPublicOnDemandCost(),
                awsBillDaily.getCurrencyCode(),
                awsBillDaily.getUsageStartDate(),
                awsBillDaily.getUsageEndDate(),
                DateFormatUtils.format(awsBillDaily.getBillingStartTime(), DatePatterns.YMDHMS),
                DateFormatUtils.format(awsBillDaily.getBillingEndTime(), DatePatterns.YMDHMS)
        };
    }

    @Override
    public Object[] getHeader() throws Exception {
        return headers;
    }

    /**
     *
     * @param type
     *            aliyun/aws/huaweicloud
     * @param userId
     * @param startdate
     * @param enddate
     * @return
     */
    public String getBillDailyCSVFilename(String type, String userId, String startdate, String enddate) {
        StringBuilder sb = new StringBuilder();
        sb.append(type).append("_").append(userId).append(startdate).append("_").append(enddate).append(".csv");
        return sb.toString();
    }
}
