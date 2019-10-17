package com.utils.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

/**
 * Reader封装类
 * 
 * @author
 */
public class BufferedFileReader extends Reader {
    protected final FileInputStream fis;
    protected final InputStreamReader isr;
    protected final BufferedReader br;

    public BufferedFileReader(File file, Charset charset) throws IOException {
        fis = new FileInputStream(file);
        isr = new InputStreamReader(fis, charset);
        br = new BufferedReader(isr);
    }

    public BufferedFileReader(String file, Charset charset) throws IOException {
        fis = new FileInputStream(file);
        isr = new InputStreamReader(fis, charset);
        br = new BufferedReader(isr);
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        return br.read(cbuf, off, len);
    }

    /**
     * Reads a line of text. A line is considered to be terminated by any one of a
     * line feed ('\n'), a carriage return ('\r'), or a carriage return followed
     * immediately by a linefeed.
     *
     * @return A String containing the contents of the line, not including any
     *         line-termination characters, or null if the end of the stream has
     *         been reached
     *
     * @exception IOException If an I/O error occurs
     *
     * @see java.nio.file.Files#readAllLines
     */
    public String readLine() throws IOException {
        return br.readLine();
    }

    @Override
    public void close() throws IOException {
        br.close();
        isr.close();
        fis.close();
    }
}
