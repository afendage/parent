package com.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 访问控制类 - 防止频繁刷单与恶意攻击
 * @author afen
 *
 */
public class VisitUtil {

	private static final int len = 150; // 秒问单位， 多久时间内
	
	private static final int max = 5; 	// 做大访问数量限制
	
	/**
	 * 控制客户端 在指定时间内不能频繁请求
	 * @param request
	 * @param session
	 * @return true 在规定范围内，false 已超出访问数量限制
	 */
	public static boolean maxVisit(HttpServletRequest request, HttpSession session){
		String ip = IPUtil.getIp(request);
		Object obj = session.getAttribute(ip);
		if (obj == null) {
			session.setAttribute(ip+"_currentTime", (System.currentTimeMillis()/1000));
			session.setAttribute(ip, 0);
		}else {
			Integer seconde = (Integer) session.getAttribute(ip);
			long ct = (Long) session.getAttribute(ip+"_currentTime");
			long interal = (System.currentTimeMillis()/1000) - ct ;
			if (interal > len) {	// 当前时间 - 客户端之前访问存入的时间，大于 len 秒 ，则清空
				session.removeAttribute(ip+"_currentTime");
				session.removeAttribute(ip);
			}else {
				session.setAttribute(ip, ++seconde);
				System.out.println("YYYYYYYYYYYYYYYYYYYYYYYYYYYY:"+seconde);
			}
			if (seconde > max) {	// 客户端请在规定时间内请求超过限定 返回 false
				return false;
			}
		}
		return true;
	}
	
}
