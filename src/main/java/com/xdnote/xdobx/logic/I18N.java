package com.xdnote.xdobx.logic;

import java.util.ResourceBundle;
/**
 * 国际化，包括取内容，与取错误信息
 * */
public class I18N {
	
	private static ResourceBundle source ;
	private static ResourceBundle codes ;
	
	static{
		source = ResourceBundle.getBundle("context");
		codes = ResourceBundle.getBundle("codes");
	}
	public static String CODE(String errorcode){
		return codes.getString(errorcode);
	}
	public static String CODE(String errorcode,String... args){
		String str = codes.getString(errorcode);
		for(int i=0,j=args.length;i<j;i++){
			str = str.replace("{"+i+"}", args[i]);
		}
		return str;
	}
	
	public static String STR(String key){
		return source.getString(key);
	}

	public static class MAIN{
		public static String TITLE = I18N.STR("main.title");
		public static String WELCOME =  I18N.STR("main.welcome");
	}
	
	public static class SETTING{
		public static String TITLE = I18N.STR("setting.title");
	}
	
	public static class CYTOKEN{
		private static String prefix = "cyToken.";
		public static String TITLE = I18N.STR(prefix+"title");
		public static String MSISDN=I18N.STR(prefix+"msisdn");
		public static String GEN_TOKEN=I18N.STR(prefix+"genToken");
		public static String COPY=I18N.STR(prefix+"copyToken");
		public static String OPEN=I18N.STR(prefix+"openWebsite");
	}
}
