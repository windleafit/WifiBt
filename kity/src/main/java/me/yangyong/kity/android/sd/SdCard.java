package me.yangyong.kity.android.sd;

import android.os.Environment;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import me.yangyong.kity.java.file.Copy;
import me.yangyong.kity.java.file.FileMethod;

/**
 * 如果需要在程序中使用sdcard进行数据的存储，那么需要在AndroidMainfset.xml文件中
 * 进行权限的配置：
 * <!-- 在sd中创建和删除文件的权限 -->
 * <uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS" />
 * <p/>
 * <!-- 向SD卡中的写入权限 -->
 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 * 复制代码
 * 小贴士：
 * 在对SD卡进行读写的时候会用到的知识点：
 * Environment类的静态方法：
 * getDataDirectory();                                获取android中的data目录；
 * getExternalStorgeDirectory();                获取到外部存储的目录一般指SD卡；
 * getDownloadCacheDirectory();                获取到下载的缓存目录；
 * getExternalStorageState();                获取外部设置的当前状态，sd卡
 * <p/>
 * android对于外部的设置状态，我们比较常用的应该是MEDIA_MOUNTED（sd卡存在并且可以进行读写 ）
 * getRootDirectory();                                 获取到Android Root路径；
 * isExternalStorageEmulated()；                返回Boolean值判断外部设置是否有效；
 * isExternalStorageRemovable();                判断外部设置是否可以移除；
 * <p/>
 * Android中外部设置的状态情况如下：
 * MEDIA_MOUNTED                                sd卡中可以进行读写；
 * MEDIA_MOUNTED_READ_ONLY        sd卡        存在，只可以进行读的操作
 */
public class SdCard implements FileMethod.Copy {

    public SdCard() {
        mDetail = new Detail();
    }

    private FileOutputStream fos;

    //获取SD卡的根目录
    public static String sdCardRoot = Environment.getExternalStorageDirectory().getAbsolutePath();

