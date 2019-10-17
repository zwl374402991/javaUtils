package com.utils.service.csv;

import com.utils.module.CSVDemo;
import com.utils.utils.csv.AbCSVReader;
import org.apache.commons.csv.CSVRecord;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author
 * @Date
 */
public class AWSBillDailyCSVReader extends AbCSVReader<CSVDemo> {


    /**
     * 解析AWS的CSV账单文件
     * @param csvRecord
     *      csvRecord.get("匹配csv文件表头字段")
     *      csvRecord.get(int) 根据表头下标进行匹配
     * @return
     * @throws Exception
     */
    @Override
    public CSVDemo row2T(CSVRecord csvRecord) throws Exception {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CSVDemo csvDemo = new CSVDemo();
        Map<String,Date> dateMap = strToDate(csvRecord.get(AWSConstant.IDENTITY_TIME_INTERVAL));
        Date billingStartTime = dateMap.get(AWSConstant.BILLING_START_TIME);
        Date billingEndTime = dateMap.get(AWSConstant.BILLING_END_TIME);
        csvDemo.setBillingStartTime(billingStartTime);
        csvDemo.setBillingEndTime(billingEndTime);
        csvDemo.setPayerAccountId(csvRecord.get(AWSConstant.PAYER_ACCOUNT_ID));
        csvDemo.setUsageAccountId(csvRecord.get(AWSConstant.USAGE_ACCOUNT_ID));
        csvDemo.setProductName(csvRecord.get(AWSConstant.PRODUCT_NAME));
        csvDemo.setLocation(csvRecord.get(AWSConstant.LOCATION));
        csvDemo.setUsageType(csvRecord.get(AWSConstant.USAGE_TYPE));
        csvDemo.setLineItemDescription(csvRecord.get(AWSConstant.LINE_ITEM_DESCRIPTION));
        csvDemo.setUsageAmount(csvRecord.get(AWSConstant.USAGE_AMOUNT));
        csvDemo.setUnit(csvRecord.get(AWSConstant.UNIT));
        csvDemo.setPublicOnDemandCost(csvRecord.get(AWSConstant.PUBLIC_ON_DEMAND_COST));
        csvDemo.setCurrencyCode(csvRecord.get(AWSConstant.CURRENCY_CODE));
        csvDemo.setOperation(csvRecord.get(AWSConstant.LINEITEM_OPERATION));
        Date usageStartDate = Date.from(Instant.parse(csvRecord.get(AWSConstant.LINEITEM_USAGE_START_DATE)));
        Date usageEndDate = Date.from(Instant.parse(csvRecord.get(AWSConstant.LINEITEM_USAGE_END_DATE)));
        csvDemo.setUsageStartDate(sf.format(usageStartDate));
        csvDemo.setUsageEndDate(sf.format(usageEndDate));
        csvDemo.setLineItemId(csvRecord.get(AWSConstant.LINE_ITEM_ID));
        return csvDemo;
    }

    /**
     * 字符串转换成date类型
     * @param str
     * @return
     * @throws ParseException
     */
    private static Map<String, Date> strToDate(String str) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String[] dates = str.split("\\/");
        Date billingStartTime = sf.parse((dates[0].split("T"))[0]);
        Date billingEndTime = sf.parse((dates[1].split("T"))[0]);
        Map<String, Date> map = new HashMap<String, Date>();
        map.put(AWSConstant.BILLING_START_TIME,billingStartTime);
        map.put(AWSConstant.BILLING_END_TIME,billingEndTime);
        return map;
    }


    public static class AWSConstant {
        static final String IDENTITY_TIME_INTERVAL = "identity/TimeInterval";
        static final String BILLING_START_TIME = "billingStartTime";
        static final String BILLING_END_TIME = "billingEndTime";
        static final String LINE_ITEM_ID = "identity/LineItemId";
        static final String PAYER_ACCOUNT_ID = "bill/PayerAccountId";
        static final String USAGE_ACCOUNT_ID = "lineItem/UsageAccountId";
        static final String PRODUCT_NAME = "product/ProductName";
        static final String LOCATION = "product/location";
        static final String USAGE_TYPE = "lineItem/UsageType";
        static final String LINE_ITEM_DESCRIPTION = "lineItem/LineItemDescription";
        static final String USAGE_AMOUNT = "lineItem/UsageAmount";
        static final String UNIT = "pricing/unit";
        static final String PUBLIC_ON_DEMAND_COST = "pricing/publicOnDemandCost";
        static final String CURRENCY_CODE = "lineItem/CurrencyCode";
        static final String LINEITEM_OPERATION = "lineItem/Operation";
        static final String LINEITEM_USAGE_START_DATE = "lineItem/UsageStartDate";
        static final String LINEITEM_USAGE_END_DATE = "lineItem/UsageEndDate";
    }
}
