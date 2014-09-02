package com.xdnote.xdobx.face.cmd;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.xdnote.xdobx.logic.CODE;
import com.xdnote.xdobx.logic.CONFIG;
import com.xdnote.xdobx.logic.I18N;

/**
 * <pre>
 * 主控制台，这里实现了一个help(展示所有可用的命令)命令与一个quit(退出程序)命令
 * 由于主控制台是必须启动的，这里的命令就写在了代码里面，其它需要扩展的地方在配置文件里面扩展命令
 * </pre>
 * */
public class MainCommand extends Command{
	
	private Map<String,Command> comands = new HashMap<String,Command> ();
	
	public MainCommand(BufferedReader br) {
		this.init(br);
//		this.execute(new String[]{"help"});
		try {
			String str = br.readLine().trim();
			while(str!=null){
				String[] args = str.split("\\s+");
				Command cmd = comands.get(args[0]);
				if(cmd!=null){
					boolean executed = false;
					//试着寻找方法处理，如不能成功，则再使用execute处理
					try {
						Method method = cmd.getClass().getMethod(args[0], String[].class);
						if(method!=null){
							method.invoke(cmd, (Object)shift(args));
							executed=true;
						}
					} catch (SecurityException e) {
					} catch (NoSuchMethodException e) {
					} catch (IllegalArgumentException e) {
					} catch (IllegalAccessException e) {
					} catch (InvocationTargetException e) {
					}
					if(!executed){
						cmd.execute(args);
					}
				}else{
					this.execute(args);
				}
				str = br.readLine().trim();;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 注册到控制台
	 * */
	private void regiterComand(String cmdStr, Command cmd){
		String[] cmds = cmdStr.split(",");
		for(int i=0,j=cmds.length;i<j;i++){
			this.comands.put(cmds[i], cmd);
		}
	}
	
	@Override
	public void execute(String[] args)  {
		if(args[0].equalsIgnoreCase("quit")){
			System.exit(0);
		}else if(args[0].equalsIgnoreCase("help")){
			String msg = buildMsg("This", COLOR_RED, FONT_BLINK_SLOW,FONT_ITALIC);
			msg+=buildMsg(" Dev Boox Tool ", COLOR_BLUE,FONT_BLINK_FAST,FONT_BOLD);
			msg+=buildMsg("is", COLOR_GREEN,FONT_FAINT);
			msg+=buildMsg(" prower by ", COLOR_MAGENTA);
			msg+=buildMsg("xdnote ",COLOR_YELLOW,FONT_BOLD);
			show(msg);
			info("**********************************************************************");
			msg = buildMsg("*******************      ", COLOR_GREEN);
			msg+=buildMsg("Suport commands",COLOR_YELLOW,FONT_BLINK_FAST,FONT_BOLD);
			msg+= buildMsg("      **********************", COLOR_GREEN);
			show(msg);
			for (Map.Entry<String, Command> key : comands.entrySet()){
				info("*******************  "+ fixString(key.getKey(),24)+"   **********************");
			}
			info("**********************************************************************");
		}else{
			if(args[0].trim().length()>0){
				warn("no comands for : "+args[0] +" , type\"help\" for get Helps!");
			}
		}
	}

	/**
	 * 把String增加到指定长度
	 * */
	public static String fixString(String str, int length) {
		int size = str.length();
		if (size < length) {
			int more = length - size;
			String prev = "";
			String next = "";
			for (int i = 0; i < more / 2; i++) {
				prev += " ";
				next += " ";
			}
			if ((more % 2) !=0) {
				next += " ";
			}
			return prev + str + next;
		}
		return str;
	}
	private void init(BufferedReader br){
		regiterComand("quit,help",this);
		String[] cmds = CONFIG.MAIN.CMDS;
		for(int i=0;i<cmds.length;i++){
			String[] regiter = cmds[i].split(":");
			if(regiter.length==2){
				try {
					regiterComand(regiter[0],(Command)Class.forName(regiter[1]).newInstance());
				} catch (InstantiationException e) {
					error( I18N.CODE(CODE.SYSTEM.INSTANTIATION_FAILD,regiter[1] ));
				} catch (IllegalAccessException e) {
					error( I18N.CODE(CODE.SYSTEM.ILLEGAL_ACCESS_FAILD,regiter[1]));
				} catch (ClassNotFoundException e) {
					error( I18N.CODE(CODE.SYSTEM.CLASS_NOT_FOUND,regiter[1]));
				}
			}
		}
	}
}
