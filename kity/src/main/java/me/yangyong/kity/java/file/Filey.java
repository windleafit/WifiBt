package me.yangyong.kity.java.file;

import java.io.*;

/**
 * Created by YangYong on 2016/8/9 0009.
 */
public class Filey {

    public File getFile() {
        return file;
    }

    public void setFile(File newFile) {
        file = newFile;
    }

    public void renameTo(File newFile) {
        file.renameTo(newFile);
        file = newFile;
    }

    private File file;

    private FileOutputStream stepfos;

    public Filey(String dirName, String fileName) {
        file = new File(dirName + File.separator + fileName);
        createFile();
    }

    public Filey(String path) {
        file = new File(path);
        createFile();
    }

    public String getNameNoSuffix(){
        return file.getName().substring(0, file.getName().lastIndexOf("."));
    }

    public String getPathNoSuffix(){
        return file.getPath().substring(0, file.getPath().lastIndexOf("."));
    }

    public String getSuffix(){
        return file.getName().substring(file.getName().lastIndexOf(".") + 1);
    }

    private boolean createFile(){
        if(file.exists()) {
//            System.out.println("文件:"+file.getPath()+" 已存在！");
            return false; //目标文件已存在
        }
        if (file.getName().endsWith(File.separator)) {
//            System.out.println("目标文件不能为目录！");
            return false; //目标文件不能为目录
        }
        //判断目标文件所在的目录是否存在
        if(!file.getParentFile().exists()) {
            //如果目标文件所在的目录不存在，则创建父目录
//            System.out.println("目标文件所在目录不存在，准备创建它！");
            if(!file.getParentFile().mkdirs()) {
//                System.out.println("创建目标文件所在目录失败！");
                return false;
            }
        }
        //创建目标文件
        try {
            if (file.createNewFile()) {
//                System.out.println("创建单个文件" + file.getName() + "成功！");
                return true;
            } else {
//                System.out.println("创建单个文件" + file.getName() + "失败！");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
//            System.out.println("创建单个文件" + file.getName() + "失败！" + e.getMessage());
            return false;
        }

    }

    private boolean createDir(String dirName) {
        File dir = new File(dirName);
        if (dir.exists()) {
//            System.out.println("创建目录" + dirName + "失败，目标目录已经存在");
            return false;
        }
        if (!dirName.endsWith(File.separator)) {
            dirName = dirName + File.separator;
        }
        //创建目录
        if (dir.mkdirs()) {
//            System.out.println("创建目录" + dirName + "成功！");
            return true;
        } else {
//            System.out.println("创建目录" + dirName + "失败！");
            return false;
        }
    }


    public boolean close(){
        return false;
    }


    /**********************************************************************************
     *
     *                                  读数据
     *
     ************************************************************************************/

    /**
     * 读数据
     *
     * @throws IOException
     */
    public String read() throws IOException {
        FileInputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        fis.close();
        return new String(buffer, "UTF-8");
    }


    /**********************************************************************************
     *
     *                                  写入
     *
     ************************************************************************************/

    /**
     * 写文件
     *
     * @param content  内容
     * @param add      true追加/false覆盖
     * @throws IOException
     */
    public void write(String content, boolean add) throws IOException {
        FileOutputStream fos = new FileOutputStream(file, add);
        fos.write(content.getBytes());
        fos.close();
    }

    /**
     * 写文件并换行
     *
     * @param content  内容
     * @param add     true追加/false覆盖
     * @throws IOException
     */
    public void line(String content, boolean add) throws IOException {
        FileOutputStream fos = new FileOutputStream(file, add);
        fos.write((content + "\n").getBytes());
        fos.close();
    }


    /**
     * 写文件[尾部添加]
     *
     * @param content
     * @throws IOException
     */
    public void write(String content) throws IOException {
        write(content, true);
    }


    /**
     * 写文件[尾部添加]
     *
     * @param content
     * @throws IOException
     */
    public void add(String content) throws IOException {
        write(content, true);
    }

    /**
     * 写文件文件并换行[默认尾部添加]
     *
     * @param content  内容
     * @throws IOException
     */
    public void line(String content) throws IOException {
        FileOutputStream fos = new FileOutputStream(file, true);
        fos.write((content + "\n").getBytes());
        fos.close();
    }


    /**
     * 写文件[覆盖原文件]
     *
     * @param content
     * @throws IOException
     */
    public void cover(String content) throws IOException {
        write(content, false);
    }

    /**
     * 换行[默认追加模式]
     *
     * @throws IOException
     */
    public void line() throws IOException {
        FileOutputStream fos = new FileOutputStream(file, true);
        fos.write("\n".getBytes());
        fos.close();
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
     * @throws FileNotFoundException
     */
    public void stepReady() throws FileNotFoundException {
        stepfos = new FileOutputStream(file, true);//添加模式
    }


    /**
     * 步骤 - 写数据
     *
     * @param content 数据
     * @throws IOException
     */
    public void stepWrite(String content) throws IOException {
         stepfos.write(content.getBytes());
    }

    /**
     * 步骤 - 换行
     *
     * @throws IOException
     */
    public void stepLine() throws IOException {
        stepfos.write("\n".getBytes());
    }

    /**
     * 步骤 - 写数据并换行
     *
     * @param content 数据
     * @throws IOException
     */
    public void stepLine(String content) throws IOException {
        stepfos.write(content.getBytes());
        stepfos.write("\n".getBytes());
    }

    /**
     * 步骤 - 关闭
     *
     * @throws IOException
     */
    public void stepClose() throws IOException {
        stepfos.close();
        stepfos = null;
    }

}
