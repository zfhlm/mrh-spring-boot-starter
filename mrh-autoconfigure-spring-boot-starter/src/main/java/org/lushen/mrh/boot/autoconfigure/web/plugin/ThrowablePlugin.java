package org.lushen.mrh.boot.autoconfigure.web.plugin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lushen.mrh.boot.autoconfigure.web.ErrorHandlerPlugin;
import org.lushen.mrh.support.generic.status.GenericStatus;
import org.lushen.mrh.support.generic.view.GenericResult;
import org.springframework.core.Ordered;

/**
 * {@link Throwable}
 * 
 * @author hlm
 */
public class ThrowablePlugin implements ErrorHandlerPlugin, Ordered {

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
