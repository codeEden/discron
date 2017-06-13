/**
 * 
 */
package com.nt.open.proc.main;

import com.nt.open.proc.entity.emun.JobEnum;
import com.nt.open.proc.util.HTTPUtils;
import com.nt.open.proc.util.LogUtil;

import lombok.AllArgsConstructor;

/**
 * @author bjfulianqiu
 *
 */
@AllArgsConstructor
public class ExecuteThread implements Runnable{
	
	private Integer type;
	private String url;

	@Override
	public void run() {
		try{
			if(type==JobEnum.Type.HTTP.getCode()){
				HTTPUtils.sendHttpOrHttps(url, "GET", null, null);
			}else if(type==JobEnum.Type.CLASS.getCode()){
				
			}
			
		}catch(Exception e){
			LogUtil.error("任务执行错误",e);
		}
	}

}
