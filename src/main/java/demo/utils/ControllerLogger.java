package demo.utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class ControllerLogger {
    private final static Logger logger = LoggerFactory.getLogger(ControllerLogger.class);

    @Before("execution(public * demo.controller..*.*(..))")
    public void logBeforeController(JoinPoint joinPoint) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();//这个RequestContextHolder是Springmvc提供来获得请求的东西
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        // 记录下请求内容
        logger.info("===========================================");
        logger.info("URI : {}", request.getRequestURI());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());

        //下面这个getSignature().getDeclaringTypeName()是获取包+类名的   然后后面的joinPoint.getSignature.getName()获取了方法名
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
//        logger.info("TARGET: " + joinPoint.getTarget());//返回的是需要加强的目标类的对象
//        logger.info("THIS: " + joinPoint.getThis());//返回的是经过加强后的代理类的对象
    }

}
