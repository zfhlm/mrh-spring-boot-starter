package org.lushen.mrh.boot.autoconfigure.web.plugin;

import static org.lushen.mrh.support.generic.status.GenericStatus.BIND_PARAM_ERROR;
import static org.lushen.mrh.support.generic.status.GenericStatus.VALID_PARAM_ERROR;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lushen.mrh.boot.autoconfigure.web.ErrorHandlerPlugin;
import org.lushen.mrh.support.generic.view.GenericResult;
import org.lushen.mrh.support.generic.view.ValidationResult;
import org.lushen.mrh.support.generic.view.ValidationResult.Message;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;

/**
 * {@link BindException}
 * 
 * @author hlm
 */
public class BindExceptionPlugin implements ErrorHandlerPlugin {

	private final Log log = LogFactory.getLog(getClass().getSimpleName());

	@Override
	public int getOrder() {
		return HIGHEST_PRECEDENCE + 999;
	}

	@Override
	public boolean supports(Throwable cause) {
		return cause instanceof BindException;
	}

	@Override
	public GenericResult handle(Throwable cause) {
		log.error(cause.getMessage());
		List<Message> messages = ((BindException)cause).getFieldErrors().stream()
				.map(this::toMessage).sorted(Comparator.comparing(Message::getName)).collect(Collectors.toList());
		String errmsg = messages.stream().findFirst().map(Message::getMessage).orElse(VALID_PARAM_ERROR.getErrmsg());
		return new ValidationResult(VALID_PARAM_ERROR.getErrcode(), errmsg, messages);
	}

	private Message toMessage(FieldError fieldError) {
		String message = fieldError.isBindingFailure()? BIND_PARAM_ERROR.getErrmsg() : fieldError.getDefaultMessage();
		return new Message(fieldError.getField(), message);
	}

}
