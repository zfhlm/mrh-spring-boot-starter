package org.lushen.mrh.boot.autoconfigure.support.asserts;

import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * 断言方法接口定义
 * 
 * @author hlm
 * @param <M> 构建异常参数类型
 * @param <E> 异常类型
 */
public interface AssertDefine<M, E extends Throwable> {

	/**
	 * 抛出业务异常
	 * 
	 * @param message
	 * @throws E 
	 */
	void error(M message) throws E;

	/**
	 * {@code expression==false} 抛出业务异常
	 * 
	 * @param expression
	 * @param message
	 * @throws E 
	 */
	void isTrue(boolean expression, M message) throws E;

	/**
	 * {@code expression==true} 抛出业务异常
	 * 
	 * @param expression
	 * @param message
	 * @throws E 
	 */
	void notTrue(boolean expression, M message) throws E;

	/**
	 * {@code arg!=null} 抛出业务异常
	 * 
	 * @param arg
	 * @param message
	 * @throws E 
	 */
	void isNull(Object arg, M message) throws E;

	/**
	 * {@code arg==null} 抛出业务异常
	 * 
	 * @param arg
	 * @param message
	 * @throws E 
	 */
	void notNull(Object arg, M message) throws E;

	/**
	 * {@code collection==null || collection.isEmpty()} 抛出业务异常
	 * 
	 * @param collection
	 * @param message
	 * @throws E 
	 */
	void notEmpty(Collection<?> collection, M message) throws E;

	/**
	 * {@code collection != null && ! collection.isEmpty()} 抛出业务异常
	 * 
	 * @param collection
	 * @param message
	 * @throws E 
	 */
	void isEmpty(Collection<?> collection, M message) throws E;

	/**
	 * {@code map==null || map.isEmpty()} 抛出业务异常
	 * 
	 * @param map
	 * @param message
	 * @throws E 
	 */
	void notEmpty(Map<?, ?> map, M message) throws E;

	/**
	 * {@code map != null && ! map.isEmpty()} 抛出业务异常
	 * 
	 * @param map
	 * @param message
	 * @throws E 
	 */
	void isEmpty(Map<?, ?> map, M message) throws E;

	/**
	 * {@code array==null || array.length==0} 抛出业务异常
	 * 
	 * @param array
	 * @param message
	 * @throws E 
	 */
	void notEmpty(Object[] array, M message) throws E;

	/**
	 * {@code array != null && array.length != 0} 抛出业务异常
	 * 
	 * @param array
	 * @param message
	 * @throws E 
	 */
	void isEmpty(Object[] array, M message) throws E;

	/**
	 * {@code org.apache.commons.lang3.StringUtils.isBlank(sequence)} 抛出业务异常
	 * 
	 * @param sequence
	 * @param message
	 * @throws E 
	 */
	void notBlank(CharSequence sequence, M message) throws E;

	/**
	 * {@code org.apache.commons.lang3.StringUtils.isNotBlank(sequence)} 抛出业务异常
	 * 
	 * @param sequence
	 * @param message
	 * @throws E 
	 */
	void isBlank(CharSequence sequence, M message) throws E;

	/**
	 * 字符串是否相等，不等抛出业务异常
	 * 
	 * @param seq
	 * @param other
	 * @param message
	 * @throws E
	 */
	void equals(CharSequence seq, CharSequence other, M message) throws E;

	/**
	 * 字符串是否不等，相等抛出业务异常
	 * 
	 * @param seq
	 * @param other
	 * @param message
	 * @throws E
	 */
	void notEquals(CharSequence seq, CharSequence other, M message) throws E;

	/**
	 * {@code sequence==null || sequence.length()==0} 抛出业务异常
	 * 
	 * @param sequence
	 * @param message
	 * @throws E 
	 */
	void hasLength(CharSequence sequence, M message) throws E;

	/**
	 * {@code sequence != null && sequence.length() != 0} 抛出业务异常
	 * 
	 * @param sequence
	 * @param message
	 * @throws E 
	 */
	void noLength(CharSequence sequence, M message) throws E;

	/**
	 * 正则表达式匹配失败 抛出业务异常
	 * 
	 * @param sequence
	 * @param regex
	 * @param message
	 * @throws E 
	 */
	void isMatched(CharSequence sequence, String regex, M message) throws E;

