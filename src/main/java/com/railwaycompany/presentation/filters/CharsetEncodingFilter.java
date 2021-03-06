package com.railwaycompany.presentation.filters;

import org.apache.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;

public class CharsetEncodingFilter implements Filter {

    private static final Logger LOG = Logger.getLogger(CharsetEncodingFilter.class.getName());

    private static final String UTF_8_ENCODING = "UTF-8";

    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOG.info("Charset encoding filter initializing...");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        servletRequest.setCharacterEncoding(UTF_8_ENCODING);
        servletResponse.setCharacterEncoding(UTF_8_ENCODING);
        servletResponse.setContentType(CONTENT_TYPE);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        LOG.info("Charset encoding filter has been destroyed");
    }
}
