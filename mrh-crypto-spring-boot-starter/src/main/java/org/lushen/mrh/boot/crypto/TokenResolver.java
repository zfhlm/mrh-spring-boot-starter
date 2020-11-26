package org.lushen.mrh.boot.crypto;

/**
 * 令牌处理器
 * 
 * @author hlm
 */
public interface TokenResolver {

	/**
	 * 生成令牌
	 * 
	 * @param tokenObject
	 * @return
	 * @throws TokenException
	 */
	public <T extends TokenObject> String create(T tokenObject) throws TokenException;

	/**
	 * 解析令牌
	 * 
	 * @param clazz
	 * @param token
	 * @return
	 * @throws TokenException
	 */
	public <T extends TokenObject> T parse(Class<T> clazz, String token) throws TokenException;

}