    /**
     * 判断SDCard是否存在 [当没有外挂SD卡时，内置ROM也被识别为存在sd卡]
     *
     * @return
     */
    public static boolean isSdCardExist() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * SdCard可读
     *
     * @return
     */
    public static boolean isSdCardReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    /**
     * SdCard可读可写
     *
     * @return
     */
    public static boolean isSdCardWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }


    /**
     * 在SD卡上创建目录
     *
     * @param dir
     * @return
     */
    public File createDir(String dir) {
        File dirFile = new File(sdCardRoot + File.separator + dir + File.separator);
        dirFile.mkdirs();
        return dirFile;
    }

    /**
     * 在SD卡上创建文件
     *
     * @param fileName
     * @param dir
     * @return
     * @throws IOException
     */
    public File createFile(String dir, String fileName) throws IOException {
        File file = new File(sdCardRoot + File.separator + dir + File.separator + fileName);
        file.createNewFile();
        return file;
    }

    /**
     * 判断SD卡上文件是否存在
     */
    public boolean isFileExist(String dir, String fileName) {
        File file = new File(sdCardRoot + File.separator + dir + File.separator + fileName);
        return file.exists();
    }

    /**
     * 判断SD卡上文件是否存在
     */
    public boolean isFileExist(String path) {
        File file = new File(sdCardRoot + File.separator + path);
        return file.exists();
    }


    /**********************************************************************************
     *
     *                                  写入
     *
     ************************************************************************************/

    /**
     * 写文件[从头开始]
     *
     * @param fileName 文件名
     * @param content  内容
     * @param add      追加
     * @throws IOException
     */
    public void write(String fileName, String content, boolean add) throws IOException {
        if (!isSdCardWritable()) {
            return;
        }
        File file = new File(Environment.getExternalStorageDirectory(), fileName);
        FileOutputStream fos = new FileOutputStream(file, add);
        fos.write(content.getBytes());
        fos.close();

    }

    /**
     * 写文件[尾部添加]
     *
     * @param fileName
     * @param content
     * @throws IOException
     */
    public void add(String fileName, String content) throws IOException {
        write(fileName, content, true);
    }


    /**
     * 写文件[覆盖原文件]
     *
     * @param fileName
     * @param content
     * @throws IOException
     */
    public void cover(String fileName, String content) throws IOException {
        write(fileName, content, false);
    }


    /**********************************************************************************
     *
     *                                  分步写入
     *
     ************************************************************************************/

    /* ready -> write...... -> close */

    /**
     * 步骤 - 准备(追加模式)
     *
     * @param fileName 文件名
     * @throws FileNotFoundException
     */
    public void stepReady(String fileName) throws FileNotFoundException {
        File file = new File(Environment.getExternalStorageDirectory(), fileName);
        fos = new FileOutputStream(file, true);//添加模式
    }

    /**
     * 步骤 - 准备
     *
     * @param fileName 文件名
     * @param add      追加模式
     * @throws FileNotFoundException
     */
    public void stepReady(String fileName, boolean add) throws FileNotFoundException {
        File file = new File(Environment.getExternalStorageDirectory(), fileName);
        fos = new FileOutputStream(file, add);
    }

    /**
     * 步骤 - 写数据
     *
     * @param content 数据
     * @throws IOException
     */
    public void stepWrite(String content) throws IOException {
        if (isSdCardWritable())
            fos.write(content.getBytes());
    }

    /**
     * 步骤 - 写数据并换行
     *
     * @param content 数据
     * @throws IOException
     */
    public void stepWriteLine(String content) throws IOException {
        if (isSdCardWritable()) {
            fos.write(content.getBytes());
            fos.write("\n".getBytes());
        }
    }

    /**
     * 步骤 - 换行
     *
     * @throws IOException
     */
    public void stepLine() throws IOException {
        if (isSdCardWritable())
            fos.write("\n".getBytes());
    }

    /**
     * 步骤 - 关闭
     *
     * @throws IOException
     */
    public void stepClose() throws IOException {
        fos.close();
    }


    /**********************************************************************************
     *
     *                                  读数据
     *
     ************************************************************************************/

    /**
     * 读数据
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    public String read(String fileName) throws IOException {
        if (!isSdCardWritable()) {
            return null;
        }
        File file = new File(Environment.getExternalStorageDirectory(), fileName);
        FileInputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        fis.close();
        return new String(buffer, "UTF-8");
    }


    @Override
    public void copyFile(File fromFile, File toFile) throws IOException {
        Copy.copyFile(sdCardRoot + File.separator + fromFile, sdCardRoot + File.separator + toFile);
    }

    @Override
    public void copyFile(String fromPath, String toPath) throws IOException {
        Copy.copyFile(sdCardRoot + File.separator + fromPath, sdCardRoot + File.separator + toPath);
    }

    @Override
    public void copyDir(String fromDir, String toDir) throws IOException {
        copyDir(fromDir, toDir, null, true);
    }

    @Override
    public void copyDir(String fromDir, String toDir, FileFilter filter, boolean copySubDir) throws IOException {
        Runnable runnable = new CopyDirRunnable(fromDir, toDir, filter, copySubDir);
        Thread thread = new Thread(runnable);
        thread.start();
    }


    private class CopyDirRunnable implements Runnable {
        String fromDir;
        String toDir;
        FileFilter filter;
        boolean copySubDir;

        public CopyDirRunnable(String fromDir, String toDir, FileFilter filter, boolean copySubDir) {
            this.fromDir = fromDir;
            this.toDir = toDir;
            this.filter = filter;
            this.copySubDir = copySubDir;
        }

        @Override
        public void run() {
            try {
                Copy.copyDir(sdCardRoot + File.separator + fromDir, sdCardRoot + File.separator + toDir, filter, copySubDir);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Detail mDetail;

    private Progress mProgress;

    public void setProgress(Progress progress) {
        mProgress = progress;
    }

    public interface Progress {
        void detail(Detail detail);
    }


    public class Detail {
        public Method method;
        public File file;
        public int curSize;
        public int allSize;
        public int curNum;
        public int allNum;
        public int curFileName;

    }

    public enum Method {
        Copy, Move, Delete
    }
}
