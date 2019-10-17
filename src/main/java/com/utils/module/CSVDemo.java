package com.utils.module;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CSVDemo {

    private Integer id;
    private String lineItemId;
    private String payerAccountId;
    private String usageAccountId;
    private String productName;
    private String location;
    private String usageType;
    private String lineItemDescription;
    private String operation;
    private String usageAmount;
    private String unit;
    private String publicOnDemandCost;
    private String currencyCode;
    private String awsBillTaskId;
    private String usageStartDate;
    private String usageEndDate;
    private Date billingStartTime;
    private Date billingEndTime;
}
