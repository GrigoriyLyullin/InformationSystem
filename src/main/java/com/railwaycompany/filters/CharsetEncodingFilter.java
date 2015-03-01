package com.railwaycompany.filters;

import javax.servlet.*;
import java.io.IOException;

public class CharsetEncodingFilter implements Filter {

    private static final String UTF_8_ENCODING = "UTF-8";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        if (servletRequest.getCharacterEncoding() == null) {
            servletRequest.setCharacterEncoding(UTF_8_ENCODING);
        }
        servletResponse.setContentType("text/html; charset=UTF-8");
        servletResponse.setCharacterEncoding("UTF-8");

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
