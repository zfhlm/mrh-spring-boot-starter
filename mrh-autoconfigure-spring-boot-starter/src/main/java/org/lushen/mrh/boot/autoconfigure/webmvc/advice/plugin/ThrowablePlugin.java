package org.lushen.mrh.boot.autoconfigure.webmvc.advice.plugin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lushen.mrh.boot.autoconfigure.webmvc.advice.ExceptionPlugin;
import org.lushen.mrh.support.generic.status.GenericStatus;
import org.lushen.mrh.support.generic.view.GenericResult;

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
