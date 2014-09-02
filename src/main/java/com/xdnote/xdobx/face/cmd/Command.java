package com.xdnote.xdobx.face.cmd;

import org.fusesource.jansi.Ansi;

/**
 * 命令控制中心，扩展命令工具要继承此类，实现 execute方法
 * @author xdnote.com
 * */
public abstract class Command {
	
	public static final String BOOL_CMD_ADVANCE="XDBOX.BOOL_CMD_ADVANCE";
	
	public String COLOR_BLACK="BLACK";
	public String COLOR_RED="RED";
	public String COLOR_YELLOW="YELLOW";
	public String COLOR_BLUE="BLUE";
	public String COLOR_GREEN="GREEN";
	public String COLOR_MAGENTA="MAGENTA";
	public String COLOR_CYAN="CYAN";
	public String COLOR_WHITE="WHITE";
	
	public String FONT_BOLD="INTENSITY_BOLD";
	public String FONT_FAINT="INTENSITY_FAINT";
	public String FONT_ITALIC="ITALIC";
	public String FONT_BLINK_SLOW="BLINK_SLOW";
	public String FONT_BLINK_FAST="BLINK_FAST";
	public String FONT_NEGATIVE_ON="NEGATIVE_ON";
	//是否是展示多彩模式
	private boolean isAdvanced = false;
	
	//推出数组中的第一个元素（命令）
	public String[] shift(String[] args){
		int size = args.length-1;
		String [] arr = new String[size];
		for(int i=0;i<size;i++){
			arr[i]=args[i+1];
		}
		return arr;
	}
	/**
	 * 无参构造函数
	 * */
	public Command() {
		if(Boolean.getBoolean(BOOL_CMD_ADVANCE)){
			this.isAdvanced = true;
		}
	}

	/**
	 * 执行命令：由实现类具体实现
	 * @param args String[] 输入的命令的字符串数以空格为分隔的数据
	 * */
	public abstract void execute( String[] args);

	/**
	 * 输出
	 * @param msg 要输出的信息
	 * */
	public void println(Object msg){
		System.out.println(msg);
	}
	/**
	 * 构建自定义字体与色彩进行展示，属性可取本类中的COLOR_ FONT_ 常量
	 * 说明：并不一定所有的参数都会生效
	 * @param msg String 需要输出的信息
	 * @param custom(,custom2,custom3) 输出信息的颜色或属性，可取本类的 COLOR_ FONT_ 常量
	 * */
	public String buildMsg(String msg,String... custom){
		if(this.isAdvanced){
			String tmp="@|";
			for(int i=0,j=custom.length;i<j;i++){
				tmp+=custom[i];
				tmp+=",";
			}
			tmp=tmp.substring(0,tmp.length()-1);
			tmp+=" ";
			tmp+=msg;
			tmp+="|@ ";
			return tmp;
		}else{
			return msg;
		}
	}
	/**
	 * 对于build方法生成出来的字符串，需要使用show方法来显示
	 * @param text build过后的信息
	 * */
	public void show(String text){
		if(this.isAdvanced){
			System.out.println( new Ansi().render(text) );
		}else{
			System.out.println(text);
		}
	}
	/**
	 * 绿色字体展示 INFO信息
	 * @param msg 需要输出的info信息
	 * */
	public void info(String msg){
		if(this.isAdvanced){
			this.println(new Ansi().fg(Ansi.Color.GREEN).a(msg).reset());
		}else{
			this.println(msg);
		}
	}
	/**
	 * 黄色字体展示WARN信息
	 * @param msg 需要输出的warn信息
	 * */
	public void warn(String msg){
		if(this.isAdvanced){
			this.println(new Ansi().fg(Ansi.Color.YELLOW).a(Ansi.Attribute.INTENSITY_BOLD).a(msg).reset());
		}else{
			this.println(msg);
		}
	}
	/**
	 * 灰底红字展示错误信息
	 * @param msg 需要输出的error信息
	 * */
	public void error(String msg){
		if(this.isAdvanced){
			this.println(new Ansi().fg(Ansi.Color.RED).bg(Ansi.Color.WHITE).a(Ansi.Attribute.INTENSITY_BOLD).a(msg).reset());
		}else{
			this.println(msg);
		}
	}
}
