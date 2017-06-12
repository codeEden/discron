package com.nt.open.discron.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.nt.open.discron.log.LogUtil;

/**
 * 
 * @author bjfulianqiu
 *
 */
public class HostHelper {
	
  public static String getHostName() {
    try {
      return InetAddress.getLocalHost().getHostName();
    } catch (UnknownHostException e) {
      e.printStackTrace();
      LogUtil.info("获取主机名出错！");
    }
    return "";
  }

  public static String getHostAddress() {
    try {
      return InetAddress.getLocalHost().getHostAddress();
    } catch (UnknownHostException e) {
      e.printStackTrace();
      LogUtil.info("获取主机名出错！");
    }
    return "";
  }

}
