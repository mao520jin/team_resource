package com.wsxd.sync.common.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.wsxd.sync.common.StatusCode;
import com.wsxd.sync.model.power.SysUser;
import com.wsxd.sync.service.SysUserService;

public class RequestInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private SysUserService sysUserService;

	// 操作权限判断
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String url = requestUri.substring(contextPath.length());
		if (url.contains("/")) {
			url = url.replaceAll("/", "");
		}
		if (url.contains("\\")) {
			url = url.replaceAll("\\", "");
		}

		HttpSession session = request.getSession();
		SysUser sysUser = (SysUser) request.getSession().getAttribute("sysUser");

		List<String> urls = null;
		urls = (List<String>) session.getAttribute("urls");
		if (urls == null) {
			urls = this.sysUserService.selectUrlsByUserId(sysUser.getSysUserId());
			session.setAttribute("urls", urls);
		}

		if (!urls.contains(url)) {
			response.getWriter().write(StatusCode.NO_AUTHORITY);
			return false;
		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
	}
}