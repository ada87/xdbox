package com.xdnote.xdobx.logic;

/**
 * 错误码常量定义，采用二层静态常量表示，便于分组与编辑器提示
 * 规则：四位数 ：前两位表示模块，后两位表示条目
 * 对应的提示配置在error.properties与error_zh_CN.properties中
 * */
public class CODE {
	/**
	 * 系统级别错误码：00 
	 * */
	public static class SYSTEM{
		/**默认系统错误*/
		public static final String DEFAULT="0000";
		/**反射-没有找到类 / 一般是打错包名或其它*/
		public static final String CLASS_NOT_FOUND="0001";
		/**反射-无法连接到类 / 一般是类没有生名为public*/
		public static final String ILLEGAL_ACCESS_FAILD="0002";
		/**反射-实例化类失败 / 一般是参数不对*/
		public static final String INSTANTIATION_FAILD="0003";
		/**IO - 文件读取异常*/
		public static final String IO_FILE_ERROR="0004";
		/**IO - 网络传输异常*/
		public static final String IO_NET_ERROR="0005";
		/** 编码解码错误, 比如BASE64解码时抛出 */
		public static final String CODE_INVAIDE="0006";
		/**在非 windows 操作系统上以pretty_cmd模式启动*/
		public static final String NOT_WINDOWS="0007";
		/**URL不符合规则*/
		public static final String MALFORMED_URL="0008";
		/**HTTP请求失败*/
		public static final String HTTP_FAILD="0009";
		/**HTTP响应错误*/
		public static final String HTTP_RESPONSE_ERROR="0010";
	}

	/**
	 * 工具：彩云Token生成器相关错误码：11 
	 * */
	public static class CYTOKEN{
		/**获取TOKEN失败*/
		public static final String RESIVE_FAILD="1101";
		/**解析TOKEN失败*/
		public static final String PARSE_FAILD="1102";
		/**生成TOKEN失败*/
		public static final String GEN_FAILD="1103";
		/**验证错误 输入的不是11位手机号*/
		public static final String INPUT_INVALID="1104";
		/**打开网站错误*/
		public static final String OPEN_FAILD="1105";
		/**复制成功*/
		public static final String COPY_SUCCESS="1106";
	}

}
