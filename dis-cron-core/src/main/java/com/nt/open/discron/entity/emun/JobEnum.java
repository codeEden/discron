/**
 * 
 */
package com.nt.open.discron.entity.emun;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author bjfulianqiu
 *
 */
public class JobEnum {

	@Getter  @AllArgsConstructor
	public enum Status{
		NORMAL(0,"正常"),DEL(1,"删除");
		
		private Integer code;
		private String message;
	}
	
	@Getter @AllArgsConstructor
	public enum Type{
		HTTP(1,"http方式"),CLASS(2,"class方式");
		
		private Integer code;
		private String message;
	}
}
