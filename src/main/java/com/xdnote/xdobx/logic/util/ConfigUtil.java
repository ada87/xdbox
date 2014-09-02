package com.xdnote.xdobx.logic.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtil {

	private static Properties properties;

	private static boolean loaded = false;

	static {
		init();
	}

	static void init() {
		if (!loaded) {
			properties = new Properties();
			String fileName = "config.properties";
			loaded = loadProperties(properties, "." + File.separatorChar
					+ fileName)
					|| loadProperties(properties,
							ConfigUtil.class.getResourceAsStream("/" + fileName));
		}
	}

	private static boolean loadProperties(Properties props, String path) {
		FileInputStream in = null;
		try {
			File file = new File(path);
			if (file.exists() && file.isFile()) {
				in = new FileInputStream(file);
				props.load(in);
				return true;
			}
		} catch (Exception ignore) {
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}
		return false;
	}

	private static boolean loadProperties(Properties props, InputStream is) {
		try {
			props.load(is);
			return true;
		} catch (Exception ignore) {
		}
		return false;
	}
	/**
	 * 修改配置项
	 * */
	public static void setPorperty(String key,String value){
		properties.setProperty(key, value);
	}
	/**
	 * 提取配置项
	 * */
	public static String getProperty(String name) {
		return properties.getProperty(name);
	}

}
