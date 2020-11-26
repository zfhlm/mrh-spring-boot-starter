package org.lushen.mrh.boot.jpa.where;

import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;

/**
 * jpa where like/not-like 方法定义接口
 * 
 * @author hlm
 * @param <E>
 * @param <X>
 */
@SuppressWarnings("unchecked")
public interface JpaLikeOrNotExpression<E, X extends JpaLikeOrNotExpression<E, X>> {

	/**
	 * 左模糊条件匹配
	 * 
	 * @param function
	 * @param value
	 * @return
	 */
	X leftLike(Function<E, ?> function, String value);

	/**
	 * 右模糊条件匹配
	 * 
	 * @param function
	 * @param value
	 * @return
	 */
	X rightLike(Function<E, ?> function, String value);

	/**
	 * 左右模糊条件匹配
	 * 
	 * @param function
	 * @param value
	 * @return
	 */
	X fullLike(Function<E, ?> function, String value);

	/**
	 * 左模糊条件不匹配
	 * 
	 * @param function
	 * @param value
	 * @return
	 */
	X notLeftLike(Function<E, ?> function, String value);

	/**
	 * 右模糊条件不匹配
	 * 
	 * @param function
	 * @param value
	 * @return
	 */
	X notRightLike(Function<E, ?> function, String value);

	/**
	 * 左右模糊条件不匹配
	 * 
	 * @param function
	 * @param value
	 * @return
	 */
	X notFullLike(Function<E, ?> function, String value);

	/**
	 * 左模糊条件匹配，value为null不生效
	 * 
	 * @param function
	 * @param value
	 * @return
	 */
	default X leftLikeIgnoreNull(Function<E, ?> function, String value) {
		if(value != null) {
			leftLike(function, value);
		}
		return (X) this;
	}

	/**
	 * 右模糊条件匹配，value为null不生效
	 * 
	 * @param function
	 * @param value
	 * @return
	 */
	default X rightLikeIgnoreNull(Function<E, ?> function, String value) {
		if(value != null) {
			rightLike(function, value);
		}
		return (X) this;
	}

	/**
	 * 左右模糊条件匹配，value为null不生效
	 * 
	 * @param function
	 * @param value
	 * @return
	 */
	default X fullLikeIgnoreNull(Function<E, ?> function, String value) {
		if(value != null) {
			fullLike(function, value);
		}
		return (X) this;
	}

	/**
	 * 左模糊条件不匹配，value为null不生效
	 * 
	 * @param function
	 * @param value
	 * @return
	 */
	default X notLeftLikeIgnoreNull(Function<E, ?> function, String value) {
		if(value != null) {
			notLeftLike(function, value);
		}
		return (X) this;
	}

	/**
	 * 右模糊条件不匹配，value为null不生效
	 * 
	 * @param function
	 * @param value
	 * @return
	 */
	default X notRightLikeIgnoreNull(Function<E, ?> function, String value) {
		if(value != null) {
			notRightLike(function, value);
		}
		return (X) this;
	}

	/**
	 * 左右模糊条件不匹配，value为null不生效
	 * 
	 * @param function
	 * @param value
	 * @return
	 */
	default X notFullLikeIgnoreNull(Function<E, ?> function, String value) {
		if(value != null) {
			notFullLike(function, value);
		}
		return (X) this;
	}

	/**
	 * 左模糊条件匹配，value为空字符串不生效
	 * 
	 * @param function
	 * @param value
	 * @return
	 */
	default X leftLikeIgnoreBlank(Function<E, ?> function, String value) {
		if(StringUtils.isNotBlank(value)) {
			leftLike(function, value);
		}
		return (X) this;
	}

	/**
	 * 右模糊条件匹配，value为空字符串不生效
	 * 
	 * @param function
	 * @param value
	 * @return
	 */
	default X rightLikeIgnoreBlank(Function<E, ?> function, String value) {
		if(StringUtils.isNotBlank(value)) {
			rightLike(function, value);
		}
		return (X) this;
	}

	/**
	 * 左右模糊条件匹配，value为空字符串不生效
	 * 
	 * @param function
	 * @param value
	 * @return
	 */
	default X fullLikeIgnoreBlank(Function<E, ?> function, String value) {
		if(StringUtils.isNotBlank(value)) {
			fullLike(function, value);
		}
		return (X) this;
	}

	/**
	 * 左模糊条件不匹配，value为空字符串不生效
	 * 
	 * @param function
	 * @param value
	 * @return
	 */
	default X notLeftLikeIgnoreBlank(Function<E, ?> function, String value) {
		if(StringUtils.isNotBlank(value)) {
			notLeftLike(function, value);
		}
		return (X) this;
	}

	/**
	 * 右模糊条件不匹配，value为空字符串不生效
	 * 
	 * @param function
	 * @param value
	 * @return
	 */
	default X notRightLikeIgnoreBlank(Function<E, ?> function, String value) {
		if(StringUtils.isNotBlank(value)) {
			notRightLike(function, value);
		}
		return (X) this;
	}

	/**
	 * 左右模糊条件不匹配，value为空字符串不生效
	 * 
	 * @param function
	 * @param value
	 * @return
	 */
	default X notFullLikeIgnoreBlank(Function<E, ?> function, String value) {
		if(StringUtils.isNotBlank(value)) {
			notFullLike(function, value);
		}
		return (X) this;
	}

}
