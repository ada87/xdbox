package com.xdnote.xdobx.face.win;

/**
 * GUI启动类，默认没有什么功能 ，可通过配置项动态加入更多功能
 * 可添加任意JFrame 或 JComponent
 * */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;










import com.xdnote.xdobx.logic.CONFIG;
import com.xdnote.xdobx.logic.I18N;
import com.xdnote.xdobx.logic.util.ConfigUtil;

public class MainActivity extends JPanel{

	private static final long serialVersionUID = -6186390235479386406L;
	
	private ArrayList<ToolBox> boxs =new ArrayList<ToolBox>();
	private ToolBox currentBox = null;

	private JToolBar menu ;
	private JTabbedPane container = null;
	
	private void initTools(){
		String[] tools = CONFIG.MAIN.ENABLE_TOOL;
		for(int i = 0,j=tools.length;i<j;i++){
			String tool = tools[i];
			ToolBox toolbox = new ToolBox(I18N.STR(tool+".title") , this , ConfigUtil.getProperty(tool));
			JButton btn = toolbox.getNav();
			this.menu.add(btn);
			this.boxs.add(toolbox);
		}
	}
	
	/***
	 * 用JAVA写这种东西就是烦，有点长
	 * */
	public MainActivity(){
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createEmptyBorder(2,0,4,0));
		
		/**
		 * 构建Menu
		 * */
		JPanel panel_top = new JPanel();
		panel_top.setLayout(new BorderLayout());
		this.add(panel_top,BorderLayout.NORTH);
		
		this.menu= new JToolBar();
		this.menu.setFloatable(false);
		panel_top.add(this.menu,BorderLayout.CENTER);
		this.initTools();
		
		/**
		 * 构建Context ❀ 区
		 * */
		this.container = new JTabbedPane();
		this.container.setLayout(new BorderLayout());
		this.add(this.container, BorderLayout.CENTER);
		this.container.add(new javax.swing.JTextArea(I18N.MAIN.WELCOME));
		
		JFrame m = new JFrame();
		m.setTitle(I18N.MAIN.TITLE);
		m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		m.setMinimumSize(new Dimension(CONFIG.MAIN.MIN_WIDTH,CONFIG.MAIN.MIN_HEIGHT));
		m.setPreferredSize(new Dimension(CONFIG.MAIN.WIDTH,CONFIG.MAIN.HEIGHT));
		m.setBounds(100,100,CONFIG.MAIN.WIDTH,CONFIG.MAIN.HEIGHT);
		m.add(this);
		m.setVisible(true);
	}
	
	public void switchTo(ToolBox box){
		if(currentBox==box){
			return;
		}
		for(int i=0,j=boxs.size();i<j;i++){
			ToolBox temp = boxs.get(i);
			if (temp == box){
				this.currentBox = box;
				temp.getNav().setEnabled(false);
				Object win = box.getWindow();
				if(win instanceof JFrame){
					((JFrame) win).setVisible(true);
				}else if(win instanceof JComponent){
					this.container.removeAll();
					this.container.add((JComponent) win);
					this.container.repaint();
				}
			}else{
				temp.getNav().setEnabled(true);
			}
		}
	}
	
	/**
	 * 工具盒子集
	 * */
	class ToolBox {
		//工具导航上的按钮
		private JButton nav;
		//工具依附的主窗口
		private MainActivity main;
		//工具的类
		private String control;
		//工具的实体,初始为空，仅当使用的时候才创建
		private Object win = null;
		
		/**
		 * 工具构造函数
		 * @param title String 导航名称
		 * @param main  MainActivity 依附的主窗口
		 * @param classname String 工具的实现类
		 * */
		public ToolBox(String title, MainActivity main,String classname) {
			super();
			this.main = main;
			this.control = classname;
			this.setNav(title);
		}
		
		public JButton getNav() {
			return nav;
		}
		private void setNav(String title) {
			this.nav = new JButton();
			this.nav.setAction(new SwitchNavAction(this.main,this));
			this.nav.setText(title);
		}
		public Object getWindow() {
			if(this.win!=null){
				return this.win;
			}
			try {
				this.win =Class.forName(this.control).newInstance();
				return this.win;
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return null;
		}
		/**
		 * 导航切换
		 * */
		private class SwitchNavAction extends AbstractAction {
			
			private static final long serialVersionUID = -3605822783794594093L;
			
			MainActivity main;
			ToolBox tool;

			public SwitchNavAction(MainActivity activity,ToolBox tool) {
				this.main = activity;
				this.tool = tool;
			}

			public void actionPerformed(ActionEvent e) {
				this.main.switchTo(tool);
			}
		}
	}

}
