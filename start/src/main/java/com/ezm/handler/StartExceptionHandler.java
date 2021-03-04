package com.ezm.handler;

import com.ezm.common.exception.ExceptionEnum;
import com.ezm.common.exception.ExceptionResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.thymeleaf.exceptions.TemplateInputException;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class StartExceptionHandler {


    /**
     * 未知异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ExceptionResult exception(Exception e) {
        log(e);
        return new ExceptionResult(ExceptionEnum.Exception);
    }


    /**
     * 算术异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = BadCredentialsException.class)
    public ExceptionResult badCredentialsException(BadCredentialsException e) {
        log(e);
        return new ExceptionResult(ExceptionEnum.BadCredentialsException);
    }


    /**
     * 算术异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = DisabledException.class)
    public ExceptionResult disabledException(DisabledException e) {
        log(e);
        return new ExceptionResult(ExceptionEnum.DisabledException);
    }


    /**
     * 算术异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = ArithmeticException.class)
    public ExceptionResult arithmeticException(ArithmeticException e) {
        log(e);
        return new ExceptionResult(ExceptionEnum.ArithmeticException);
    }


    /**
     * 空指针异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = NullPointerException.class)
    public ExceptionResult nullPointerException(NullPointerException e) {
        log(e);
        return new ExceptionResult(ExceptionEnum.NullPointerException);
    }


    /**
     * 类强制转换异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ClassCastException.class)
    public ExceptionResult classCastException(ClassCastException e) {
        log(e);
        return new ExceptionResult(ExceptionEnum.ClassCastException);
    }


    /**
     * 数字格式异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(NumberFormatException.class)
    public ExceptionResult numberFormatException(NumberFormatException e) {
        log(e);
        return new ExceptionResult(ExceptionEnum.NumberFormatException);
    }


    /**
     * 数组索引超出范围异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
    public ExceptionResult arrayIndexOutOfBoundsException(ArrayIndexOutOfBoundsException e) {
        log(e);
        return new ExceptionResult(ExceptionEnum.ArrayIndexOutOfBoundsException);
    }


    /**
     * 文件未找到异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(FileNotFoundException.class)
    public ExceptionResult fileNotFoundException(FileNotFoundException e) {
        log(e);
        return new ExceptionResult(ExceptionEnum.FileNotFoundException);
    }


    /**
     * 文件未找到异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(RequestRejectedException.class)
    public ExceptionResult requestRejectedException(RequestRejectedException e) {
        log(e);
        return new ExceptionResult(ExceptionEnum.RequestRejectedException);
    }


    /**
     * 文件未找到异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(EOFException.class)
    public ExceptionResult eOFException(EOFException e) {
        log(e);
        return new ExceptionResult(ExceptionEnum.EOFException);
    }


    /**
     * 输入输出异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(IOException.class)
    public ExceptionResult iOException(IOException e) {
        log(e);
        return new ExceptionResult(ExceptionEnum.IOException);
    }


    /**
     * 操作数据库异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(SQLException.class)
    public ExceptionResult sQLException(SQLException e) {
        log(e);
        return new ExceptionResult(ExceptionEnum.SQLException);
    }


    /**
     * 方法未找到异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(NoSuchMethodException.class)
    public ExceptionResult noSuchMethodException(NoSuchMethodException e) {
        log(e);
        return new ExceptionResult(ExceptionEnum.NoSuchMethodException);
    }


    /**
     * formdata数据格式validation验证
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    public ExceptionResult bindException(BindException e) {
        log(e);

        FieldError fieldError = e.getFieldError();
        String defaultMessage = fieldError.getDefaultMessage();
        String field = fieldError.getField();
        return new ExceptionResult(1, field + defaultMessage);

    }

    /**
     * json格式的validation数据验证
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionResult methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log(e);

        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        FieldError fieldError = fieldErrors.get(0);

        String defaultMessage = fieldError.getDefaultMessage();
        String field = fieldError.getField();

        return new ExceptionResult(1, field + defaultMessage);

    }




    @ExceptionHandler(UnexpectedRollbackException.class)
    public ExceptionResult unexpectedRollbackException(UnexpectedRollbackException e) {
        log(e);
        return new ExceptionResult(ExceptionEnum.UnexpectedRollbackException);
    }




    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ExceptionResult httpMessageNotReadableException(HttpMessageNotReadableException e) {
        log(e);
        return new ExceptionResult(ExceptionEnum.HttpMessageNotReadableException);
    }



    @ExceptionHandler(RuntimeException.class)
    public ExceptionResult runtimeException(RuntimeException e) {
        log(e);
        return new ExceptionResult(ExceptionEnum.RuntimeException);
    }


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ExceptionResult httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log(e);
        return new ExceptionResult(ExceptionEnum.HttpRequestMethodNotSupportedException);
    }

    @ExceptionHandler(TemplateInputException.class)
    public ExceptionResult templateInputException(TemplateInputException e) {
        log(e);
        return new ExceptionResult(ExceptionEnum.TemplateInputException);
    }




    private void log(Exception e) {
        e.printStackTrace();
        StackTraceElement[] stackTrace = e.getStackTrace();
        for (int i = 0; i < stackTrace.length; i++) {
            StackTraceElement stackTraceElement = stackTrace[i];
            StringBuffer sb = new StringBuffer();
            sb.append(stackTraceElement.getClassName());
            sb.append(".");
            sb.append(stackTraceElement.getMethodName());
            sb.append("(");
            sb.append(stackTraceElement.getFileName());
            sb.append(":");
            sb.append(stackTraceElement.getLineNumber());
            sb.append(")");
            log.error(sb.toString());
        }
    }


}
