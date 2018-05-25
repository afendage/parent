package com.common;

import org.junit.Test;

import java.io.*;
import java.net.URL;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 压缩工具类
 */
public class GzipUtils {
    /**
     * 压缩
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] gzip(byte[] data)throws Exception{
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(bos);
        gzip.write(data);
        gzip.finish();
        gzip.close();
        byte[] ret = bos.toByteArray();
        bos.close();
        return ret;
    }

    /**
     * 解压
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] ungzip(byte[] data)throws Exception{
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        GZIPInputStream gzip = new GZIPInputStream(bis);
        byte[] buf = new byte['?'];
        int num = -1;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((num = gzip.read(buf, 0, buf.length)) != -1) {
            bos.write(buf, 0, num);
        }
        gzip.close();
        bis.close();
        byte[] ret = bos.toByteArray();
        bos.flush();
        bos.close();
        return ret;
    }


    @Test
    public void test()throws Exception{
        URL url = GzipUtils.class.getClassLoader().getResource("006.jpg");
        File file = new File(url.getFile());
        FileInputStream in = new FileInputStream(file);
        byte[] data = new byte[in.available()];
        in.read(data);
        in.close();

        System.out.println("文件原始大小:" + data.length);

        byte[] ret1 = gzip(data);
        System.out.println("压缩后大小:" + ret1.length);

        byte[] ret2 = ungzip(ret1);
        System.out.println("还原后大小:" + ret2.length);

        String writePath = System.getProperty("user.dir") + File.separatorChar + "receive" + File.separatorChar + "006_1.jpg";
        FileOutputStream fos = new FileOutputStream(writePath);
        fos.write(ret2);
        fos.close();
    }
}
