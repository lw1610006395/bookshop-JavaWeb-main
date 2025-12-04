package com.bookshop.filter;

import com.bookshop.utils.JDBCUtilsByDruid;

import javax.servlet.*;
import java.io.IOException;

public class TransactionFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    // 修改TransactionFilter的doFilter方法
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            filterChain.doFilter(servletRequest, servletResponse);
            JDBCUtilsByDruid.commitAndClose(); // 提交并关闭连接
        } catch (Exception e) {
            JDBCUtilsByDruid.rollbackAndClose(); // 异常时回滚并关闭
            throw new RuntimeException(e); // 抛出异常便于排查
        }
    }

    @Override
    public void destroy() {

    }
}
