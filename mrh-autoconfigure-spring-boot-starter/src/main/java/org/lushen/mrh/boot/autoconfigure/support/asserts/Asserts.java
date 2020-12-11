package org.lushen.mrh.boot.autoconfigure.support.asserts;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * 断言工具类
 * 
 * @author hlm
 */
public class Asserts<M, T extends Throwable> implements AssertDefine<M, T> {

	public static final Asserts<String, Throwable> INSTANCE = new Asserts<String, Throwable>(IllegalArgumentException::new);

	private Function<M, T> errorParser;

	public Asserts(Function<M, T> errorParser) {
		super();
		if(errorParser == null) {
			throw new IllegalArgumentException("errorParser is null.");
		}
		this.errorParser = errorParser;
	}

	@Override
	public void error(M message) throws T {
		throw errorParser.apply(message);
	}

	@Override
	public void isTrue(boolean expression, M message) throws T {
		isTrue(expression, () -> errorParser.apply(message));
	}

	@Override
	public void notTrue(boolean expression, M message) throws T {
		notTrue(expression, () -> errorParser.apply(message));
	}

	@Override
	public void isNull(Object arg, M message) throws T {
		isNull(arg, () -> errorParser.apply(message));
	}

	@Override
	public void notNull(Object arg, M message) throws T {
		notNull(arg, () -> errorParser.apply(message));
	}

	@Override
	public void notEmpty(Collection<?> collection, M message) throws T {
		notEmpty(collection, () -> errorParser.apply(message));

	}

	@Override
	public void isEmpty(Collection<?> collection, M message) throws T {
		isEmpty(collection, () -> errorParser.apply(message));
	}

	@Override
	public void notEmpty(Map<?, ?> map, M message) throws T {
		notEmpty(map, () -> errorParser.apply(message));
	}

	@Override
	public void isEmpty(Map<?, ?> map, M message) throws T {
		isEmpty(map, () -> errorParser.apply(message));
	}

	@Override
	public void notEmpty(Object[] array, M message) throws T {
		notEmpty(array, () -> errorParser.apply(message));
	}

	@Override
	public void isEmpty(Object[] array, M message) throws T {
		isEmpty(array, () -> errorParser.apply(message));
	}

	@Override
	public void notBlank(CharSequence sequence, M message) throws T {
		notBlank(sequence, () -> errorParser.apply(message));
	}

	@Override
	public void isBlank(CharSequence sequence, M message) throws T {
		isBlank(sequence, () -> errorParser.apply(message));
	}

	@Override
	public void hasLength(CharSequence sequence, M message) throws T {
		hasLength(sequence, () -> errorParser.apply(message));
	}

	@Override
	public void noLength(CharSequence sequence, M message) throws T {
		noLength(sequence, () -> errorParser.apply(message));
	}

	@Override
	public void isMatched(CharSequence sequence, String regex, M message) throws T {
		isMatched(sequence, regex, () -> errorParser.apply(message));
	}

	@Override
	public void isMatched(CharSequence sequence, Pattern pattern, M message) throws T {
		isMatched(sequence, pattern, () -> errorParser.apply(message));
	}

	@Override
	public void startWith(String sequence, String prefix, M message) throws T {
		startWith(sequence, prefix, () -> errorParser.apply(message));
	}

	@Override
	public void endWith(String sequence, String subfix, M message) throws T {
		endWith(sequence, subfix, () -> errorParser.apply(message));
	}

	@Override
	public void equals(CharSequence seq, CharSequence other, M message) throws T {
		equals(seq, other, () -> errorParser.apply(message));
	}

	@Override
	public void notEquals(CharSequence seq, CharSequence other, M message) throws T {
		notEquals(seq, other, () -> errorParser.apply(message));
	}

	@Override
	public void largeThan(long number, long thanNumber, M message) throws T {
		largeThan(number, thanNumber, () -> errorParser.apply(message));
	}

	@Override
	public void largeThanOrEqual(long number, long thanNumber, M message) throws T {
		largeThanOrEqual(number, thanNumber, () -> errorParser.apply(message));
	}

	@Override
	public void lessThan(long number, long thanNumber, M message) throws T {
		lessThan(number, thanNumber, () -> errorParser.apply(message));
	}

	@Override
	public void lessThanOrEqual(long number, long thanNumber, M message) throws T {
		lessThanOrEqual(number, thanNumber, () -> errorParser.apply(message));
	}

	@Override
	public void equal(long number, long thanNumber, M message) throws T {
		equal(number, thanNumber, () -> errorParser.apply(message));
	}

	@Override
	public void notEqual(long number, long thanNumber, M message) throws T {
		notEqual(number, thanNumber, () -> errorParser.apply(message));
	}

}
