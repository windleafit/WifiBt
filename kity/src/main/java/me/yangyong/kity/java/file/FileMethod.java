package me.yangyong.kity.java.file;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

/**
 * Created by JOUAV on 2015/11/16.
 */
public abstract class FileMethod {

    public interface Read {
        void read(File file);

        void read(String path);
    }

    public interface Write {
        void write(File file);

        void write(String path);
    }

    /**
     * 复制 文件/文件夹
     */
    public interface Copy {
        void copyFile(File fromFile, File toFile) throws IOException;

        void copyFile(String fromPath, String toPath) throws IOException;

        void copyDir(String fromDir, String toDir) throws IOException;

        void copyDir(String fromDir, String toDir, FileFilter filter, boolean copySubDir) throws IOException;
    }

    public interface Move {
        void move(File fromFile, File toFile);

        void move(String fromPath, String toPath);
    }

    public interface Delete {
        void delete(File file);

        void delete(String path);
    }


}