	/**
	 * 正则表达式匹配失败 抛出业务异常
	 * 
	 * @param sequence
	 * @param pattern
	 * @param message
	 * @throws E 
	 */
	void isMatched(CharSequence sequence, Pattern pattern, M message) throws E;

	/**
	 * {@code sequence==null || ! sequence.startsWith(prefix)} 抛出业务异常
	 * 
	 * @param sequence
	 * @param prefix
	 * @param message
	 * @throws E 
	 */
	void startWith(String sequence, String prefix, M message) throws E;

	/**
	 * {@code sequence==null || ! sequence.endWith(prefix)} 抛出业务异常
	 * 
	 * @param sequence
	 * @param subfix
	 * @param message
	 * @throws E 
	 */
	void endWith(String sequence, String subfix, M message) throws E;

	/**
	 * {@code (number > thanNumber) == false } 抛出业务异常
	 * 
	 * @param number
	 * @param thanNumber
	 * @param message
	 * @throws E 
	 */
	void largeThan(long number, long thanNumber, M message) throws E;

	/**
	 * {@code (number >= thanNumber) == false } 抛出业务异常
	 * 
	 * @param number
	 * @param thanNumber
	 * @param message
	 * @throws E 
	 */
	void largeThanOrEqual(long number, long thanNumber, M message) throws E;

	/**
	 * {@code (number < thanNumber) == false } 抛出业务异常
	 * 
	 * @param number
	 * @param thanNumber
	 * @param message
	 * @throws E 
	 */
	void lessThan(long number, long thanNumber, M message) throws E;

	/**
	 * {@code (number <= thanNumber) == false } 抛出业务异常
	 * 
	 * @param number
	 * @param thanNumber
	 * @param message
	 * @throws E 
	 */
	void lessThanOrEqual(long number, long thanNumber, M message) throws E;

	/**
	 * {@code number != thanNumber } 抛出业务异常
	 * 
	 * @param number
	 * @param thanNumber
	 * @param message
	 * @throws E
	 */
	void equal(long number, long thanNumber, M message) throws E;

	/**
	 * {@code number == thanNumber } 抛出业务异常
	 * 
	 * @param number
	 * @param thanNumber
	 * @param message
	 * @throws E
	 */
	void notEqual(long number, long thanNumber, M message) throws E;

	/**
	 * {@code number != 0 } 抛出业务异常
	 * 
	 * @param number
	 * @param message
	 * @throws E
	 */
	default void equalZero(long number, M message) throws E {
		equal(number, 0, message);
	}

	/**
	 * {@code number == 0 } 抛出业务异常
	 * 
	 * @param number
	 * @param message
	 * @throws E
	 */
	default void notEqualZero(long number, M message) throws E {
		notEqual(number, 0, message);
	}

	/**
	 * 抛出业务异常
	 * 
	 * @param supplier
	 * @throws E 
	 */
	default void error(Supplier<E> supplier) throws E {
		if(supplier == null) {
			throw new IllegalArgumentException("supplier is null.");
		}
		throw supplier.get();
	}

	/**
	 * {@code expression==false} 抛出业务异常
	 * 
	 * @param expression
	 * @param supplier
	 * @throws E 
	 */
	default void isTrue(boolean expression, Supplier<E> supplier) throws E {
		if( ! expression) {
			error(supplier);
		}
	}

	/**
	 * {@code expression==true} 抛出业务异常
	 * 
	 * @param expression
	 * @param supplier
	 * @throws E 
	 */
	default void notTrue(boolean expression, Supplier<E> supplier) throws E {
		if(expression) {
			error(supplier);
		}
	}

	/**
	 * {@code arg!=null} 抛出业务异常
	 * 
	 * @param arg
	 * @param supplier
	 * @throws E 
	 */
	default void isNull(Object arg, Supplier<E> supplier) throws E {
		if(arg != null) {
			error(supplier);
		}
	}

	/**
	 * {@code arg==null} 抛出业务异常
	 * 
	 * @param arg
	 * @param supplier
	 * @throws E 
	 */
	default void notNull(Object arg, Supplier<E> supplier) throws E {
		if(arg == null) {
			error(supplier);
		}
	}

