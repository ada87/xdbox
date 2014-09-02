package com.xdnote.xdobx.logic;

import com.xdnote.xdobx.logic.util.ConfigUtil;
/**
 * 配置文件定义,采用二层静态常量表示，便于分组与编辑器提示
 * */
public class CONFIG {
	
	/**
	 * 主程序相关配置
	 * */
	public static class MAIN{
		private static final String prefix ="main.";
		public static String STARTUP=ConfigUtil.getProperty(prefix+"startup_mode");
		public static String[] CMDS=ConfigUtil.getProperty(prefix+"cmd_reg").split("\\|");
	
		public static int WIDTH= Integer.parseInt(ConfigUtil.getProperty(prefix+"width"));
		public static int HEIGHT= Integer.parseInt(ConfigUtil.getProperty(prefix+"height"));
		public static int MIN_WIDTH= Integer.parseInt(ConfigUtil.getProperty(prefix+"min_width"));
		public static int MIN_HEIGHT= Integer.parseInt(ConfigUtil.getProperty(prefix+"min_height"));
		
		public static String[] ENABLE_TOOL = ConfigUtil.getProperty(prefix+"enable_tools").split(",");
	}
	
	/**
	 * 设置相关配置项
	 * */
	public static class SETTING{
//		private static final String prefix ="setting.";
	}

	/**
	 * Mcloud Token相关配置项
	 * */
	public static class CYTOKEN{
//		private static final String prefix ="cyToken.";
	}

}
