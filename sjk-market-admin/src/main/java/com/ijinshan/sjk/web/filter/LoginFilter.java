package com.ijinshan.sjk.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ijinshan.sjk.po.User;

/**
 * Servlet Filter implementation class LoginCheckFilter for check user is login.
 */
@WebFilter(urlPatterns = { "*.d", "*.json" })
public class LoginFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    /**
     * Default constructor.
     */
    public LoginFilter() {

    }

    /**
     * @see Filter#destroy()
     */
    @Override
    public void destroy() {
        logger.info("Login filter destroy ...");
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        // 开始权限过滤
        // 1 判断是否登录
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String reqPath = req.getServletPath();
        if ((reqPath.endsWith(".d") || reqPath.endsWith(".json")) && reqPath.indexOf("doLogin.d") == -1
                && reqPath.indexOf("doLogoff.d") == -1 && reqPath.indexOf("userReg.d") == -1) {
            User u = (User) req.getSession().getAttribute("user");
            if (null == u) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
                // PrintWriter out = resp.getWriter();
                // req.getRequestDispatcher("/login.html").forward(req,
                // resp);
                // return;
                // if (reqPath.startsWith("/user")) {
                // resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                // //
                // req.getRequestDispatcher("/user/getCurUserInfo.d").forward(req,
                // resp);
                // return;
                // } else {
                // req.getRequestDispatcher("/login.html").forward(req, resp);
                // return;
                // }
            }

        }

        // logger.debug("login check ...{}" + reqPath);
        chain.doFilter(request, response);
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    @Override
    public void init(FilterConfig fConfig) throws ServletException {
        logger.info("Login filter start ...");
    }

}
