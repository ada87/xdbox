package com.xdnote.xdobx.logic.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xdnote.xdobx.logic.CODE;
import com.xdnote.xdobx.logic.RunException;
/**
 * 简单粗暴的HTTP请求类，可以设 请求方法，请求头，请求编码，请求体，请求地址，来获取响应信息
 * */
public class HttpUtil {

	public static class Request{
		private String url;
		private String method="GET";
		private Map<String, String> headers =new HashMap<String, String>();
		private String entry;
		private String charset="UTF-8";
		public Request(String url) {
			super();
			this.url = url;
			this.headers.put("Content-Type", "application/x-www-form-urlencoded");
//			this.headers.put("Accept", "text/html,text/xml,text/json,application/xhtml+xml,application/xml");
//			this.headers.put("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
//			this.headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:31.0) Gecko/20100101 Firefox/31.0");
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getMethod() {
			return this.method;
		}
		public void setPost(boolean ispost) {
			if(ispost){
				this.method = "POST";
			}else{
				this.method = "GET";
			}
		}
		public Map<String, String> getHeaders() {
			return headers;
		}
		public void setHeader(String key, String value) {
			this.headers.put(key, value);
		}
		public String getEntry() {
			return entry;
		}
		public void setEntry(String entry) {
			this.entry = entry;
		}
		public String getCharset() {
			return this.charset;
		}
		public void setCharset(String charset) {
			this.charset = charset;
		}
	}

	/**
	 * <pre>
	 * 		METHOD:GET
	 * 		HEAD:默认
	 * 		ENTRY:无
	 * 		CHASET:UTF-8
	 * </pre>
	 * */
	public static String sendHttpRequest(String url) throws RunException{
		Request req = new HttpUtil.Request(url);
		return sendHttpRequest(req);
	}
	/**
	 * <pre>
	 * 		METHOD:GET
	 * 		HEAD:默认
	 * 		ENTRY:无
	 * 		CHASET:传入
	 * </pre>
	 * */
	public static String sendHttpRequest(String url,String charset) throws RunException{
		Request req = new HttpUtil.Request(url);
		req.setCharset(charset);
		return sendHttpRequest(req);
	}
	
	/**
	 * <pre>
	 * 		METHOD:POST
	 * 		HEAD:默认
	 * 		ENTRY:传入
	 * 		CHASET:传入
	 * </pre>
	 * */
	public static String sendHttpRequest(String url,String charset, String httpstr) throws RunException{
		Request req = new HttpUtil.Request(url);
		req.setCharset(charset);
		req.setPost(true);
		req.setEntry(httpstr);
		return sendHttpRequest(req);
	}
	
	/**
	 * <pre>
	 * 		METHOD:GET
	 * 		HEAD:覆盖
	 * 		ENTRY:无
	 * 		CHASET:UTF-8
	 * </pre>
	 * */
	public static String sendHttpRequest(String url,Map<String,String> headers) throws RunException{
		Request req = new HttpUtil.Request(url);
		for (Map.Entry<String, String> entry : headers.entrySet()){
			req.setHeader(entry.getKey(), entry.getValue());
		}
		return sendHttpRequest(req);
	}
	/**
	 * <pre>
	 * 		METHOD:POST
	 * 		HEAD:覆盖
	 * 		ENTRY:传入
	 * 		CHASET:UTF-8
	 * </pre>
	 * */
	public static String sendHttpRequest(String url,String httpstr,Map<String,String> headers) throws RunException{
		Request req = new HttpUtil.Request(url);
		for (Map.Entry<String, String> entry : headers.entrySet()){
			req.setHeader(entry.getKey(), entry.getValue());
		}
		req.setEntry(httpstr);
		req.setPost(true);
		return sendHttpRequest(req);
	}
	
	/**
	 * 发送请求/获取响应
	 * @throws RunException 
	 * */
	public static String sendHttpRequest(Request req) throws RunException {
		HttpURLConnection con;
		try {
			con = (HttpURLConnection) new URL(req.getUrl()).openConnection();
			con.setRequestMethod(req.getMethod());
			con.setUseCaches(false);
			con.setDoInput(true);
			Map<String,String>headers=req.getHeaders() ;
			if (headers!= null){
				for (Map.Entry<String, String> entry : headers.entrySet()){
					con.setRequestProperty(entry.getKey(), entry.getValue());
				}
			}
			String httpStr=req.getEntry();
			if(httpStr!=null){
				con.setDoOutput(true);
				OutputStream out = con.getOutputStream();
				out.write(httpStr.getBytes(req.getCharset()));
				out.flush();
			}
			if (200 != con.getResponseCode()){
				throw new RunException(CODE.SYSTEM.HTTP_RESPONSE_ERROR, "Response:"+con.getResponseCode());
			}
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(),req.getCharset()));
			
			StringBuilder result = new StringBuilder(100);
			char[] buf = new char[512];
			int byteread = 0;
			
			while ((byteread = in.read(buf)) != -1){
				result.append(buf, 0, byteread);
			}
			return result.toString();
		} catch (MalformedURLException e) {
			throw new RunException(CODE.SYSTEM.MALFORMED_URL,e);
		} catch (IOException e) {
			throw new RunException(CODE.SYSTEM.IO_NET_ERROR,e);
		}
	}

	/**
	 * 探测响应头
	 * */
	public static Map<String, List<String>> getHeader(String url,
			Map<String, String> headers) throws RunException {
		HttpURLConnection con = null;
		try {
			con = (HttpURLConnection) new URL(url).openConnection();
			con.setRequestMethod("GET");
			if (headers != null) {
				for (Map.Entry<String, String> entry : headers.entrySet()) {
					con.setRequestProperty(entry.getKey(), entry.getValue());
				}
			}
			return con.getHeaderFields();
		} catch (MalformedURLException e) {
			throw new RunException(CODE.SYSTEM.MALFORMED_URL, e);
		} catch (IOException e) {
			throw new RunException(CODE.SYSTEM.IO_NET_ERROR, e);
		}
	}
	
	
}