	/**
	 * {@code collection==null || collection.isEmpty()} 抛出业务异常
	 * 
	 * @param collection
	 * @param supplier
	 * @throws E 
	 */
	default void notEmpty(Collection<?> collection, Supplier<E> supplier) throws E {
		if(collection == null || collection.isEmpty()) {
			error(supplier);
		}
	}

	/**
	 * {@code collection != null && ! collection.isEmpty()} 抛出业务异常
	 * 
	 * @param collection
	 * @param supplier
	 * @throws E 
	 */
	default void isEmpty(Collection<?> collection, Supplier<E> supplier) throws E {
		if(collection != null && ! collection.isEmpty()) {
			error(supplier);
		}
	}

	/**
	 * {@code map==null || map.isEmpty()} 抛出业务异常
	 * 
	 * @param map
	 * @param supplier
	 * @throws E 
	 */
	default void notEmpty(Map<?, ?> map, Supplier<E> supplier) throws E {
		if(map == null || map.isEmpty()) {
			error(supplier);
		}
	}

	/**
	 * {@code map != null && ! map.isEmpty()} 抛出业务异常
	 * 
	 * @param map
	 * @param supplier
	 * @throws E 
	 */
	default void isEmpty(Map<?, ?> map, Supplier<E> supplier) throws E {
		if(map != null && ! map.isEmpty()) {
			error(supplier);
		}
	}

	/**
	 * {@code array==null || array.length==0} 抛出业务异常
	 * 
	 * @param array
	 * @param supplier
	 * @throws E 
	 */
	default void notEmpty(Object[] array, Supplier<E> supplier) throws E {
		if(array == null || array.length == 0) {
			error(supplier);
		}
	}

	/**
	 * {@code array != null && array.length != 0} 抛出业务异常
	 * 
	 * @param array
	 * @param supplier
	 * @throws E 
	 */
	default void isEmpty(Object[] array, Supplier<E> supplier) throws E {
		if(array != null && array.length != 0) {
			error(supplier);
		}
	}

	/**
	 * {@code org.apache.commons.lang3.StringUtils.isBlank(sequence)} 抛出业务异常
	 * 
	 * @param sequence
	 * @param supplier
	 * @throws E 
	 */
	default void notBlank(CharSequence sequence, Supplier<E> supplier) throws E {
		if(StringUtils.isBlank(sequence)) {
			error(supplier);
		}
	}

	/**
	 * {@code org.apache.commons.lang3.StringUtils.isNotBlank(sequence)} 抛出业务异常
	 * 
	 * @param sequence
	 * @param supplier
	 * @throws E 
	 */
	default void isBlank(CharSequence sequence, Supplier<E> supplier) throws E {
		if( ! StringUtils.isBlank(sequence)) {
			error(supplier);
		}
	}

	/**
	 * 字符串是否相等，不等抛出业务异常
	 * 
	 * @param seq
	 * @param other
	 * @param supplier
	 * @throws E
	 */
	default void equals(CharSequence seq, CharSequence other, Supplier<E> supplier) throws E {
		if( ! StringUtils.equals(seq, other)) {
			error(supplier);
		}
	}

	/**
	 * 字符串是否不等，相等抛出业务异常
	 * 
	 * @param seq
	 * @param other
	 * @param supplier
	 * @throws E
	 */
	default void notEquals(CharSequence seq, CharSequence other, Supplier<E> supplier) throws E {
		if(StringUtils.equals(seq, other)) {
			error(supplier);
		}
	}

	/**
	 * {@code sequence==null || sequence.length()==0} 抛出业务异常
	 * 
	 * @param sequence
	 * @param supplier
	 * @throws E 
	 */
	default void hasLength(CharSequence sequence, Supplier<E> supplier) throws E {
		if(sequence == null || sequence.length() == 0) {
			error(supplier);
		}
	}

	/**
	 * {@code sequence != null && sequence.length() != 0} 抛出业务异常
	 * 
	 * @param sequence
	 * @param supplier
	 * @throws E 
	 */
	default void noLength(CharSequence sequence, Supplier<E> supplier) throws E {
		if(sequence != null && sequence.length() != 0) {
			error(supplier);
		}
	}

