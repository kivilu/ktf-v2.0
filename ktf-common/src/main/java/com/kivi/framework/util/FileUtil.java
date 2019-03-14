package com.kivi.framework.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import com.kivi.framework.exception.ToolBoxException;
import com.kivi.framework.util.kit.ByteStringKit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileUtil {

    /**
     * 列举目录下的文件
     * 
     * @param dirName
     * @param filter
     * @return
     */
    public static File[] listFiles(String dirName, String filter) {
	File dir = new File(dirName);

	if (!dir.exists() || !dir.isDirectory()) {
	    return null;
	}

	File[] files = dir.listFiles(new FilenameFilter() {
	    // private Pattern pattern = Pattern.compile(filter);

	    @Override
	    public boolean accept(File dir, String name) {
		// return pattern.matcher(name).matches();
		if (name.endsWith(filter)) {
		    return true;
		} else {
		    return false;
		}
	    }

	});

	return files;

    }

    /**
     * NIO way 读取文件
     */
    public static byte[] toByteArray(String filename) {

	File f = new File(filename);
	return toByteArray(f);
    }

    public static byte[] toByteArray(File f) {

	if (!f.exists()) {
	    log.error("文件{}未找到！", f.getAbsolutePath());
	    throw new ToolBoxException("文件未找到");
	}
	try (FileInputStream fs = new FileInputStream(f)) {
	    FileChannel channel = fs.getChannel();
	    ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
	    while ((channel.read(byteBuffer)) > 0) {
		// do nothing
		// System.out.println("reading");
	    }
	    return byteBuffer.array();
	} catch (IOException e) {
	    throw new ToolBoxException("读取文件失败", e);
	}

    }

    /**
     * 将文件内容转成base64字符串
     * 
     * @param filename
     * @return
     */
    public static String toBase64(String filename) {
	return toBase64(new File(filename));
    }

    /**
     * 将文件内容转成base64字符串
     * 
     * @param filename
     * @return
     */
    public static String toBase64(File file) {
	byte[] data = toByteArray(file);

	return ByteStringKit.toString(data, ByteStringKit.BASE64);
    }
}
