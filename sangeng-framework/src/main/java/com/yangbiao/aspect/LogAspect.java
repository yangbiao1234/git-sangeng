package com.yangbiao.aspect;

import com.alibaba.fastjson.JSON;
import com.yangbiao.annotation.SystemLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

//这是一个切面类
@Slf4j
@Component
@Aspect
public class LogAspect {

    /**
     * 切点  @Pointcut
     * 通过注解方式增加切点 @annotation
     */
    @Pointcut("@annotation(com.yangbiao.annotation.SystemLog)")
    public void pt() {

    }


    /**
     * 五种通知之一的环绕通知
     * joinPoint 被增强方法的信息封装的对象
     */
    @Around("pt()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {

        //joinPoint.proceed():目标方法的调用       try快捷键: CTRL + ALT +T
        Object ret;
        try {
            handlebefore(joinPoint);
            ret = joinPoint.proceed();
            handlerAfter(ret);

        } finally {
            // 结束后换行
            log.info("=======End=======" + System.lineSeparator());

        }

        return ret;
    }

    private void handlerAfter(Object ret) {
        // 打印出参
        log.info("Response       : {}", JSON.toJSONString(ret));
    }

    private void handlebefore(ProceedingJoinPoint joinPoint) {

        // 打印请求 URL
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        //打印描述信息
        //获取被增强方法上的注解对象
        SystemLog systemLog = getSystemLog(joinPoint);


        log.info("=======Start=======");
        // 打印请求 URL(可以获取当前线程的请求对象)
        log.info("URL            : {}", request.getRequestURL());
        // 打印描述信息
        log.info("BusinessName   : {}", systemLog.businessName());
        // 打印 Http method
        log.info("HTTP Method    : {}", request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        log.info("Class Method   : {}.{}",
                joinPoint.getSignature().getDeclaringTypeName(),
                ((MethodSignature) joinPoint.getSignature()).getName());
        // 打印请求的 IP
        log.info("IP             : {}", request.getRemoteHost());
        // 打印请求入参(被增强方法的信息)
        log.info("Request Args   : {}", JSON.toJSONString(joinPoint.getArgs()));
    }

    /**
     * 打印描述信息
     * 获取被增强方法上的注解对象
     *
     * @param joinPoint
     * @return
     */
    private SystemLog getSystemLog(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        SystemLog systemLog = methodSignature.getMethod().getAnnotation(SystemLog.class);
        return systemLog;
    }
}
