package com.untils.untils.csv;

import com.untils.untils.BufferedFileReader;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * csv解析基类
 * 
 * @author
 */
public abstract class AbCSVReader<T> {

    /**
     * 
     * @param fileName 文件名
     * @param charset  字符集
     * @return 实体类集合
     * @throws IOException
     */
    public List<T> read(String fileName, Charset charset) throws Exception {
        try (BufferedFileReader bfr = new BufferedFileReader(fileName, charset);
             CSVParser csvParser = new CSVParser(bfr, CSVFormat.DEFAULT);) {
            List<T> list = new ArrayList<T>();
            for (Iterator<CSVRecord> iterator = csvParser.iterator(); iterator.hasNext();) {
                list.add(row2T(iterator.next()));
            }
            return list;
        }
    }

    /**
     * 
     * @param csvRecord
     * @return 实体
     */
    public abstract T row2T(CSVRecord csvRecord) throws Exception;
}
