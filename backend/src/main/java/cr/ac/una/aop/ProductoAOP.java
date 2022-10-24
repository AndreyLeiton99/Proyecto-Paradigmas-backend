package cr.ac.una.aop;

import cr.ac.una.entity.Log;
import cr.ac.una.repository.LogRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
public class ProductoAOP {

    @Autowired
    private LogRepository logRepository;

    @Before("execution(* cr.ac.una.repository.ProductoRepository.findAll(..))")
    public void logBeforeFindAll(JoinPoint joinPoint){
        logRepository.save(new Log(joinPoint.getSignature().getName(), new Date()));
    }
    @Before("execution(* cr.ac.una.repository.ProductoRepository.findById(..))")
    public void logBeforeFindById(JoinPoint joinPoint){
        logRepository.save(new Log(joinPoint.getSignature().getName(), new Date()));
    }

    @Before("execution(* cr.ac.una.repository.ProductoRepository.save(..))")
    public void logBeforeSave(JoinPoint joinPoint){
        logRepository.save(new Log(joinPoint.getSignature().getName(), new Date()));
    }

    @Before("execution(* cr.ac.una.repository.ProductoRepository.delete(..))")
    public void logBeforeDelete(JoinPoint joinPoint){
        logRepository.save(new Log(joinPoint.getSignature().getName(), new Date()));
    }

}
