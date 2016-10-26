package me.yangyong.kity.java.file;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Created by JOUAV on 2015/11/16.
 */
public class Copy {


    /**
     * 复制文件[使用文件通道的方式，速度快]
     *
     * @param fromFile
     * @param toFile
     */
    public static void copyFile(File fromFile, File toFile) throws IOException {

        if (!fromFile.exists()) {
            throw new IOException("fromFile is not exist!");
        }

        //判断是否为文件
        if (!fromFile.isFile()) {
            throw new IOException(fromFile.getPath() + " is not a file");
        }

        //目标文件夹不存在
        File toFileDir = toFile.getParentFile();
        if (toFileDir != null && !toFileDir.exists()) {
            toFileDir.mkdirs();
        }

        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel in = null;
        FileChannel out = null;
        try {
            fis = new FileInputStream(fromFile);
            fos = new FileOutputStream(toFile);
            in = fis.getChannel();//得到对应的文件通道
            out = fos.getChannel();//得到对应的文件通道
            in.transferTo(0, in.size(), out);//连接两个通道，并且从in通道读取，然后写入out通道
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                fis.close();
                in.close();
                fos.close();
                out.close();
            } catch (IOException e) {
                throw e;
            }
        }
    }

    /**
     * 复制文件
     *
     * @param fromFile
     * @param to
     * @throws IOException
     */
    public static void copyFile(File fromFile, String to) throws IOException {
        File toFile = new File(to);
        copyFile(fromFile, toFile);
    }

    /**
     * 复制文件
     *
     * @param from 被复制的文件路径
     * @param to   复制到的新文件路径
     */
    public static void copyFile(String from, String to) throws IOException {
        File fromFile = new File(from);
        copyFile(fromFile, to);
    }


    /**
     * 复制文件夹
     *
     * @param fromDir
     * @param toDir
     */
    public static void copyDir(String fromDir, String toDir) throws IOException {
        copyDir(fromDir, toDir, null, true);
    }

    /**
     * 复制文件夹
     *
     * @param fromDir    源文件夹
     * @param toDir      目标文件夹
     * @param filter     过滤器
     * @param copySubDir 是否复制子文件夹
     * @throws IOException
     */
    public static void copyDir(String fromDir, String toDir, FileFilter filter, boolean copySubDir) throws IOException {
        File fromFile = new File(fromDir);
        copyDir(fromFile, toDir, filter, copySubDir);
    }

    public static void copyDir(File fromFile, String toDir, FileFilter filter, boolean copySubDir) throws IOException {

        //判断文件夹是否存在
        if (!fromFile.exists()) {
            throw new IOException(fromFile.getPath() + " is not exist!");
        }

        //判断是否为文件夹
        if (!fromFile.isDirectory()) {
            throw new IOException(fromFile.getPath() + " is not a directory!");
        }

        File[] fromFiles;
        if (filter == null) {
            fromFiles = fromFile.listFiles();
        } else {
            fromFiles = fromFile.listFiles(filter);
        }


        //循环
        for (File file : fromFiles) {

            //文件夹
            if (file.isDirectory()) {
                //复制子文件夹
                if (copySubDir) {
                    copyDir(file, toDir + File.separator + file.getName(), filter, copySubDir);
                } else {
                    continue;
                }
            }
            //文件
            else {
                //复制单个文件
                copyFile(file, toDir + File.separator + file.getName());
            }

        }
    }
}
