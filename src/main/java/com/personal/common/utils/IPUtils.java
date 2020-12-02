package com.personal.common.utils;

import javax.servlet.http.HttpServletRequest;

public class IPUtils {

	public static final String HEADER1 = "x-forwarded-for";
	public static final String HEADER2 = "Proxy-Client-IP";
	public static final String HEADER3 = "WL-Proxy-Client-IP";
	public static final String UNKNOWN = "unknown";
	public static final String LOCALHOST = "127.0.0.1";
	public static final String LOCALHOST_STR = "0:0:0:0:0:0:0:1";


	/**
	 * 获取IP地址
	 * 
	 * 使用Nginx等反向代理软件， 则不能通过request.getRemoteAddr()获取IP地址
	 * 如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，
     * 而是一串IP地址，X-Forwarded-For中第一个非unknown的有效IP字符串，则为真实IP地址
	 */
	public static String getIpAddr(HttpServletRequest request) {

		String ip = request.getHeader(HEADER1);
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader(HEADER2);
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader(HEADER3);
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return LOCALHOST_STR.equals(ip) ? LOCALHOST : ip;
	}

}
