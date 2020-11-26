package org.lushen.mrh.boot.sequence.snowflake;

import static org.lushen.mrh.boot.sequence.snowflake.SnowflakeWorker.blockMoveState;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lushen.mrh.boot.sequence.SequenceGenerator;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * 雪花ID生成器
 * 
 * @author hlm
 */
public class SnowflakeGenerator implements SequenceGenerator, InitializingBean, DisposableBean {

	private final Log log = LogFactory.getLog(getClass());

	private final ExecutorService executor = Executors.newSingleThreadExecutor();

	private SnowflakeFactory factory;

	private SnowflakeWorker mainWorker;

	private SnowflakeWorker backWorker;

	public SnowflakeGenerator(SnowflakeFactory factory) {
		super();
		this.factory = factory;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		synchronized (this.executor) {
			if(this.mainWorker == null) {
				this.mainWorker = this.factory.build();
			}
			if(this.backWorker == null) {
				this.backWorker = this.factory.build();
			}
		}
	}

	@Override
	public synchronized void destroy() throws Exception {
		synchronized (this.executor) {
			if( ! this.executor.isShutdown() ) {
				this.executor.shutdownNow();
			}
		}
	}

	@Override
	public Long generate() {
		synchronized (this.executor) {
			Long sequence = this.mainWorker.generate();
			if(sequence > 0L) {
				return sequence;
			} else {
				if(sequence == blockMoveState) {
					log.warn("Block move back and try to failover.");
				}
				failover();
				return generate();
			}
		}
	}

	private void failover() {
		while(true) {
			synchronized (this.executor) {
				if(this.backWorker != null) {
					this.mainWorker = this.backWorker;
					this.backWorker = null;
				}
				else {
					while (true) {
						try {
							this.mainWorker = this.factory.build();
						} catch (Throwable ex) {
							if(this.executor.isTerminated()) {
								break;
							}
							log.warn("Apply main generator failure and try again.", ex);
						}
					}
				}
				this.executor.submit(() -> {
					synchronized (this.executor) {
						try {
							if(this.backWorker == null) {
								this.backWorker = this.factory.build();
							}
						} catch (Throwable ex) {
							log.warn("Apply backup generator failure.", ex);
						}
					}
				});
				break;
			}
		}
	}

}