package com.common;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取访问客户端 IP 地址工具类
 * 
 * @author afen
 * 
 */
public class IPUtil {

	/**
	 * 获取访问客户端 IP 方法 1
	 * @param request
	 * @return
	 */
	public static String getIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个ip值，第一个ip才是真实ip
			int index = ip.indexOf(",");
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}
		ip = request.getHeader("X-Real-IP");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			return ip;
		}
		return request.getRemoteAddr();
	}

	/**
	 * 获取访问用户的客户端IP（适用于公网与局域网）方法 2
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static final String getIpAddr(final HttpServletRequest request)
	        throws Exception {
	    if (request == null) {
	        throw (new Exception("getIpAddr method HttpServletRequest Object is null"));
	    }
	    String ipString = request.getHeader("x-forwarded-for");
	    if (StringUtils.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {
	        ipString = request.getHeader("Proxy-Client-IP");
	    }
	    if (StringUtils.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {
	        ipString = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if (StringUtils.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {
	        ipString = request.getRemoteAddr();
	    }
	 
	    // 多个路由时，取第一个非unknown的ip
	    final String[] arr = ipString.split(",");
	    for (final String str : arr) {
	        if (!"unknown".equalsIgnoreCase(str)) {
	            ipString = str;
	            break;
	        }
	    }
	 
	    return ipString;
	}
}
