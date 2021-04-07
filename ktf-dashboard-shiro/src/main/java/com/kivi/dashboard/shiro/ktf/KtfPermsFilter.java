package com.kivi.dashboard.shiro.ktf;

import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;

import com.kivi.framework.constant.KtfError;
import com.kivi.framework.model.ResultMap;

public class KtfPermsFilter extends AuthorizationFilter {

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		HttpServletRequest	req			= WebUtils.toHttp(request);
		HttpServletResponse	resp		= WebUtils.toHttp(response);

		String				permission	= req.getContextPath();

		Subject				subject		= getSubject(request, response);
		// If the subject isn't identified, redirect to login URL
		if (!subject.isPermitted(permission)) {
			resp.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
			resp.setHeader("Access-Control-Allow-Credentials", "true");
			resp.setContentType("application/json; charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			PrintWriter	out		= resp.getWriter();
			String		json	= ResultMap.error(KtfError.E_FORBIDDEN, "权限不足").toString();
			out.println(json);
			out.flush();
			out.close();
			return false;
		}

		return true;
	}

}
