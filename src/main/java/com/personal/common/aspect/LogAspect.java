package com.personal.common.aspect;

import com.personal.common.annotation.Log;
import com.personal.common.config.ProjectProperties;
import com.personal.common.dao.LogDao;
import com.personal.common.domain.LogDO;
import com.personal.common.exception.IFastException;
import com.personal.common.enums.EnumErrorCode;
import com.personal.common.utils.*;
import com.personal.sys.domain.UserDO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

/**
 * 日志切面
 */
@Data
@Slf4j
@Aspect
@Component
public class LogAspect {

    @Resource
    private LogDao logMapper;
    @Autowired
    private ProjectProperties projectProperties;

    @Pointcut("execution(public * com.personal..*.controller.*.*(..))")
    public void logController() {}

    /**
     * 记录controller日志，包括请求、ip、参数、响应结果
     */
    @Around("logController()")
    public Object controller(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        long time = 0L;
        Object result;
        try {
            result = point.proceed();
            time = System.currentTimeMillis() - beginTime;
        }catch (Exception e){

            if(e instanceof IllegalArgumentException){
                result = Result.build(EnumErrorCode.illegalArgument.getCode(), e.getMessage());
            }else if(e instanceof IFastException){
                log.info("已知异常：{}, 继续抛出",e.getClass());
                result = Result.buildByErrorCode(e.getMessage());
            }else {
                log.info("未知异常：{}, 继续抛出",e.getClass());
                result = Result.build(EnumErrorCode.unknowFail.getCode(), EnumErrorCode.unknowFail.getMsg());
            }

            try {
                print(point, result, time, projectProperties.isLogPretty());
                saveLog(point, time);
            } catch (Exception e1) {
                log.warn("日志输出|保存错误: {}", e1.getMessage());
            }
            throw e;
        }
        try {
            print(point, result, time, projectProperties.isLogPretty());
            saveLog(point, time);
        } catch (Exception e) {
            log.warn("日志输出|保存错误: {}", e.getMessage());
        }
        return result;
    }

    /**
     * 日志打印
     * 注意：
     * 1. 被外层拦截器拦截将不会记录
     * 2. 有些特殊情况下打印的日志与实际返回不相同，可参考全局异常处理
     * 3. 用于常规请求的日志打印，方便调试
     * 4. 没有被@Log注解的方法不会记录
     */
    private void print(ProceedingJoinPoint point, Object result, long time, boolean isLogPretty) {
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        UserDO sysUser = ShiroUtils.getSysUser();
        if(Objects.isNull(sysUser)){
            sysUser = new UserDO();
        }
        Integer userId = sysUser.getId();
        String username = sysUser.getUsername();

        if (isLogPretty) {
            log.info("User request info  ---- {} ---- S", DateUtils.getLocalDateTime());
            log.info("请求接口: {} {}@{} {} {}.{}", request.getMethod(), request.getRequestURI(), userId, IPUtils.getIpAddr(request), point.getTarget().getClass().getSimpleName(), point.getSignature().getName());
//            log.info("请求参数:{}", JSONUtils.beanToJson(point.getArgs()));
            log.info("请求耗时:{} ms", time);
            log.info("请求用户:{} [{}]", userId, username);
            log.info("请求结果:{}", JSONUtils.beanToJson(result));
            log.info("---------{}--------- E", DateUtils.getLocalDateTime());
        } else {
            log.info("【请求】：{} {}@{}[{}] {} {}.{}{} (耗时 {} ms) 【返回】：{}", request.getMethod(), request.getRequestURI(), userId, username, IPUtils.getIpAddr(request), point.getTarget().getClass().getSimpleName(), point.getSignature().getName(), JSONUtils.beanToJson(point.getArgs()), time, JSONUtils.beanToJson(result));
        }
    }

    /**
     * 保存日志
     */
    private void saveLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LogDO sysLog = new LogDO();
        Log syslog = method.getAnnotation(Log.class);
        // 如果方法上有注解 - 才保存日志
        if (syslog != null) {
            // 注解上的描述
            sysLog.setOperation(syslog.value());
            // 请求的方法名
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = signature.getName();
            String params;
            HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
            if (request != null) {
                sysLog.setMethod(request.getMethod() + " " + request.getRequestURI());
                Map<String, String[]> parameterMap = request.getParameterMap();
                params = JSONUtils.beanToJson(parameterMap);
                // 设置IP地址
                sysLog.setIp(IPUtils.getIpAddr(request));
            } else {
                sysLog.setMethod(className + "." + methodName + "()");
                Object[] args = joinPoint.getArgs();
                params = JSONUtils.beanToJson(args);
            }
            int maxLength = 4999;
            if (params.length() > maxLength) {
                params = params.substring(0, maxLength);
            }
            sysLog.setParams(params);
            // 用户名
            UserDO currUser = ShiroUtils.getSysUser();
            if (null == currUser) {
                sysLog.setUserId(-1);
                sysLog.setUsername("");
            } else {
                sysLog.setUserId(currUser.getId());
                sysLog.setUsername(currUser.getUsername());
            }
            sysLog.setTime((int) time);
            // 系统当前时间
            sysLog.setCreateTime(LocalDateTime.now());
            // 保存系统日志
            logMapper.insert(sysLog);
        }
    }
}
