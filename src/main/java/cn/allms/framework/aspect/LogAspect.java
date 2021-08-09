package cn.allms.framework.aspect;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 日志
 *
 * @author rainerosion
 * @date 2021/4/9 19:11
 */
@Slf4j
@Component
@Aspect
public class LogAspect {
    @Pointcut("execution(* cn.allms.controller.*.*(..))")
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        // 开始打印请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();

        // 打印请求相关参数
        log.info("========================================== Start ==========================================");
        // 打印请求 url
        log.info("[请求URL]      : {}", request.getRequestURL().toString());
        // 打印 Http method
        log.info("[请求方法]      : {}", request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        log.info("[请求类名]      : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        // 打印请求的 IP
        log.info("[请求IP]       : {}", request.getRemoteAddr());
        // 打印请求入参
        log.info("[请求参数]      : {}", JSONObject.toJSONString(joinPoint.getArgs()));
    }

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        // 打印出参
        log.info("[响应结果]      : {}", JSONObject.toJSONString(result));
        // 执行耗时
        log.info("[请求耗时]      : {} ms", System.currentTimeMillis() - startTime);
        log.info("=========================================== End ===========================================");
        return result;
    }
}