	/**
	 * 正则表达式匹配失败 抛出业务异常
	 * 
	 * @param sequence
	 * @param regex
	 * @param supplier
	 * @throws E 
	 */
	default void isMatched(CharSequence sequence, String regex, Supplier<E> supplier) throws E {
		if(sequence == null || regex == null || ! Pattern.compile(regex).matcher(sequence).matches()) {
			error(supplier);
		}
	}

	/**
	 * 正则表达式匹配失败 抛出业务异常
	 * 
	 * @param sequence
	 * @param pattern
	 * @param supplier
	 * @throws E 
	 */
	default void isMatched(CharSequence sequence, Pattern pattern, Supplier<E> supplier) throws E {
		if(sequence == null || pattern == null || ! pattern.matcher(sequence).matches()) {
			error(supplier);
		}
	}

	/**
	 * {@code sequence==null || ! sequence.startsWith(prefix)} 抛出业务异常
	 * 
	 * @param sequence
	 * @param prefix
	 * @param supplier
	 * @throws E 
	 */
	default void startWith(String sequence, String prefix, Supplier<E> supplier) throws E {
		if(sequence == null || prefix == null || ! sequence.startsWith(prefix)) {
			error(supplier);
		}
	}

	/**
	 * {@code sequence==null || ! sequence.endWith(prefix)} 抛出业务异常
	 * 
	 * @param sequence
	 * @param subfix
	 * @param supplier
	 * @throws E 
	 */
	default void endWith(String sequence, String subfix, Supplier<E> supplier) throws E {
		if(sequence == null || subfix == null || ! sequence.endsWith(subfix)) {
			error(supplier);
		}
	}

	/**
	 * {@code (number > thanNumber) == false } 抛出业务异常
	 * 
	 * @param number
	 * @param thanNumber
	 * @param supplier
	 * @throws E 
	 */
	default void largeThan(long number, long thanNumber, Supplier<E> supplier) throws E {
		if(number <= thanNumber) {
			error(supplier);
		}
	}

	/**
	 * {@code (number >= thanNumber) == false } 抛出业务异常
	 * 
	 * @param number
	 * @param thanNumber
	 * @param supplier
	 * @throws E 
	 */
	default void largeThanOrEqual(long number, long thanNumber, Supplier<E> supplier) throws E {
		if(number < thanNumber) {
			error(supplier);
		}
	}

	/**
	 * {@code (number < thanNumber) == false } 抛出业务异常
	 * 
	 * @param number
	 * @param thanNumber
	 * @param supplier
	 * @throws E 
	 */
	default void lessThan(long number, long thanNumber, Supplier<E> supplier) throws E {
		if(number >= thanNumber) {
			error(supplier);
		}
	}

	/**
	 * {@code (number <= thanNumber) == false } 抛出业务异常
	 * 
	 * @param number
	 * @param thanNumber
	 * @param supplier
	 * @throws E 
	 */
	default void lessThanOrEqual(long number, long thanNumber, Supplier<E> supplier) throws E {
		if(number > thanNumber) {
			error(supplier);
		}
	}

	/**
	 * {@code number != thanNumber } 抛出业务异常
	 * 
	 * @param number
	 * @param thanNumber
	 * @param supplier
	 * @throws E
	 */
	default void equal(long number, long thanNumber, Supplier<E> supplier) throws E {
		if(number != thanNumber) {
			error(supplier);
		}
	}

	/**
	 * {@code number == thanNumber } 抛出业务异常
	 * 
	 * @param number
	 * @param thanNumber
	 * @param supplier
	 * @throws E
	 */
	default void notEqual(long number, long thanNumber, Supplier<E> supplier) throws E {
		if(number == thanNumber) {
			error(supplier);
		}
	}

	/**
	 * {@code number != 0 } 抛出业务异常
	 * 
	 * @param number
	 * @param supplier
	 * @throws E
	 */
	default void equalZero(long number, Supplier<E> supplier) throws E {
		equal(number, 0, supplier);
	}

	/**
	 * {@code number == 0 } 抛出业务异常
	 * 
	 * @param number
	 * @param supplier
	 * @throws E
	 */
	default void notEqualZero(long number, Supplier<E> supplier) throws E {
		notEqual(number, 0, supplier);
	}

}
