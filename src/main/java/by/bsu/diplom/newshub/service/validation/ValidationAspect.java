package by.bsu.diplom.newshub.service.validation;

import by.bsu.diplom.newshub.exception.ValidationException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;

import java.lang.annotation.Annotation;

/**
 * Aspect for validator parameters in methods
 * that annotated with {@link Validation} annotation
 */
@Aspect
@Component
public class ValidationAspect {
    private Validator validator;

    public ValidationAspect(Validator validator) {
        this.validator = validator;
    }

    @Pointcut("execution(public * *(..))")
    private void methodExecution() {
    }

    @Pointcut("@annotation(by.bsu.diplom.newshub.service.validation.Validation)")
    private void annotatedMethod() {
    }

    @Before(value = "methodExecution() && annotatedMethod()")
    public void doBefore(JoinPoint pointcut) {
        Annotation[][] parametersAnnotations = ((MethodSignature) pointcut.getSignature()).getMethod().getParameterAnnotations();
        for (int i = 0; i < parametersAnnotations.length; i++) {
            for (Annotation annotation : parametersAnnotations[i]) {
                if (annotation.annotationType() == Validated.class) {
                    Object param = pointcut.getArgs()[i];
                    if (param == null) {
                        continue;
                    }
                    processValidation(param);
                }
            }
        }
    }

    private void processValidation(Object param) {
        if (param instanceof Iterable) {
            Iterable list = (Iterable) param;
            for (Object entity : list) {
                validateEntity(entity);
            }
        } else {
            validateEntity(param);
        }
    }

    private void validateEntity(Object entity) {
        DataBinder dataBinder = new DataBinder(entity);
        dataBinder.setValidator(validator);
        dataBinder.validate();
        BindingResult errors = dataBinder.getBindingResult();
        if (errors.hasFieldErrors()) {
            throw new ValidationException(errors);
        }
    }
}
