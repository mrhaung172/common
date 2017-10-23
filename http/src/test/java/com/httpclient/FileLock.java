package com.httpclient;

/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/


import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;


public class FileLock implements AutoCloseable
{
    private String lockPath;
    private FileOutputStream lockStream;

    public FileLock(final String id) throws IOException {
        this(System.getProperty("java.io.tmpdir"), id);
        this.lock(0L);
    }

    public FileLock(final String lockDir, final String id) throws FileNotFoundException {
        this.lockPath = new File(lockDir, id + ".lock").toString();
        this.lockStream = new FileOutputStream(this.lockPath, true);
    }

    public void lock(final long waitTimeoutSecs) throws IOException {
        final long timeoutNanos = TimeUnit.SECONDS.toNanos(waitTimeoutSecs);
        final long start = System.nanoTime();
        while (this.lockStream.getChannel().tryLock() == null) {
            try {
                Thread.sleep(1000L);
            }
            catch (InterruptedException e) {
                throw new FileLockException("Failed to obtain lock on " + this.lockPath, e);
            }
            if (System.nanoTime() - start >= timeoutNanos) {
                throw new FileLockException("Failed to obtain lock on " + this.lockPath + " within " + waitTimeoutSecs + " seconds");
            }
        }
        final long elapsed = System.nanoTime() - start;
        if (elapsed > TimeUnit.SECONDS.toNanos(60L)) {
            System.out.println(((Object)("Waited " + TimeUnit.NANOSECONDS.toSeconds(elapsed) + " seconds to obtain lock " + this.lockPath)));;
        }
        final String pidHost = ManagementFactory.getRuntimeMXBean().getName() + "\n";
        this.lockStream.getChannel().truncate(0L);
        this.lockStream.write(pidHost.getBytes());
        this.lockStream.getChannel().force(false);
    }

    public void release() {
        try {
            this.lockStream.close();
        }
        catch (IOException e) {
            System.out.println((Object)e);
        }
    }

    @Override
    public void close() {
        this.release();
    }

    public static class FileLockException extends IOException
    {
        private static final long serialVersionUID = -6827101220375630932L;

        public FileLockException() {
        }

        public FileLockException(final String message) {
            super(message);
        }

        public FileLockException(final String message, final Throwable cause) {
            super(message, cause);
        }
    }



    public static void main(String[] args) {
        try(FileLock filelock = new FileLock("D:\\test\\","huangjun222")) {
            filelock.lock(100);
            Thread.sleep(90000000);

        } catch (Exception e) {
           e.printStackTrace();
            System.out.println("faild");
        }
    }
}
