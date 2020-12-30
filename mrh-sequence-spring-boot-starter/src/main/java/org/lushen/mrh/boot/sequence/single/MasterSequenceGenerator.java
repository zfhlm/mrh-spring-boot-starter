package org.lushen.mrh.boot.sequence.single;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lushen.mrh.boot.sequence.SequenceGenerator;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * 主备切换 序列ID生成器
 * 
 * @author hlm
 */
public abstract class MasterSequenceGenerator implements SequenceGenerator, InitializingBean, DisposableBean {

	protected final Log log = LogFactory.getLog(getClass());

	private final ExecutorService executor = Executors.newSingleThreadExecutor();

	private SequenceGenerator master;

	private SequenceGenerator slave;

	@Override
	public synchronized void afterPropertiesSet() throws Exception {
		if(this.master == null) {
			this.master = apply();
		}
		if(this.slave == null) {
			this.slave = apply();
		}
	}

	@Override
	public synchronized void destroy() throws Exception {
		if( ! this.executor.isTerminated() ) {
			this.executor.shutdownNow();
		}
	}

	@Override
	public Long generate() {
		synchronized (this.executor) {
			while (true) {
				try {
					return this.master.generate();
				} catch (Exception e) {
					if(this.slave != null) {
						this.master = this.slave;
						this.slave = null;
						log.info("Change slave to master, cause by : " + e.getMessage());
					}
					else {
						while (true) {
							try {
								this.master = apply();
								log.info("Apply master success, cause by : " + e.getMessage());
								break;
							} catch (Throwable ex) {
								if(this.executor.isTerminated()) {
									break;
								}
								log.warn("Apply master failure, try again.", ex);
							}
						}
					}
					this.executor.submit(() -> {
						synchronized (this.executor) {
							try {
								if(this.slave == null) {
									this.slave = apply();
									log.info("Apply slave success, cause by : " + e.getMessage());
								}
							} catch (Throwable ex) {
								log.warn("Apply slave failure.", ex);
							}
						}
					});
				}
			}
		}
	}

	/**
	 * 创建序列ID生成器实例
	 * 
	 * @return
	 * @throws Exception 
	 */
	protected abstract SequenceGenerator apply() throws Exception;

}
