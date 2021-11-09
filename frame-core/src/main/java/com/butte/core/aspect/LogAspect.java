package com.butte.core.aspect;

import cn.hutool.core.date.StopWatch;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.butte.base.constant.BizOptEnum;
import com.butte.core.entity.LogRecord;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
@Aspect
@Slf4j
public class LogAspect {

    @Value("${spring.application.name:}")
    private String appName;

    private SpelExpressionParser spelExpressionParser = new SpelExpressionParser();

    @Pointcut("@annotation(logAop)")
    public void logPoint(LogAop logAop) {

    }

    @Around(value = "logPoint(logAop)", argNames = "joinPoint,logAop")
    public Object logAround(ProceedingJoinPoint joinPoint, LogAop logAop) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object result = null ;
        try{
            result = joinPoint.proceed();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            LogRecord logRecord = parsePoint(joinPoint);
            stopWatch.stop();
            long spanTime = stopWatch.getTotalTimeMillis() ;
            saveLogRecord(logRecord,spanTime);
            log.info("span time: {} ms",spanTime);
        }
        return result ;
    }

    private void saveLogRecord (LogRecord logRecord,long spanTime){
        try {
            if (ObjectUtil.isNotNull(logRecord)){
                logRecord.setSpanTime(spanTime);
                // 打印日志内容
                log.info(JSONUtil.toJsonPrettyStr(logRecord));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private LogRecord parsePoint (ProceedingJoinPoint joinPoint){
        try {
            // 方法结构解析
            String className = joinPoint.getTarget().getClass().getName();
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            String methodName = methodSignature.getMethod().getName();
            String[] paramArr = methodSignature.getParameterNames();
            Object[] argsArr = joinPoint.getArgs();
            LogAop logAop = methodSignature.getMethod().getAnnotation(LogAop.class);

            // 业务标识解析
            String bizSign = logAop.bizSign() ;
            BizOptEnum bizOptEnum = logAop.optSign();
            Expression expression = spelExpressionParser.parseExpression(bizSign);
            EvaluationContext context = new StandardEvaluationContext();
            for (int i = 0; i < argsArr.length; i++) {
                context.setVariable(paramArr[i], argsArr[i]);
            }
            String bizSignValue = String.valueOf(expression.getValue(context)) ;

            // 构建日志实体
            return LogRecord.builder().appName(appName)
                    .className(className).methodName(methodName).bizSign(bizSignValue).optSign(bizOptEnum.getOpt())
                    .paramArr(paramArr).argsArr(argsArr).createTime(new Date()).build();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null ;
    }
}