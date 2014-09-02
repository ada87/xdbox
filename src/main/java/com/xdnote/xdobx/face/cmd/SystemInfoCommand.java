package com.xdnote.xdobx.face.cmd;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class SystemInfoCommand extends Command{

	@Override
	public void execute(String[] args) {}
	
	public void system(String[] args){
		Properties p = System.getProperties();
		Iterator<Object> it = p.keySet().iterator();
		while(it.hasNext()){
			String key = it.next().toString();
			this.info(key +" = " + p.getProperty(key));
		}
	}
	
	public void set(String[] args){
		if(args.length>=1){
			String key= args[0];
			String value = System.getenv(key);
			if(value!=null){
				this.info(key+"="+value);
			}else{
				this.warn(key+" not exisits");
			}
		}else{
			Map<String, String> p=System.getenv();
			Iterator<String> it = p.keySet().iterator();
			while(it.hasNext()){
				String key = it.next().toString();
				this.info(key+"="+p.get(key));
			}
		}
		
	}

}
