package com.railwaycompany.filters;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AuthenticationFilter implements Filter {

    private List<String> allowedForAllUrls;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        allowedForAllUrls = new ArrayList<>();
        allowedForAllUrls.add("index.html");
        allowedForAllUrls.add("login");
        allowedForAllUrls.add("logout");

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException,
            ServletException {

        HttpServletRequest httpReq = (HttpServletRequest) req;
        HttpServletResponse httpResp = (HttpServletResponse) resp;

        String url = httpReq.getServletPath();

        if (!allowedForAllUrls.contains(url)) {

//            HttpSession session = httpReq.getSession(false);
//            if (session != null) {
//                chain.doFilter(req, resp);
//            } else {
//                httpResp.sendRedirect("/login");
//            }
        }

        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {

    }
}
