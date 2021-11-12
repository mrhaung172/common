package com.httpUtils;

import com.httpclient.HttpUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.CharSet;
import org.apache.coyote.http2.Http2Protocol;

import org.apache.http.Header;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.io.DefaultHttpRequestParser;
import org.apache.http.io.HttpMessageParser;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.message.BasicLineParser;
import org.apache.http.message.LineParser;
import org.apache.http.message.ParserCursor;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.CharsetUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.HttpMessageConverter;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpHeaderAnalyse {
    static String head =
            "accept: */*\n" +
                    "accept-encoding: gzip, deflate, br\n" +
                    "accept-language: zh-CN,zh;q=0.9\n" +
                    "content-length: 3470\n" +
                    "content-type: application/x-www-form-urlencoded\n" +
                    "origin: https://search.jd.com\n" +
                    "referer: https://search.jd.com/Search?keyword=123&enc=utf-8&wq=&pvid=d9accb3be4284c0290d167fa4d1c393c\n" +
                    "user-agent: Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.25 Mobile Safari/537.36";

    public static void main(String[] args) throws IOException {
        StringBuffer stringBuffer = new StringBuffer(head);
        List<String> liens = IOUtils.readLines(IOUtils.toInputStream(head, "UTF-8"));
        LineParser lineParser = BasicLineParser.INSTANCE;
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(1024);
        liens.stream().forEach(i -> {
            charArrayBuffer.clear();
            charArrayBuffer.append(i);

            Header header = lineParser.parseHeader(charArrayBuffer);
        });

    }

    public static HttpHeaders convertHeaders(String headersStr) {
        try {
            HttpHeaders headers = new HttpHeaders();
            StringBuffer stringBuffer = new StringBuffer(headersStr);
            List<String> liens = IOUtils.readLines(new StringReader(headersStr));
            LineParser lineParser = BasicLineParser.INSTANCE;
            CharArrayBuffer charArrayBuffer = new CharArrayBuffer(1024);
            liens.stream().forEach(i -> {
                charArrayBuffer.clear();
                charArrayBuffer.append(i);
                Header header = lineParser.parseHeader(charArrayBuffer);
                headers.add(header.getName(), header.getValue());
            });
            return headers;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }


    public static Map<String, String> convertHeadersToMap(String headersStr) {
        try {
            Map headers = new HashMap();
            StringBuffer stringBuffer = new StringBuffer(headersStr);
            List<String> liens = IOUtils.readLines(new StringReader(headersStr));
            LineParser lineParser = BasicLineParser.INSTANCE;
            CharArrayBuffer charArrayBuffer = new CharArrayBuffer(1024);
            liens.stream().forEach(i -> {
                charArrayBuffer.clear();
                charArrayBuffer.append(i);
                Header header = lineParser.parseHeader(charArrayBuffer);
                headers.put(header.getName(), header.getValue());

            });
            return headers;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

}
