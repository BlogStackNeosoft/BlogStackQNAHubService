package com.blogstack.controller.advisor;

import com.blogstack.beans.responses.ServiceResponseBean;
import com.blogstack.commons.BlogStackCommonConstants;
import com.blogstack.exceptions.BlogStackCustomException;
import com.blogstack.exceptions.BlogstackDataNotFoundException;
import com.blogstack.utils.BlogStackCommonUtils;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import javax.naming.LimitExceededException;
import java.math.BigInteger;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class BlogStackMasterRestControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WebExchangeBindException.class)
    public ServiceResponseBean handleWebExchangeBindException(WebExchangeBindException webExchangeBindException) {
        Set<String> errorSet = webExchangeBindException.getBindingResult().getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toSet());
        errorSet = errorSet.parallelStream().map(errorCode -> {
            try {
                if (errorCode.contains(BlogStackCommonConstants.INSTANCE.SPACE_STRING)) {
                    Object[] errorCodeArray = errorCode.split(BlogStackCommonConstants.INSTANCE.SPACE_STRING);
                    String errorMessage = errorCodeArray[BigInteger.ZERO.bitCount()].toString();
                    System.arraycopy(errorCodeArray, BigInteger.ONE.intValue(), errorCodeArray, BigInteger.ZERO.intValue(), errorCodeArray.length - BigInteger.ONE.intValue());
                    return String.format(errorMessage, errorCodeArray);
                }
                return errorCode;
            } catch (Exception e) {
                return errorCode;
            }
        }).collect(Collectors.toSet());
        return ServiceResponseBean.builder()
                .errors(errorSet)
                .message(errorSet.stream().findFirst().isPresent() ? errorSet.stream().findFirst().get() : null)
                .build();
    }

    @ResponseStatus(HttpStatus.ALREADY_REPORTED)
    @ExceptionHandler(BlogStackCustomException.class)
    public ServiceResponseBean handleBlogStackCustomException(BlogStackCustomException BlogStackCustomException) {
        return ServiceResponseBean.builder()
                .message(BlogStackCustomException.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BlogstackDataNotFoundException.class)
    public ServiceResponseBean handleBlogStackDataNotFoundException(BlogstackDataNotFoundException blogStackDataNotFoundException) {
        return ServiceResponseBean.builder()
                .message(blogStackDataNotFoundException.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(LimitExceededException.class)
    public ServiceResponseBean handleLimitExceededException(LimitExceededException limitExceededException) {
        return ServiceResponseBean.builder()
                .message(BlogStackCommonUtils.INSTANCE.getMessageStringFromException(limitExceededException.getMessage()))
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ServiceResponseBean handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        Set<String> errorSet = methodArgumentNotValidException.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toSet());
        errorSet = errorSet.stream().map(errorCode -> {
            try {
                if (errorCode.contains(BlogStackCommonConstants.INSTANCE.SPACE_STRING)) {
                    Object[] errorCodeArray = errorCode.split(BlogStackCommonConstants.INSTANCE.SPACE_STRING);
                    String errorMessage = errorCodeArray[BigInteger.ZERO.bitCount()].toString();
                    System.arraycopy(errorCodeArray, BigInteger.ONE.intValue(), errorCodeArray, BigInteger.ZERO.intValue(), errorCodeArray.length - BigInteger.ONE.intValue());
                    return String.format(errorMessage, errorCodeArray);
                }
                return errorCode;
            } catch (Exception e) {
                return errorCode;
            }
        }).collect(Collectors.toSet());
        return ServiceResponseBean.builder()
                .errors(errorSet)
                .message(errorSet.stream().findFirst().isPresent() ? errorSet.stream().findFirst().get() : null)
                .build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ServiceResponseBean handleConstraintViolationException(ConstraintViolationException constraintViolationException) {
        Set<String> errorSet = constraintViolationException.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());
        errorSet = errorSet.stream().map(errorCode -> {
            try {
                if (errorCode.contains(BlogStackCommonConstants.INSTANCE.SPACE_STRING)) {
                    Object[] errorCodeArray = errorCode.split(BlogStackCommonConstants.INSTANCE.SPACE_STRING);
                    String errorMessage = errorCodeArray[BigInteger.ZERO.bitCount()].toString();
                    System.arraycopy(errorCodeArray, BigInteger.ONE.intValue(), errorCodeArray, BigInteger.ZERO.intValue(), errorCodeArray.length - BigInteger.ONE.intValue());
                    return String.format(errorMessage, errorCodeArray);
                }
                return errorCode;
            } catch (Exception e) {
                return errorCode;
            }
        }).collect(Collectors.toSet());
        return ServiceResponseBean.builder()
                .errors(errorSet)
                .message(errorSet.stream().findFirst().isPresent() ? errorSet.stream().findFirst().get() : constraintViolationException.getMessage())
                .build();
    }

}