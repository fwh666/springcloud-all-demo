package club.fuwenhao.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @author zby
 */
public class FileUtil {

    private FileUtil() {
    }

    private static final Logger log = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 写入磁盘
     *
     * @param file
     * @param filePath
     * @param fileName
     * @return void
     * @author fwh [2021/2/5 && 5:03 下午]
     */
    public static void writeToDisk(byte[] file, String filePath, String fileName) {
        File targetFile = new File(filePath + fileName);
        File parentFile = targetFile.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }

        FileOutputStream out = null;
        try {
            if (targetFile.createNewFile()) {
                log.error("文件已经存在，未创建");
            }
            out = new FileOutputStream(filePath + fileName);
            out.write(file);
            out.flush();
        } catch (Exception e) {
            log.error("文件写入出现异常：{}", e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    log.error("流关闭异常：{}", e);
                }
            }
        }
    }

    /**
     * 删除文件
     *
     * @param fileName
     * @return boolean
     * @author fwh [2021/2/5 && 5:02 下午]
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            return file.delete();
        } else {
            return false;
        }
    }

    /**
     * 随机UUID
     *
     * @param fileName
     * @return java.lang.String
     * @author fwh [2021/2/5 && 5:02 下午]
     */
    public static String renameToUUID(String fileName) {
        return UUID.randomUUID() + "." + fileName.substring(fileName.lastIndexOf('.') + 1);
    }

    /**
     * 删除服务上的文件
     *
     * @param filePath 路径
     * @return
     */
    public static boolean deleteServerFile(String filePath) {
        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            return file.delete();
        }
        return false;

    }


    /**
     * 删除文件夹
     *
     * @param folderPath
     * @return void
     * @author fwh [2021/2/5 && 5:02 下午]
     */
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            File myFilePath = new File(filePath);
            //删除空文件夹
            if (myFilePath.delete()) {
                log.info("文件夹删除成功：{}", myFilePath.getName());
            }
        } catch (Exception e) {
            log.info("文件夹删除出现异常！", e);
        }
    }

    /**
     * 删除指定文件夹下的所有文件
     *
     * @return
     * @author fwh [2021/2/5 && 5:02 下午]
     */
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile() && temp.delete()) {
                log.info("文件删除成功：{}", temp.getName());
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }
}
