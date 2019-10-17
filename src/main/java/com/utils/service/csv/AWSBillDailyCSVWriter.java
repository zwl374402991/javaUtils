package com.utils.service.csv;

import com.utils.module.CSVDemo;
import com.utils.utils.csv.AbCSVWriter;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * @author zm
 * @Date 2019/8/23
 */
public class AWSBillDailyCSVWriter extends AbCSVWriter<CSVDemo> {


    /**
     * 设置csv生成文件的表头
     */
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
    public Object[] t2Row(CSVDemo csvDemo) throws Exception {
        return new Object[] {
                csvDemo.getId(),
                csvDemo.getPayerAccountId(),
                csvDemo.getUsageAccountId(),
                csvDemo.getProductName(),
                csvDemo.getLocation(),
                csvDemo.getUsageType(),
                csvDemo.getLineItemDescription(),
                csvDemo.getOperation(),
                csvDemo.getUsageAmount(),
                csvDemo.getUnit(),
                csvDemo.getPublicOnDemandCost(),
                csvDemo.getCurrencyCode(),
                csvDemo.getUsageStartDate(),
                csvDemo.getUsageEndDate(),
                DateFormatUtils.format(csvDemo.getBillingStartTime(), "yyyy-MM-dd HH:mm:ss"),
                DateFormatUtils.format(csvDemo.getBillingEndTime(), "yyyy-MM-dd HH:mm:ss")
        };
    }

    @Override
    public Object[] getHeader() throws Exception {
        return headers;
    }

    /**
     * 生成csv文件名称
     * @param fileName
     * @param time
     * @return
     */
    public String getBillDailyCSVFilename(String fileName, String time) {
        StringBuilder sb = new StringBuilder();
        sb.append(fileName).append("_").append(time).append("_").append(".csv");
        return sb.toString();
    }
}
