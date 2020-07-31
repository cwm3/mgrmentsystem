/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package org.cwm3.mgrsystem.exception;

import org.cwm3.mgrsystem.common.entily.RespBean;
import org.cwm3.mgrsystem.common.entity.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 异常处理器
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestControllerAdvice
public class RRExceptionHandler {

}
//	private Logger logger = LoggerFactory.getLogger(getClass());
//
//	/**
//	 * 处理自定义异常
//	 */
//	@ExceptionHandler(RRException.class)
//	public RespBean handleRRException(RRException e){
//		RespBean r = new RespBean();
//		r.put("code", e.getCode());
//		r.put("msg", e.getMessage());
//		return r;
//	}
//
//	@ExceptionHandler(NoHandlerFoundException.class)
//	public RespBean handlerNoFoundException(Exception e) {
//		logger.error(e.getMessage(), e);
//		return  RespBean.error(StatusCode.REQUEST_CODE_UNKNOWN, "路径不存在，请检查路径是否正确");
//	}
//
//	@ExceptionHandler(DuplicateKeyException.class)
//	public RespBean handleDuplicateKeyException(DuplicateKeyException e){
//		logger.error(e.getMessage(), e);
//		return RespBean.error("数据库中已存在该记录");
//	}
//
////	@ExceptionHandler(AuthorizationException.class)
////	public RespBean handleAuthorizationException(AuthorizationException e){
////		logger.error(e.getMessage(), e);
////		return RespBean.error("没有权限，请联系管理员授权");
////	}
//
//	@ExceptionHandler(Exception.class)
//	public RespBean handleException(Exception e){
//		logger.error(e.getMessage(), e);
//		return RespBean.error();
//	}
//}
