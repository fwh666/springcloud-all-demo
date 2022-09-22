package club.fuwenhao.utils;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.core.io.Resource;
//import org.springframework.http.MediaType;
//
//import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class ExcelUtils {
    private static final Logger log = LoggerFactory.getLogger(ExcelUtils.class);

    private static final String ERROR_MESSAGE_ONE  = "模板下载异常：{}";
    private ExcelUtils() {
    }

    // @描述：是否是2003的excel，返回true是2003
    public static boolean isExcel2003(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    // @描述：是否是2007的excel，返回true是2007
    public static boolean isExcel2007(String filePath) {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }

    // @描述：是否是zip文件
    public static boolean isZip(String filePath) {
        return filePath != null && filePath.matches(".+\\.(?i)(zip)$");
    }

    /**
     * 验证EXCEL文件
     *
     * @param filePath
     * @return
     */
    public static boolean validateExcel(String filePath) {
        return filePath != null && (isExcel2003(filePath) || isExcel2007(filePath));
    }

    // 自适应宽度(中文支持)
    public static void setSizeColumn(XSSFSheet sheet, int size) {
        for (int columnNum = 0; columnNum < size; columnNum++) {
            int columnWidth = sheet.getColumnWidth(columnNum) / 256;
            for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                XSSFRow currentRow;
                //当前行未被使用过
                if (StringUtil.isNull(sheet.getRow(rowNum))) {
                    currentRow = sheet.createRow(rowNum);
                } else {
                    currentRow = sheet.getRow(rowNum);
                }
                if (currentRow.getCell(columnNum) != null && currentRow.getCell(columnNum).getCellTypeEnum().equals(CellType.STRING)) {
                    XSSFCell currentCell = currentRow.getCell(columnNum);
                    int length = currentCell.getStringCellValue().getBytes().length;
                    if (columnWidth < length) {
                        columnWidth = length;
                    }
                }
            }
            sheet.setColumnWidth(columnNum, columnWidth * 256);
        }
    }

    // 模板下载
//    public static void template(HttpServletResponse response, String fileName, String downFileName) throws IOException {
//        String formatFileName = String.format("attachment; filename=\"%s\"",
//                new String(downFileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
//        response.setHeader("Content-Type", MediaType.APPLICATION_OCTET_STREAM_VALUE);
//        response.setHeader("Content-Disposition", formatFileName);
//        Resource resource = new ClassPathResource("template/" + fileName);
//        File file = resource.getFile();
//
//        InputStream fileInputStream = new FileInputStream(file);
//        try {
//            OutputStream os = response.getOutputStream();
//            int bytesRead = 0;
//            byte[] buffer = new byte[1024];
//            while ((bytesRead = fileInputStream.read(buffer, 0, 1024)) != -1) {
//                os.write(buffer, 0, bytesRead);
//            }
//            // 关闭资源
//            os.close();
//        } catch (Exception e) {
//            LogUtil.error(log, ERROR_MESSAGE_ONE, e.getMessage());
//        } finally {
//            fileInputStream.close();
//        }
//
//    }
//
//    // 模板下载
//    public static void templateDown(HttpServletResponse response, String fileName, String downFileName) throws IOException {
//        String filePath = ExcelUtils.class.getResource("/").getPath() + "download/template/";
//        File tmpPath = new File(filePath);
//        if (!tmpPath.exists()) {
//            tmpPath.mkdirs();
//        }
//        File f = new File(filePath + System.currentTimeMillis() + "_" + fileName);
//        String formatFileName = String.format("attachment; filename=\"%s\"",
//                new String(downFileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
//        response.setHeader("Content-Type", MediaType.APPLICATION_OCTET_STREAM_VALUE);
//        response.setHeader("Content-Disposition", formatFileName);
//        if (!f.exists()) {
//            InputStream in = ExcelUtils.class.getResourceAsStream("/template/" + fileName);
//            try {
//                OutputStream os = response.getOutputStream();
//                int bytesRead = 0;
//                byte[] buffer = new byte[1024];
//                while ((bytesRead = in.read(buffer, 0, 1024)) != -1) {
//                    os.write(buffer, 0, bytesRead);
//                }
//                // 关闭资源
//                os.close();
//                in.close();
//            } catch (Exception e) {
//                LogUtil.error(log, ERROR_MESSAGE_ONE, e.getMessage());
//            }
//        } else {
//            byte[] buff = new byte[1024];
//            BufferedInputStream bis = null;
//            OutputStream os = response.getOutputStream();
//            try {
//                bis = new BufferedInputStream(new FileInputStream(f));
//                int i = bis.read(buff);
//                while (i != -1) {
//                    os.write(buff, 0, buff.length);
//                    os.flush();
//                    i = bis.read(buff);
//                }
//            } catch (Exception e) {
//                LogUtil.error(log, ERROR_MESSAGE_ONE, e.getMessage());
//            } finally {
//                // 关闭资源
//                if (bis != null) {
//                    bis.close();
//                }
//                os.close();
//                f.deleteOnExit();
//            }
//        }
//    }
}
