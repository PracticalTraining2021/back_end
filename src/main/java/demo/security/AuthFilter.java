package demo.security;

import demo.utils.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 拦截 /auth 下的请求，进行token验证
@Component
public class AuthFilter implements Filter {

    @Resource
    private JwtUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        String uri = httpServletRequest.getRequestURI();
        if (uri.startsWith("/userLogin") || uri.startsWith("/userRegister")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String token = null;
        String bearerToken = httpServletRequest.getHeader(JwtUtil.TOKEN_HEADER_KEY);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtUtil.TOKEN_START_WITH)) {
            token = bearerToken.substring(JwtUtil.TOKEN_START_WITH.length());
        }

        if (StringUtils.hasText(token) && jwtUtil.validateToken(token)) {
//            Authentication authentication = jwtUtil.getAuthenticationFromToken(token);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            System.out.println("set:uri:" + httpServletRequest.getRequestURI() + ";authentication:" + authentication);
            System.out.println("token验证成功");
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        System.out.println("fail:uri:" + httpServletRequest.getRequestURI());
        httpServletResponse.sendError(401, "请先登录");
    }
}
