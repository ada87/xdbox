package com.xdnote.xdobx.face.cmd;



import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.xdnote.xdobx.logic.RunException;
import com.xdnote.xdobx.logic.util.HttpUtil;

public class HttpCommand extends Command{

	private String ipget="<center>(.+)</center>";
	

	
	@Override
	public void execute(String[] args) {
		try {
			Matcher m = Pattern.compile(ipget).matcher(HttpUtil.sendHttpRequest("http://20140507.ip138.com/ic.asp","GB2312"));
			if(m.find()){
				this.info(m.group(1));
			}else{
				this.error("");
			}
		} catch (RunException e) {
			this.error(e.toString());
		}
		
	}

}
