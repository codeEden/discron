package com.nt.open.discron.common.util;

public class CommonStatusCode {

	public static final class StatusCode {
		public static final Integer OK = 200;
		public static final Integer SERVER_ERROR = 500;
		public static final Integer DATABASE_ERROR = 501;
		public static final Integer EXCEED_LOAD_ERROR = 502;
		public static final Integer PARAM_ERROR = 400;
		public static final Integer REFER_ERROR = 102;
	}
	
	public static final class StatusMsg {
		public static final String OK_MSG = "success";
		public static final String SERVER_ERROR_MSG = "服务器异常";
		public static final String EXCEED_LOAD_ERROR_MSG = "线程数超出负载异常";
		public static final String EXCEED_THREAD_ERROR_MSG = "线程数超出限制异常";
		public static final String REUQUEST_ERROR_MSG = "参数异常";
	}
}
