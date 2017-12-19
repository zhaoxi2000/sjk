package com.ijinshan.sjk.web.filter;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IPFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    protected FilterConfig filterConfig;
    protected String notFilteredIPs; // 可以访问的IP

    @Override
    public void init(FilterConfig config) throws ServletException {
        this.filterConfig = config;
        notFilteredIPs = config.getInitParameter("notFilteredIPs");// 从web.xml中获取初始化参数
        logger.debug("可以访问的IP:{}", notFilteredIPs);
        if (notFilteredIPs == null)
            notFilteredIPs = "";

    }

    /* 过滤器方法 */
    @Override
    public void doFilter(ServletRequest reg, ServletResponse res, FilterChain chain) throws IOException,
            ServletException {
        RequestDispatcher reqDispatcher = reg.getRequestDispatcher("deny.html");
        String remoteIP = reg.getRemoteAddr();// 获取访问服务器的IP
        String[] ips = null;
        boolean isAllow = false;
        Pattern pattern = null;
        if (!StringUtils.isEmpty(notFilteredIPs)) {
            ips = notFilteredIPs.split(";");
            for (String ip : ips) {
                pattern = Pattern.compile(ip);
                if (pattern.matcher(remoteIP).find()) {
                    isAllow = true;
                    break;
                }
            }
        } else {
            isAllow = true;
        }
        if (isAllow) {
            // 如果不是要过滤的IP就放行，交给下一个Filter
            chain.doFilter(reg, res);
        } else {
            logger.error("访问服务器的IP:" + remoteIP + " , 该IP地址无法访问");
            // 如果是要过滤的IP，就通过reqDispatcher跳转到错误页面。
            reqDispatcher.forward(reg, res);
        }
    }

    @Override
    public void destroy() {
        this.filterConfig = null;
    }

}
