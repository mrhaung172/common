package com.httpclient;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;

public class HttpTest {

    public static void main(String[] args) throws URISyntaxException {
        HttpGet httpget = new HttpGet(
                "http://www.google.com/search?hl=en&q=httpclient&btnG=Google+Search&aq=f&oq=");
        //HttpClient提供URIBuilder实用类用于简化request URIS的创建和修改。
        URI uri = new URIBuilder()
                .setScheme("https")
                .setHost("www.google.com")
                .setPath("/search")
                .setParameter("q", "httpclient")
                .setParameter("btnG", "Google Search")
                .setParameter("aq", "f")
                .setParameter("oq", "")
                .build();
        HttpGet httpget1 = new HttpGet(uri);
        System.out.println(httpget1.getURI());
    }
}
