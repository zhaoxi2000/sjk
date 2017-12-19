package com.kingsoft.sjk.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wuzhenyu
 * @author LinZuXiong
 * @since 2012-07-25
 */
public final class FileOPHelper {
    private static final Logger logger = LoggerFactory.getLogger(FileOPHelper.class);

    /**
     * 删除文件、目录
     * 
     * @param filepath
     * @throws IOException
     */
    public static void del(String filepath) throws IOException {
        File f = new File(filepath);
        if (f.exists() && f.isDirectory()) {
            if (f.listFiles().length == 0)
                f.delete();
            else {
                File delFile[] = f.listFiles();
                int i = f.listFiles().length;
                for (int j = 0; j < i; j++) {
                    if (delFile[j].isDirectory())
                        del(delFile[j].getAbsolutePath());
                    delFile[j].delete();
                }
            }
        } else if (f.exists() && !f.isDirectory()) {
            f.delete();
        }
    }

    /**
     * 写文件
     * 
     * @param path
     * @param content
     */
    public static void write(String path, String content) {
        String s = null;
        try {
            File f = new File(path);
            StringBuilder s1 = new StringBuilder((int) (f.length() + content.length() + 2));
            if (!f.exists())
                f.createNewFile();
            BufferedReader input = new BufferedReader(new FileReader(f));
            while ((s = input.readLine()) != null) {
                // s1 += s + "\n";
                s1.append(s).append("\n");
            }
            input.close();
            // s1 += content;
            s1.append(content);
            BufferedWriter output = new BufferedWriter(new FileWriter(f));
            output.write(s1.toString());
            output.close();
        } catch (Exception e) {
            logger.error("Exception", e);
        }
    }

    /**
     * 读文件
     * 
     * @param file
     * @return
     */
    public static String read(String file) {
        String s = null;
        StringBuilder sb = new StringBuilder();
        File f = new File(file);
        if (f.exists()) {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
                while ((s = br.readLine()) != null)
                    sb.append(s);
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

            }
        }
        return sb.toString();
    }
}
