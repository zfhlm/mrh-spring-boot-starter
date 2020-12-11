package org.lushen.mrh.boot.autoconfigure.webmvc.advice.plugin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lushen.mrh.boot.autoconfigure.webmvc.advice.ExceptionPlugin;
import org.lushen.mrh.support.generic.status.GenericStatus;
import org.lushen.mrh.support.generic.view.GenericResult;
import org.springframework.http.converter.HttpMessageNotReadableException;

/**
 * {@link HttpMessageNotReadableException}
 * 
 * @author hlm
 */
public class HttpMessageNotReadableExceptionPlugin implements ExceptionPlugin {

	private final Log log = LogFactory.getLog(getClass().getSimpleName());

	@Override
	public boolean supports(Throwable cause) {
		return cause instanceof HttpMessageNotReadableException;
	}

	@Override
	public GenericResult handle(Throwable cause) {
		log.error(cause.getMessage());
		GenericStatus status = GenericStatus.NOT_ACCEPTABLE;
		return new GenericResult(status.getErrcode(), status.getErrmsg());
	}

}
