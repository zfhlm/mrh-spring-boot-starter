package org.lushen.mrh.boot.autoconfigure.web.plugin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lushen.mrh.boot.autoconfigure.web.ErrorHandlerPlugin;
import org.lushen.mrh.support.generic.exp.GenericBizException;
import org.lushen.mrh.support.generic.exp.GenericException;
import org.lushen.mrh.support.generic.status.GenericStatus;
import org.lushen.mrh.support.generic.view.GenericResult;

/**
 * {@link GenericException}
 * 
 * @author hlm
 */
public class GenericExceptionPlugin implements ErrorHandlerPlugin {

	private final Log log = LogFactory.getLog(getClass().getSimpleName());

	@Override
	public boolean supports(Throwable cause) {
		return cause instanceof GenericException;
	}

	@Override
	public GenericResult handle(Throwable cause) {
		if(cause instanceof GenericBizException) {
			log.error(cause.getMessage());
		} else {
			log.error(cause.getMessage(), cause);
		}
		GenericStatus status = ((GenericException)cause).getStatus();
		return new GenericResult(status.getErrcode(), status.getErrmsg());
	}

}
