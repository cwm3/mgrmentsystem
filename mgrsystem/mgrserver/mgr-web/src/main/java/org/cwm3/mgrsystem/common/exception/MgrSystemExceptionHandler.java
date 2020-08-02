/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package org.cwm3.mgrsystem.common.exception;

import org.cwm3.mgrsystem.common.entily.RespBean;
import org.cwm3.mgrsystem.common.entity.StatusCode;
import org.cwm3.mgrsystem.common.exception.MgrSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

/**
 * 异常处理器
 *
 */
@RestControllerAdvice
public class MgrSystemExceptionHandler {


	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 处理自定义异常
	 */
	@ExceptionHandler(MgrSystemException.class)
	public RespBean handleMgrSystemException(MgrSystemException e){
		RespBean r = new RespBean();
		r.put("code", e.getCode());
		r.put("msg", e.getMessage());
		return r;
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public RespBean handlerNoFoundException(Exception e) {
		logger.error(e.getMessage(), e);
		return new RespBean(StatusCode.REQUEST_CODE_UNKNOWN, "路径不存在，请检查路径是否正确");
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public RespBean handleDuplicateKeyException(DuplicateKeyException e){
		logger.error(e.getMessage(), e);
		return RespBean.error("数据库中已存在该记录");
	}

//	@ExceptionHandler(AuthorizationException.class)
//	public RespBean handleAuthorizationException(AuthorizationException e){
//		logger.error(e.getMessage(), e);
//		return RespBean.error("没有权限，请联系管理员授权");
//	}

	@ExceptionHandler(Exception.class)
	public RespBean handleException(Exception e){
		logger.error(e.getMessage(), e);
		return new RespBean(StatusCode.REQUEST_CODE_UNKNOWN,"系统异常");
	}
	@ExceptionHandler(value= MethodArgumentNotValidException.class)
	public RespBean handleVaildException(MethodArgumentNotValidException e){
		logger.error("数据校验出现问题{}，异常类型：{}",e.getMessage(),e.getClass());
		BindingResult bindingResult = e.getBindingResult();
		Map<String,String> errorMap = new HashMap<>();
		bindingResult.getFieldErrors().forEach((fieldError)->{
			errorMap.put(fieldError.getField(),fieldError.getDefaultMessage());
		});
		return new RespBean(StatusCode.REQUEST_CODE_BAD,"",errorMap);
	}

    @ExceptionHandler(SQLException.class)
    public RespBean sqlException(SQLException e) {
        if (e instanceof SQLIntegrityConstraintViolationException) {
            return RespBean.error("该数据有关联数据，操作失败!");
        }
        return RespBean.error("数据库异常，操作失败!");
    }
}
