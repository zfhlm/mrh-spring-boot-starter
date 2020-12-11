package org.lushen.mrh.boot.autoconfigure.webmvc.advice.plugin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lushen.mrh.boot.autoconfigure.support.error.GenericStatus;
import org.lushen.mrh.boot.autoconfigure.support.view.GenericResult;
import org.lushen.mrh.boot.autoconfigure.webmvc.advice.ExceptionPlugin;
import org.springframework.web.bind.MissingRequestHeaderException;

/**
 * {@link MissingRequestHeaderException}
 * 
 * @author hlm
 */
public class MissingRequestHeaderExceptionPlugin implements ExceptionPlugin {

	private final Log log = LogFactory.getLog(getClass().getSimpleName());

	@Override
	public boolean supports(Throwable cause) {
		return cause instanceof MissingRequestHeaderException;
	}

	@Override
	public GenericResult handle(Throwable cause) {
		log.error(cause.getMessage());
		GenericStatus status = GenericStatus.BAD_REQUEST;
		return new GenericResult(status.getErrcode(), status.getErrmsg());
	}

}
