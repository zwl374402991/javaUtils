package com.untils.service.csv;

import com.alibaba.fastjson.JSONObject;
import com.untils.module.CSVDemo;
import com.untils.untils.BufferedFileReader;
import org.apache.commons.codec.Charsets;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class CSVUtilsService {

    private static final Logger log = LoggerFactory.getLogger(CSVUtilsService.class);

    /**
     * csv文件解析
     */
    public JSONObject readCSVFile(String filePath){
        log.info(" begin");
        //filePath = "C:\\archerzhang\\saber\\workspaceAll\\test\\untils\\src\\main\\resources\\csvDemo\\rider-00001.csv";
        AWSBillDailyCSVReader awsBillDailyCSVReader = new AWSBillDailyCSVReader();
        try (BufferedFileReader bfr = new BufferedFileReader(filePath, Charsets.UTF_8);
             CSVParser csvParser = new CSVParser(bfr, CSVFormat.DEFAULT.withFirstRecordAsHeader());) {
            List<CSVDemo> list = new ArrayList<CSVDemo>();
            for (CSVRecord csvRecord : csvParser) {
                CSVDemo csvDemo = awsBillDailyCSVReader.row2T(csvRecord);
                list.add(csvDemo);
            }
            log.info("list:"+list);
            JSONObject vo = new JSONObject();
            vo.put("code",200);
            vo.put("msg","success");
            vo.put("data",list);
            return vo;
        } catch (Exception e) {
            throw new RuntimeException(""+e);
        }
    }

    /**
     * 生成csv文件
     * @return
     */
    public File writerAWSBill(){
        log.info("downAWSBill begin");
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        JSONObject jsonObject = readCSVFile("C:\\archerzhang\\saber\\workspaceAll\\test\\untils\\src\\main\\resources\\csvDemo\\rider-00001.csv");
        List list = (List) jsonObject.get("data");
        String filePath = "C:\\archerzhang\\saber\\workspaceAll\\test\\untils\\src\\main\\resources\\csvDemo\\";
        String time = sf.format(new Date());
        AWSBillDailyCSVWriter awsBillDailyCSVWriter = new AWSBillDailyCSVWriter();
        String fileName = filePath
                + awsBillDailyCSVWriter.getBillDailyCSVFilename("aws", time);
        try {
            return awsBillDailyCSVWriter.write(fileName, Charsets.UTF_8, list);
        } catch (Exception e) {
            throw new RuntimeException("" + e);
        }
    }


}
