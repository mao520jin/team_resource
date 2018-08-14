package com.wsxd.sync.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.wsxd.sync.model.TokenModel;
import com.wsxd.sync.model.power.SysUser;
import com.wsxd.sync.service.RedisTokenService;
import com.wsxd.sync.service.SysUserService;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private RedisTokenService manager;

	@Autowired
	private SysUserService sysUserService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		// 跨域设置
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, token");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT,DELETE");

		String method = request.getMethod();
		if ("OPTIONS".equalsIgnoreCase(method)) {
			return false;
		}

		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String url = requestUri.substring(contextPath.length());
		if (url.contains("/")) {
			url = url.replaceAll("/", "");
		}
		if (url.contains("\\")) {
			url = url.replaceAll("\\", "");
		}

		if ("sysUserlogin".equalsIgnoreCase(url)) {
			return true;
		}

		String token = request.getHeader("token");
		TokenModel model = this.manager.getToken(token);
		if (!this.manager.checkToken(model)) {
			response.getWriter().write("400");
			return false;
		}

		HttpSession session = request.getSession();

		SysUser sysUser = null;
		sysUser = (SysUser) session.getAttribute("sysUser");
		if (sysUser == null) {
			sysUser = this.sysUserService.selectSysUserById(model.getUserId());
			session.setAttribute("sysUser", sysUser);
		}

		request.setAttribute("userId", model.getUserId());
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
	}
}