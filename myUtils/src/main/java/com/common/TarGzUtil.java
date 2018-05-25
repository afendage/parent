package com.common;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.tools.tar.TarOutputStream;

import java.io.*;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author LM
 * @version V1.0
 * @Title: TarGzUtil.java
 * @Description: gzip文件压缩和解压缩工具类
 * @date 2009-11-4 下午06:23:29
 */
public class TarGzUtil {

    /**
     * @param sources 要打包的原文件数组
     * @param target  打包后的文件
     * @return File    返回打包后的文件
     * @throws
     * @Title: pack
     * @Description: 将一组文件打成tar包
     */
    public static File pack(List<File> sources, File target) throws IOException {
        FileOutputStream out = new FileOutputStream(target);
        TarArchiveOutputStream os = new TarArchiveOutputStream(out);
        for (File file : sources) {
            try {
                os.setLongFileMode(TarOutputStream.LONGFILE_GNU);
                TarArchiveEntry tarEn = new TarArchiveEntry(file.getAbsoluteFile());
                tarEn.setName(file.getName());
                os.putArchiveEntry(tarEn);
                IOUtils.copy(new FileInputStream(file), os);
            } finally {
                os.closeArchiveEntry();
            }
        }
        if (os != null) {
            os.flush();
            os.close();
        }
        return target;
    }

    /**
     * @param source 需要压缩的文件
     * @return File    返回压缩后的文件
     * @throws
     * @Title: compress
     * @Description: 将文件用gzip压缩
     */
    public static File compress(File source) throws IOException {
        File target = new File(source.getName() + ".gz");
        FileInputStream in = null;
        GZIPOutputStream out = null;
        try {
            in = new FileInputStream(source);
            out = new GZIPOutputStream(new FileOutputStream(target));
            byte[] array = new byte[1024];
            int number = -1;
            while ((number = in.read(array, 0, array.length)) != -1) {
                out.write(array, 0, number);
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
        return target;
    }

    /**
     * 解压 Gzip 文件
     *
     * @param sourceDir
     */
    public static void unGzipFile(String sourceDir) throws IOException {
        String outputFile = "";
        FileInputStream fin = null;
        GZIPInputStream gzin = null;
        FileOutputStream fout = null;
        try {
            fin = new FileInputStream(sourceDir);
            gzin = new GZIPInputStream(fin);
            outputFile = sourceDir.substring(0, sourceDir.lastIndexOf('.'));
            outputFile = outputFile.substring(0, outputFile.lastIndexOf('.'));
            fout = new FileOutputStream(outputFile);
            int num;
            byte[] buf = new byte[1024];
            while ((num = gzin.read(buf, 0, buf.length)) != -1) {
                fout.write(buf, 0, num);
            }
        } finally {
            if (gzin != null) {
                gzin.close();
            }
            if (fout != null) {
                fout.close();
            }
            if (fin != null) {
                fin.close();
            }
        }
    }

    /**
     * 解压 tar.gz
     *
     * @param archive
     * @throws IOException
     * @date 2017年5月27日下午4:03:29
     */
    private static void unCompressArchiveGz(String archive) throws IOException {
        File file = new File(archive);
        String finalName;
        BufferedOutputStream bos = null;
        GzipCompressorInputStream gcis = null;
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            String fileName = file.getName().substring(0, file.getName().lastIndexOf("."));
            finalName = file.getParent() + File.separator + fileName;
            bos = new BufferedOutputStream(new FileOutputStream(finalName));
            gcis = new GzipCompressorInputStream(bis);
            byte[] buffer = new byte[1024];
            int read = -1;
            while ((read = gcis.read(buffer)) != -1) {
                bos.write(buffer, 0, read);
            }
        } finally {
            if(gcis!=null){
                gcis.close();
            }
            if(bos!=null){
                bos.close();
            }
        }

        unCompressTar(finalName);
    }

    /**
     * 解压tar
     *
     * @param finalName
     * @throws IOException
     * @author yutao
     * @date 2017年5月27日下午4:34:41
     */
    public static void unCompressTar(String finalName) throws IOException {
        File file = new File(finalName);
        String parentPath = file.getParent();
        TarArchiveInputStream tais=null;
        try {
            tais = new TarArchiveInputStream(new FileInputStream(file));
            TarArchiveEntry tarArchiveEntry = null;
            while ((tarArchiveEntry = tais.getNextTarEntry()) != null) {
                String name = tarArchiveEntry.getName();
                File tarFile = new File(parentPath, name);
                if (!tarFile.getParentFile().exists()) {
                    tarFile.getParentFile().mkdirs();
                }
                if (!tarFile.exists()) {

                }
                BufferedOutputStream bos=null;
                try {
                    bos = new BufferedOutputStream(new FileOutputStream(tarFile));
                    int read = -1;
                    byte[] buffer = new byte[1024];
                    while ((read = tais.read(buffer)) != -1) {
                        bos.write(buffer, 0, read);
                    }
                } finally {
                    if(bos!=null){
                        bos.close();
                    }
                }

            }
        } finally {
            if(tais!=null){
                tais.close();
            }
        }
        file.delete();//删除tar文件
    }
}