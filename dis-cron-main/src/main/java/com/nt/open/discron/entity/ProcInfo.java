/**
 * 
 */
package com.nt.open.discron.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author bjfulianqiu
 *
 */
@Setter @Getter
public class ProcInfo {

	private Long jobId;
	private Integer timeout;
	private Long startTime;
	private Process process;
}
