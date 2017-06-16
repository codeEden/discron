/**
 * 
 */
package com.nt.open.proc.entity.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author bjfulianqiu
 *
 */
@Setter @Getter
public class Message {

	private int code;
	private String message;
	private Object data;
}
