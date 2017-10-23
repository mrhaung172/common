package com.thread;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExcutorTest {

    public static ExecutorService excutor = Executors.newFixedThreadPool(100);

    public void excutorTest() {

        for (int i = 0; i < 5000; i++) {
            String s = i + "";
            excutor.submit(() -> {

                try {

                    Request.Get("http://127.0.0.1:9000/test").execute();

                } catch (ClientProtocolException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally {
                }


            });

        }
        System.out.println("OK");

    }


    public static void main(String[] args) {
        ExcutorTest s = new ExcutorTest();
        s.excutorTest();
    }


}
