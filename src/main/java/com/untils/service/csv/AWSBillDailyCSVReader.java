package com.hy.bj.cn.reseller.utils.csv;

import com.hy.bj.cn.reseller.module.aws.AWSBillDaily;
import com.hy.bj.cn.reseller.module.aws.AWSConstant;
import org.apache.commons.csv.CSVRecord;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * @author zm
 * @Date 2019/8/23
 */
public class AWSBillDailyCSVReader extends AbCSVReader<AWSBillDaily>{


    @Override
    public AWSBillDaily row2T(CSVRecord csvRecord) throws Exception {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        AWSBillDaily awsBillDaily = new AWSBillDaily();
        Map<String,Date> dateMap = strToDate(csvRecord.get("identity/TimeInterval"));
        Date billingStartTime = dateMap.get(AWSConstant.BILLING_START_TIME);
        Date billingEndTime = dateMap.get(AWSConstant.BILLING_END_TIME);
        awsBillDaily.setBillingStartTime(billingStartTime);
        awsBillDaily.setBillingEndTime(billingEndTime);
        awsBillDaily.setPayerAccountId(csvRecord.get(AWSConstant.PAYER_ACCOUNT_ID));
        awsBillDaily.setUsageAccountId(csvRecord.get(AWSConstant.USAGE_ACCOUNT_ID));
        awsBillDaily.setProductName(csvRecord.get(AWSConstant.PRODUCT_NAME));
        awsBillDaily.setLocation(csvRecord.get(AWSConstant.LOCATION));
        awsBillDaily.setUsageType(csvRecord.get(AWSConstant.USAGE_TYPE));
        awsBillDaily.setLineItemDescription(csvRecord.get(AWSConstant.LINE_ITEM_DESCRIPTION));
        awsBillDaily.setUsageAmount(csvRecord.get(AWSConstant.USAGE_AMOUNT));
        awsBillDaily.setUnit(csvRecord.get(AWSConstant.UNIT));
        awsBillDaily.setPublicOnDemandCost(csvRecord.get(AWSConstant.PUBLIC_ON_DEMAND_COST));
        awsBillDaily.setCurrencyCode(csvRecord.get(AWSConstant.CURRENCY_CODE));
        awsBillDaily.setOperation(csvRecord.get("lineItem/Operation"));
        Date usageStartDate = Date.from(Instant.parse(csvRecord.get("lineItem/UsageStartDate")));
        Date usageEndDate = Date.from(Instant.parse(csvRecord.get("lineItem/UsageEndDate")));
        awsBillDaily.setUsageStartDate(sf.format(usageStartDate));
        awsBillDaily.setUsageEndDate(sf.format(usageEndDate));
        awsBillDaily.setLineItemId(csvRecord.get(AWSConstant.LINE_ITEM_ID));
        return awsBillDaily;
    }


    /**
     * 字符串转换成date类型
     * @param str
     * @return
     * @throws ParseException
     */
    private static Map<String, Date> strToDate(String str) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat(AWSConstant.FORMAT_YEAR_MONTH_DATE);
        String[] dates = str.split("\\/");
        Date billingStartTime = sf.parse((dates[0].split("T"))[0]);
        Date billingEndTime = sf.parse((dates[1].split("T"))[0]);
        Map<String, Date> map = new HashMap<String, Date>();
        map.put(AWSConstant.BILLING_START_TIME,billingStartTime);
        map.put(AWSConstant.BILLING_END_TIME,billingEndTime);
        return map;
    }
}
