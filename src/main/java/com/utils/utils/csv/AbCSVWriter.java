package com.utils.utils.csv;

import com.utils.utils.BufferedFileWriter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;

/**
 * csv生成基类
 * 
 * @author
 */
public abstract class AbCSVWriter<T> {

    /**
     * 
     * @param fileName
     *            文件名
     * @param charset
     *            字符集
     * @param tList
     * @return
     * @throws Exception
     */
    public File write(String fileName, Charset charset, List<T> tList) throws Exception {
        File file = new File(fileName);
        try (BufferedFileWriter bfw = new BufferedFileWriter(file, charset, false);
             CSVPrinter csvPrinter = new CSVPrinter(bfw, CSVFormat.DEFAULT);) {
            csvPrinter.printRecord(getHeader());
            for (T t : tList) {
                csvPrinter.printRecord(t2Row(t));
            }
        }
        return file;
    }

    /**
     * 
     * @return
     * @throws Exception
     */
    public abstract Object[] getHeader() throws Exception;

    /**
     * 
     * @param t
     * @return
     * @throws Exception
     */
    public abstract Object[] t2Row(T t) throws Exception;
}
