package org.lushen.mrh.boot.autoconfigure.webmvc.advice.plugin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lushen.mrh.boot.autoconfigure.support.error.GenericBizException;
import org.lushen.mrh.boot.autoconfigure.support.error.GenericException;
import org.lushen.mrh.boot.autoconfigure.support.error.GenericPayloadException;
import org.lushen.mrh.boot.autoconfigure.support.error.GenericStatus;
import org.lushen.mrh.boot.autoconfigure.support.view.GenericResult;
import org.lushen.mrh.boot.autoconfigure.webmvc.advice.ExceptionPlugin;

/**
 * {@link GenericException}
 * 
 * @author hlm
 */
public class GenericExceptionPlugin implements ExceptionPlugin {

	private final Log log = LogFactory.getLog(getClass().getSimpleName());

	@Override
	public int getOrder() {
		return HIGHEST_PRECEDENCE + 1000;
	}

	@Override
	public boolean supports(Throwable cause) {
		return cause instanceof GenericException;
	}

	@Override
	public GenericResult handle(Throwable cause) {
		if(cause instanceof GenericBizException) {
			StringBuilder message = new StringBuilder();
			message.append("Generic biz message: ");
			message.append(cause.getMessage());
			message.append(", status: ");
			message.append(((GenericBizException)cause).getStatus());
			message.append(", payload: ");
			message.append(((GenericBizException)cause).getPayload());
			log.error(message.toString());
		}
		else if(cause instanceof GenericPayloadException) {
			StringBuilder message = new StringBuilder();
			message.append("Generic payload message: ");
			message.append(cause.getMessage());
			message.append(", status: ");
			message.append(((GenericBizException)cause).getStatus());
			message.append(", payload: ");
			message.append(((GenericBizException)cause).getPayload());
			log.error(message.toString(), cause);
		}
		else {
			StringBuilder message = new StringBuilder();
			message.append("Generic message: ");
			message.append(cause.getMessage());
			message.append(", status: ");
			message.append(((GenericException)cause).getStatus());
			log.error(message.toString(), cause);
		}
		GenericStatus status = ((GenericException)cause).getStatus();
		return new GenericResult(status.getErrcode(), status.getErrmsg());
	}

}
