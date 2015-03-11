package com.xdnote.xdobx;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.UIManager;

import nz.net.ultraq.thymeleaf.LayoutDialect;

import org.fusesource.jansi.AnsiConsole;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import com.xdnote.xdobx.face.cmd.Command;
import com.xdnote.xdobx.face.cmd.MainCommand;
import com.xdnote.xdobx.face.win.MainActivity;
import com.xdnote.xdobx.logic.CODE;
import com.xdnote.xdobx.logic.CONFIG;
import com.xdnote.xdobx.logic.I18N;
/**
 * 
 * <pre>
 * 程序启动类，打包时 设置 Main-Class: com.xdbox.face.Main
 * 支持四种启动模式，可从args传入，不传args时默认为 config.properties里面配置的模式
 * win_basic : 基本Swing窗口模式启动 默认
 * win_pretty : 渲染过的Swing窗口模式启动 需要 beautyeye_inf 支持
 * cmd_basic : 基本命令行行模式启动
 * cmd_pretty : 渲染过的多彩命令行模式启动 需要jansi支持,非window系统不支持，若在linux上以启动时，会默认启动基本命令行模式
 * </pre>
 * */
public class Main {

	/**
	 * 以基本窗口形式启动程序(默认)
	 * */
//	private static final String WIN_BASIC = "win_basic";
	/**
	 * 以美化窗口形式启动程序	(使用baeautyeye_inf皮肤)
	 * */
	private static final String WIN_PRETTY = "win_pretty";
	/**
	 * 以基本命令形式启动程序 (需要Window环境及jansi支持)
	 * */
	private static final String CMD_BASIC = "cmd_basic";
	/**
	 * 以美化命令形式启动程序
	 * */
	private static final String CMD_PRETTY = "cmd_pretty";

	/**
	 * 主体启动函数，参数可以不传
	 * */
	public static void main(String[] args) {
		String start_mode = CONFIG.MAIN.STARTUP;
		if(args.length>=1){
			start_mode=args[0];
		}
		if (start_mode.equals(CMD_BASIC) || start_mode.equals(CMD_PRETTY)) {
			System.setProperty(Command.BOOL_CMD_ADVANCE, "false");
			if(start_mode.equals(CMD_PRETTY) ){
				 String os = System.getProperty("os.name");
				    if (os.startsWith("Windows")){
						System.setProperty(Command.BOOL_CMD_ADVANCE, "true");
						AnsiConsole.systemInstall();
				    }else{
				    	System.out.println(I18N.CODE(CODE.SYSTEM.NOT_WINDOWS));
				    }
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			new MainCommand(br);
		} else {
			if (start_mode.equals(WIN_PRETTY)) {
				try {
					ServletContextTemplateResolver templateresolver = new ServletContextTemplateResolver();
					templateresolver.setTemplateMode("HTML5");

					TemplateEngine templateengine = new TemplateEngine();
					templateengine.setTemplateResolver(templateresolver);
					templateengine.addDialect(new LayoutDialect()); 
					
//					UIManager.put("RootPane.setupButtonVisible", false);
//					JFrame.setDefaultLookAndFeelDecorated(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			new MainActivity();
		}
	}
}
