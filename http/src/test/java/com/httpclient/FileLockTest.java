package com.httpclient;

import java.lang.management.ManagementFactory;

public class FileLockTest {
    public static void main(String[] args) {

        try (FileLock filelock = new FileLock("C:\\Users\\huang\\Desktop\\pp100web\\target\\classes\\channel\\sougou","sougou-data-xml")) {
            filelock.lock(2);
            Thread.sleep(1000000);
        } catch (Exception e) {
            System.out.println("faild");
        }
    }
}
