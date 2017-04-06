package ist.meic.cnv.config;

import ist.meic.cnv.service.TokenService;
import org.apache.catalina.servlet4preview.GenericFilter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;

/**
 * Created by Diogo on 06/04/2017.
 */
public class TokenFilter extends GenericFilter {

    private TokenService tokenService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // since GenericFilter is not associated with spring
        if(tokenService == null){
            ServletContext servletContext = servletRequest.getServletContext();
            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            tokenService = webApplicationContext.getBean(TokenService.class);
        }
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String token = req.getHeader("X-Token");
        // our map has no null values
        // the header username is not allowed since we use it on the wrapper
        if(token == null || tokenService.getUsername(token) == null || req.getHeader("Username") != null)
            throw new ServletException("Http Header Invalid");

        // we could verify the token, but there is no need for that, since we use as our
        // key a UUID.Random which is impossible to guess
        filterChain.doFilter(new HttpServletRequestWrapper(req), servletResponse);
    }

    @Override
    public void destroy() {
    }
}
