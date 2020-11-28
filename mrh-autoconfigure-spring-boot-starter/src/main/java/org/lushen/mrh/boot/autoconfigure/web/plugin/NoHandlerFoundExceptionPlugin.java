package org.lushen.mrh.boot.autoconfigure.web.plugin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lushen.mrh.boot.autoconfigure.web.ErrorHandlerPlugin;
import org.lushen.mrh.support.generic.status.GenericStatus;
import org.lushen.mrh.support.generic.view.GenericResult;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * {@link NoHandlerFoundException}
 * 
 * @author hlm
 */
public class NoHandlerFoundExceptionPlugin implements ErrorHandlerPlugin {

	private final Log log = LogFactory.getLog(getClass().getSimpleName());

	@Override
	public boolean supports(Throwable cause) {
		return cause instanceof NoHandlerFoundException;
	}

	@Override
	public GenericResult handle(Throwable cause) {
		log.error(cause.getMessage());
		GenericStatus status = GenericStatus.NOT_FOUND;
		return new GenericResult(status.getErrcode(), status.getErrmsg());
	}

}
