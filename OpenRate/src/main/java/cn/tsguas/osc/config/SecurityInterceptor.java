package cn.tsguas.osc.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 请求拦截器
 *
 * @author osc
 */
@Component
@Slf4j
public class SecurityInterceptor implements HandlerInterceptor {

    @Value("${api.key}")
    private String API_KEY;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从请求头中获取密钥
        String apiKey = request.getHeader("OSC-API-KEY");

        // 判断请求头是否包含密钥，且密钥是否有效
        if (!API_KEY.equals(apiKey)) {
            // 密钥不匹配，返回 403 Forbidden 错误
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Forbidden: Invalid or missing API key");
            return false;  // 阻止请求继续处理
        }

        // 密钥匹配，允许请求继续
        return true;
    }
}
