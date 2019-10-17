package com.utils.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.Charsets;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtils.class);
    private static final int BUFFER_SIZE = 1024;

    /**
     * 
     * @param url
     * @param headers
     * @return
     * @throws IOException
     */
    public static String httpGet(String url, Map<String, String> headers) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpGet.addHeader(entry.getKey(), entry.getValue());
            }
        }
        CloseableHttpClient httpClient = HttpClients.custom().build();
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);

            StatusLine statusLine = response.getStatusLine();
            StringBuilder sb = new StringBuilder();
            sb.append("ProtocolVersion=").append(statusLine.getProtocolVersion().toString()).append(", ReasonPhrase=")
                    .append(statusLine.getReasonPhrase()).append(", StatusCode=").append(statusLine.getStatusCode());
            LOGGER.info(sb.toString());
            return EntityUtils.toString(response.getEntity());
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                LOGGER.error("", e);
            }
            httpGet.releaseConnection();
            try {
                httpClient.close();
            } catch (IOException e) {
                LOGGER.error("", e);
            }
        }
    }

    /**
     * 
     * @param url
     * @param headers
     * @param params
     * @return
     * @throws IOException
     */
    public static String httpPost(String url, Map<String, String> headers, Map<String, String> params)
            throws IOException {
        HttpPost httpPost = new HttpPost(url);
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPost.addHeader(entry.getKey(), entry.getValue());
            }
        }
        if (params != null) {
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(parameters, Charsets.UTF_8));
        }
        CloseableHttpClient httpClient = HttpClients.custom().build();
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);

            StatusLine statusLine = response.getStatusLine();
            StringBuilder sb = new StringBuilder();
            sb.append("ProtocolVersion=").append(statusLine.getProtocolVersion().toString()).append(", ReasonPhrase=")
                    .append(statusLine.getReasonPhrase()).append(", StatusCode=").append(statusLine.getStatusCode());
            LOGGER.info(sb.toString());
            return EntityUtils.toString(response.getEntity());
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                LOGGER.error("", e);
            }
            httpPost.releaseConnection();
            try {
                httpClient.close();
            } catch (IOException e) {
                LOGGER.error("", e);
            }
        }
    }

    /**
     * 
     * @param url
     * @param headers
     * @param obj
     * @return
     * @throws IOException
     */
    public static String httpPostJson(String url, Map<String, String> headers, Object obj) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPost.addHeader(entry.getKey(), entry.getValue());
            }
        }
        httpPost.setHeader("Content-Type", "application/json");
        if (obj != null) {
            httpPost.setEntity(new StringEntity(JSONObject.toJSONString(obj), Charsets.UTF_8));
        }
        CloseableHttpClient httpClient = HttpClients.custom().build();
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);

            StatusLine statusLine = response.getStatusLine();
            StringBuilder sb = new StringBuilder();
            sb.append("ProtocolVersion=").append(statusLine.getProtocolVersion().toString()).append(", ReasonPhrase=")
                    .append(statusLine.getReasonPhrase()).append(", StatusCode=").append(statusLine.getStatusCode());
            LOGGER.info(sb.toString());
            return EntityUtils.toString(response.getEntity());
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                LOGGER.error("", e);
            }
            httpPost.releaseConnection();
            try {
                httpClient.close();
            } catch (IOException e) {
                LOGGER.error("", e);
            }
        }
    }

    /**
     * 下载文件
     * 
     * @param url
     * @param filePath
     * @return
     * @throws IOException
     */
    public static File download(String url, String filePath) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpClient httpClient = HttpClients.custom().build();
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);

            StatusLine statusLine = response.getStatusLine();
            StringBuilder sb = new StringBuilder();
            sb.append("ProtocolVersion=").append(statusLine.getProtocolVersion().toString()).append(", ReasonPhrase=")
                    .append(statusLine.getReasonPhrase()).append(", StatusCode=").append(statusLine.getStatusCode());
            LOGGER.info(sb.toString());
            HttpEntity httpEntity = response.getEntity();
            long contentLength = httpEntity.getContentLength();
            LOGGER.info("ContentLength=" + contentLength);
            if (contentLength <= 0) {
                return null;
            }
            File file = new File(filePath);
            if (file.exists()) {
                LOGGER.info("delete : " + filePath);
                file.delete();
            }
            file.createNewFile();
            try (InputStream is = response.getEntity().getContent();
                    BufferedInputStream bis = new BufferedInputStream(is);
                    FileOutputStream fos = new FileOutputStream(file);
                    BufferedOutputStream bos = new BufferedOutputStream(fos);) {
                byte[] buffer = new byte[BUFFER_SIZE];
                int length = 0;
                while ((length = bis.read(buffer)) != -1) {
                    bos.write(buffer, 0, length);
                }
            }
            return file;
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                LOGGER.error("", e);
            }
            httpGet.releaseConnection();
            try {
                httpClient.close();
            } catch (IOException e) {
                LOGGER.error("", e);
            }
        }
    }

    /**
     * 提供下载文件功能
     * 
     * @param response
     * @param file
     * @throws IOException
     */
    public static void httpDownloadFile(HttpServletResponse response, File file) throws IOException {
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Pragma", "no-cache");
        response.addHeader("Expires", "0");
        response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.addHeader("Content-Disposition", "attachment; filename=" + file.getName());
        byte[] bs = new byte[1024];
        try (ServletOutputStream sos = response.getOutputStream();
             FileInputStream fis = new FileInputStream(file);
             BufferedInputStream bis = new BufferedInputStream(fis);) {
            int len = bis.read(bs);
            while (len != -1) {
                sos.write(bs, 0, len);
                len = bis.read(bs);
            }
        }
    }
}
