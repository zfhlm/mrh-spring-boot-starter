package org.lushen.mrh.boot.autoconfigure.webmvc.advice.plugin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lushen.mrh.boot.autoconfigure.support.error.GenericStatus;
import org.lushen.mrh.boot.autoconfigure.support.view.GenericResult;
import org.lushen.mrh.boot.autoconfigure.webmvc.advice.ExceptionPlugin;

/**
 * {@link Throwable}
 * 
 * @author hlm
 */
public class ThrowablePlugin implements ExceptionPlugin {

	private final Log log = LogFactory.getLog(getClass().getSimpleName());

	@Override
	public int getOrder() {
		return LOWEST_PRECEDENCE;
	}

	@Override
	public boolean supports(Throwable delimiter) {
		return true;
	}

	@Override
	public GenericResult handle(Throwable cause) {
		log.error(cause.getMessage(), cause);
		GenericStatus status = GenericStatus.ERROR;
		return new GenericResult(status.getErrcode(), status.getErrmsg());
	}

}
