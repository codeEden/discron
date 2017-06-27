package com.nt.open.discron.common.util;

import java.io.Serializable;

import com.nt.open.discron.common.util.CommonStatusCode.StatusCode;
import com.nt.open.discron.common.util.CommonStatusCode.StatusMsg;

public class Result implements Serializable {

	private static final long serialVersionUID = -14972284664427720L;
	private int resultcode;
	private String msg;
	private Object data;
	
	public Result(){
	    
	}
	
	
	public Result(int resultcode, String msg, Object data) {
        this.resultcode = resultcode;
        this.msg = msg;
        this.data = data;
    }

	//set get
	public int getResultcode() {
		return resultcode;
	}

	public void setResultcode(int resultcode) {
		this.resultcode = resultcode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static class ResultFactory {
		public static Result makeErrorResult(int errorcode, String msg) {
			Result result = new Result();
			result.setResultcode(errorcode);
			result.setMsg(msg);
			result.setData("");
			return result;
		}

		public static Result makeOKResult() {
			Result result = new Result();
			result.setResultcode(StatusCode.OK);
			result.setMsg(StatusMsg.OK_MSG);
			return result;
		}
		
		public static Result makeOKResult(Object object) {
			Result result = new Result();
			result.setResultcode(StatusCode.OK);
			result.setMsg(StatusMsg.OK_MSG);
			result.setData(object);
			return result;
		}
		
		public static Result makeResult(int code,String message,Object object){
			Result result = new Result();
			result.setResultcode(code);
			result.setMsg(message);
			result.setData(object);
			return result;
		}
	}
}
