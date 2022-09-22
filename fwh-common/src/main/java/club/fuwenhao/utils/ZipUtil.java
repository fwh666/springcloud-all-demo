package club.fuwenhao.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.Charset;
import java.util.zip.*;

public class ZipUtil {
	private static final Logger log = LoggerFactory.getLogger(ZipUtil.class);

	private ZipUtil() {
	}

	/**
	 * 解压文件到指定目录
	 *
	 * @param unZipPath     解压路径，比如C:\\home\\myblog\\project\\
	 * @param //            fileName 已去除 解压后的文件名，一般命名为项目名，强制要求用户输入，并且保证不为空，
	 *                      fileName的上层目录为一个随机生成的32位UUID，以保证项目名重复的依然可以保存到服务器
	 * @param multipartFile 上传压缩文件
	 * @return FileHandleResponse 表示上传结果实体对象
	 */
	public static String unZipFiles(String unZipPath, MultipartFile multipartFile) throws IOException {
		// 如果保存解压缩文件的目录不存在，则进行创建，并且解压缩后的文件总是放在以fileName命名的文件夹下
		File unZipFile = new File(unZipPath);
		if (!unZipFile.exists()) {
			unZipFile.mkdirs();
		}
		// ZipInputStream用来读取压缩文件的输入流
		ZipInputStream zipInputStream = new ZipInputStream(multipartFile.getInputStream(), Charset.forName("GBK"));
		// 压缩文档中每一个项为一个zipEntry对象，可以通过getNextEntry方法获得，zipEntry可以是文件，也可以是路径，比如abc/test/路径下
		ZipEntry zipEntry;
		OutputStream outputStream = null;
		String zipEntryName = null;
		try {
			while ((zipEntry = zipInputStream.getNextEntry()) != null) {
				zipEntryName = zipEntry.getName();
				// 将目录中的1个或者多个\置换为/，因为在windows目录下，以\或者\\为文件目录分隔符，linux却是/
				String outPath = (unZipPath + zipEntryName).replaceAll("\\+", "/");
				// 判断所要添加的文件所在路径或者
				// 所要添加的路径是否存在,不存在则创建文件路径
				File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
				if (!file.exists()) {
					file.mkdirs();
				}
				// 判断文件全路径是否为文件夹,如果是,在上面三行已经创建,不需要解压
				if (new File(outPath).isDirectory()) {
					continue;
				}
				byte[] bytes = new byte[4096];
				int len;
				outputStream = new FileOutputStream(outPath);
				// 当read的返回值为-1，表示碰到当前项的结尾，而不是碰到zip文件的末尾
				while ((len = zipInputStream.read(bytes)) > 0) {
					outputStream.write(bytes, 0, len);
				}
				// 必须调用closeEntry()方法来读入下一项
				zipInputStream.closeEntry();
				if (outputStream != null) {
					outputStream.close();
				}
			}
		} catch (Exception e) {
			LogUtil.error(log, "解压zip包异常：{}", e.getMessage());
		} finally {
			if (outputStream != null) {
				outputStream.close();
			}
			zipInputStream.close();
		}
		return zipEntryName;
	}
	public void compress(String srcFilePath, String destFilePath) {
		File src = new File(srcFilePath);
		if (!src.exists()) {
			throw new RuntimeException(srcFilePath + "不存在");
		} else {
			File zipFile = new File(destFilePath);
			FileOutputStream fos = null;
			CheckedOutputStream cos = null;
			ZipOutputStream zos = null;

			try {
				fos = new FileOutputStream(zipFile);
				cos = new CheckedOutputStream(fos, new CRC32());
				zos = new ZipOutputStream(cos);
				String baseDir = "";
				if (src.isDirectory()) {
					File[] files = src.listFiles();
					File[] var10 = files;
					int var11 = files.length;

					for(int var12 = 0; var12 < var11; ++var12) {
						File file = var10[var12];
						this.compressByType(file, zos, baseDir);
					}
				} else {
					this.compressByType(src, zos, baseDir);
				}
			} catch (Exception var30) {
				log.error(var30.getMessage());
			} finally {
				try {
					if (zos != null) {
						zos.close();
					}
				} catch (IOException var29) {
					log.error(var29.getMessage());
				}

				try {
					if (cos != null) {
						cos.close();
					}
				} catch (IOException var28) {
					log.error(var28.getMessage());
				}

				try {
					if (fos != null) {
						fos.close();
					}
				} catch (IOException var27) {
					log.error(var27.getMessage());
				}

			}

		}
	}

	private void compressByType(File src, ZipOutputStream zos, String baseDir) {
		if (src.exists()) {
			log.info("压缩" + baseDir + src.getName());
			if (src.isFile()) {
				this.compressFile(src, zos, baseDir);
			} else if (src.isDirectory()) {
				this.compressDir(src, zos, baseDir);
			}

		}
	}

	private void compressDir(File srcDir, ZipOutputStream zos, String baseDir) {
		if (!srcDir.exists()) {
			throw new RuntimeException(srcDir + "不存在");
		} else {
			File[] files = srcDir.listFiles();
			File[] var5 = files;
			int var6 = files.length;

			for(int var7 = 0; var7 < var6; ++var7) {
				File file = var5[var7];
				this.compressByType(file, zos, baseDir + srcDir.getName() + File.separator);
			}

		}
	}

	private void compressFile(File file, ZipOutputStream zos, String baseDir) {
		if (file.exists()) {
			try {
				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
				ZipEntry entry = new ZipEntry(baseDir + file.getName());
				zos.putNextEntry(entry);
				byte[] buf = new byte[1024];

				int count;
				while((count = bis.read(buf)) != -1) {
					zos.write(buf, 0, count);
				}

				bis.close();
			} catch (Exception var8) {
				var8.printStackTrace();
				log.error(var8.getMessage());
			}

		}
	}
}
