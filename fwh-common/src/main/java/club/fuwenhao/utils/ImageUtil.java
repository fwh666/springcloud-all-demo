package club.fuwenhao.utils;

import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.Base64;

public class ImageUtil {

    private static final Logger log = LoggerFactory.getLogger(ImageUtil.class);

    /**
     *
     * 功能描述:
     * 〈下载图片并转换base64〉
     * @param imgUrl
     * @return java.lang.String
     * @author zby
     * @date 2020/12/29 10:15
     */
    public static String imageToBase64ByUrl(String imgUrl) throws IOException {
        URL url = new URL(imgUrl);
        BufferedImage bufferedImage = ImageIO.read(url);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (StringUtils.contains(imgUrl,".jpg")){
            ImageIO.write(bufferedImage, "jpg", baos);
        }else if (StringUtils.contains(imgUrl,".png")){
            ImageIO.write(bufferedImage, "png", baos);
        }else if (StringUtils.contains(imgUrl,".jpeg")){
            ImageIO.write(bufferedImage, "jpeg", baos);
        }
        // 关闭流
        if (baos != null) {
            try {
                baos.flush();
                baos.close();
            } catch (IOException e) {

            }
        }
        return getBase64String(baos.toByteArray());
    }

    /**
     * 直接指定压缩后的宽高：
     * (先保存原文件，再压缩、上传)
     * 壹拍项目中用于二维码压缩
     *
     * @param oldFile   要进行压缩的文件全路径
     * @param width     压缩后的宽度
     * @param height    压缩后的高度
     * @param quality   压缩质量
     * @param smallIcon 文件名的小小后缀(注意，非文件后缀名称),入压缩文件名是yasuo.jpg,则压缩后文件名是yasuo(+smallIcon).jpg
     * @return 返回压缩后的文件的全路径
     */
    public static String zipImageFile(String oldFile, int width, int height,
                                      float quality, String smallIcon) {
        if (oldFile == null) {
            return null;
        }
        String newImage = null;
        try {
            /** 对服务器上的临时文件进行处理 */
            BufferedImage srcFile = ImageIO.read(new File(oldFile));
            /** 宽,高设定 */
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            tag.getGraphics().drawImage(srcFile, 0, 0, width, height, null);
            String filePrex = oldFile.substring(0, oldFile.indexOf('.'));
            /** 压缩后的文件名 */
            newImage = filePrex + smallIcon + oldFile.substring(filePrex.length());
            /** 压缩之后临时存放位置 */
            FileOutputStream out = new FileOutputStream(newImage);
//			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//			JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
            /** 压缩质量 */
//			jep.setQuality(quality, true);
            ImageIO.write(tag, "jpg", out);
            out.flush();
//			encoder.encode(tag, jep);
//			out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newImage;
    }

    /**
     * 保存文件到服务器临时路径(用于文件上传)
     *
     * @param fileName
     * @param is
     * @return 文件全路径
     */

    public static String writeFile(String fileName, InputStream is) {
        if (fileName == null || fileName.trim().length() == 0) {
            return null;
        }
        try {
            /** 首先保存到临时文件 */
            FileOutputStream fos = new FileOutputStream(fileName);
            byte[] readBytes = new byte[512];// 缓冲大小
            int readed = 0;
            while ((readed = is.read(readBytes)) > 0) {
                fos.write(readBytes, 0, readed);
            }
            fos.close();
            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    /**
     * 等比例压缩算法：
     * 算法思想：根据压缩基数和压缩比来压缩原图，生产一张图片效果最接近原图的缩略图
     *
     * @param srcURL  原图地址
     * @param deskURL 缩略图地址
     * @param comBase 压缩基数
     * @param scale   压缩限制(宽/高)比例 一般用1：
     *                当scale>=1,缩略图height=comBase,width按原图宽高比例;若scale<1,缩略图width=comBase,height按原图宽高比例
     * @throws Exception
     */
    public static void saveMinPhoto(String srcURL, String deskURL, double comBase,
                                    double scale) throws Exception {
//		File srcFile = new File(srcURL);
        BufferedImage src = ImageIO.read(new URL(srcURL));
        int srcHeight = src.getHeight(null);
        int srcWidth = src.getWidth(null);
        // 缩略图高
        int deskHeight = 0;
        // 缩略图宽
        int deskWidth = 0;
        double srcScale = (double) srcHeight / srcWidth;
        /** 缩略图宽高算法 */
        if ((double) srcHeight > comBase || (double) srcWidth > comBase) {
            if (srcScale >= scale || 1 / srcScale > scale) {
                if (srcScale >= scale) {
                    deskHeight = (int) comBase;
                    deskWidth = srcWidth * deskHeight / srcHeight;
                } else {
                    deskWidth = (int) comBase;
                    deskHeight = srcHeight * deskWidth / srcWidth;
                }
            } else {
                if ((double) srcHeight > comBase) {
                    deskHeight = (int) comBase;
                    deskWidth = srcWidth * deskHeight / srcHeight;
                } else {
                    deskWidth = (int) comBase;
                    deskHeight = srcHeight * deskWidth / srcWidth;
                }
            }
        } else {
            deskHeight = srcHeight;
            deskWidth = srcWidth;
        }
        BufferedImage tag = new BufferedImage(deskWidth, deskHeight, BufferedImage.TYPE_3BYTE_BGR);
        // 绘制缩小后的图
        tag.getGraphics().drawImage(src, 0, 0, deskWidth, deskHeight, null);
        // 输出到文件流
        FileOutputStream deskImage = new FileOutputStream(deskURL);

        ImageIO.write(tag, "jpg", deskImage);
        deskImage.close();
//        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(deskImage);
//        encoder.encode(tag); // 近JPEG编码
//        deskImage.close();
    }

    /**
     * 功能描述:
     * 图片压缩工具类
     *
     * @param filePath 图片地址
     * @param comBase  宽度
     * @return
     * @Author: zhangbingyang3
     * @Date: 2020/7/7 16:27
     */
    public static String getCompressionImage(String filePath, int comBase) throws IOException {
        MultipartFile file;
        InputStream fis = null;
        try {
            if (!StringUtil.isEmpty(filePath)) {
                URL url = new URL(filePath);
                fis = url.openStream();
                file = new MockMultipartFile("file", "test.jpg", "multipart/form-data", fis);
                //io流
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                Thumbnails.of(file.getInputStream()).size(comBase, comBase).outputQuality(1f).toOutputStream(baos);
                //转换成字节
                byte[] bytes = baos.toByteArray();
                String base64 = getBase64String(bytes);
                return base64;
            }
        } catch (IOException e) {
            e.getMessage();
            log.error("压缩图片异常：{}", e.getMessage());
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
        return null;
    }

    public static String getMinBase64(String srcURL, double comBase, double scale) throws Exception {
//		File srcFile = new File(srcURL);
        BufferedImage src = ImageIO.read(new URL(srcURL));
        int srcHeight = src.getHeight(null);
        int srcWidth = src.getWidth(null);
        // 缩略图高
        int deskHeight = 0;
        // 缩略图宽
        int deskWidth = 0;
        double srcScale = (double) srcHeight / srcWidth;
        /** 缩略图宽高算法 */
        if ((double) srcHeight > comBase || (double) srcWidth > comBase) {
            if (srcScale >= scale || 1 / srcScale > scale) {
                if (srcScale >= scale) {
                    deskHeight = (int) comBase;
                    deskWidth = srcWidth * deskHeight / srcHeight;
                } else {
                    deskWidth = (int) comBase;
                    deskHeight = srcHeight * deskWidth / srcWidth;
                }
            } else {
                if ((double) srcHeight > comBase) {
                    deskHeight = (int) comBase;
                    deskWidth = srcWidth * deskHeight / srcHeight;
                } else {
                    deskWidth = (int) comBase;
                    deskHeight = srcHeight * deskWidth / srcWidth;
                }
            }
        } else {
            deskHeight = srcHeight;
            deskWidth = srcWidth;
        }
        BufferedImage tag = new BufferedImage(deskWidth, deskHeight, BufferedImage.TYPE_3BYTE_BGR);
        // 绘制缩小后的图
        tag.getGraphics().drawImage(src, 0, 0, deskWidth, deskHeight, null);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();//io流
        //写入流中
        ImageIO.write(tag, "jpg", baos);
        byte[] bytes = baos.toByteArray();//转换成字节
        String base64 = getBase64String(bytes);
        return base64;
    }

    public static String getBase64String(byte[] bytes) {
        String base64 = Base64.getEncoder().encodeToString(bytes).trim();
        base64 = base64.replaceAll("\n", "").replaceAll("\r", "");
        return base64;
    }

    public static byte[] getBytesByBase64(String base64){
        return Base64.getDecoder().decode(base64);
    }

    public static void main(String args[]) throws Exception {
//		ImageUtil.zipImageFile("D:/wmz.jpg", 1280, 1280, 1f, "x2");
        System.out.print(ImageUtil.getMinBase64("http://104.com/group1/M00/0F/9B/wKgBaF_Wo9CAeaejAAGUp6ri818371.jpg", 1442, 1d));
//        System.out.println(ImageUtil.getMinBase64("http://114.67.182.207/group1/M00/22/36/CgAAA179lFyAODiYAAAR9ZWLHpM019.jpg", 300, 0.9d));


    }

}
