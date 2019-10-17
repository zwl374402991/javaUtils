package com.utils.controller;

import com.alibaba.fastjson.JSONObject;
import com.utils.service.csv.CSVUtilsService;
import com.utils.utils.HttpUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/csv")
@Api("csv 解析")
public class CSVUtilsController {

    @Autowired
    CSVUtilsService csvUtilsService;

    @GetMapping("/say")
    public String test () {
        return "hello world";
    }

    @GetMapping("/readCSV")
    public JSONObject readCSVFile(@RequestParam("filePath") String filePath){
        return csvUtilsService.readCSVFile(filePath);
    }

    @GetMapping("/writerCSV")
    public JSONObject writerAWSBill(){
        JSONObject jsonObject = new JSONObject();
        csvUtilsService.writerAWSBill();
        jsonObject.put("code",200);
        jsonObject.put("msg","success");
        return jsonObject;
    }

    @GetMapping("/downloadCSV")
    public void downloadCSVFile(HttpServletResponse response) {
        File file = csvUtilsService.writerAWSBill();
        try {
            HttpUtils.httpDownloadFile(response, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
