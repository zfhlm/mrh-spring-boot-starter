package org.lushen.mrh.boot.sequence.snowflake;

import static org.lushen.mrh.boot.sequence.single.SnowflakeSequenceGenerator.maxCenterId;
import static org.lushen.mrh.boot.sequence.single.SnowflakeSequenceGenerator.maxWorkerId;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

import org.lushen.mrh.boot.sequence.SequenceGenerator;
import org.lushen.mrh.boot.sequence.registry.SequenceInstance;
import org.lushen.mrh.boot.sequence.registry.SequenceInstanceRepository;
import org.lushen.mrh.boot.sequence.single.MasterSequenceGenerator;
import org.lushen.mrh.boot.sequence.single.SnowflakeSequenceGenerator;

/**
 * 雪花ID生成器，基于注册中心解决时钟回拨问题
 * 
 * @author hlm
 */
public class SnowflakeRegistryGenerator extends MasterSequenceGenerator {

	private long liveTimeMillis;

	private SequenceInstanceRepository<SnowflakeInstancePayload> instanceRepository;

	public SnowflakeRegistryGenerator(long liveTimeMillis, SequenceInstanceRepository<SnowflakeInstancePayload> instanceRepository) {
		super();
		this.liveTimeMillis = liveTimeMillis;
		this.instanceRepository = instanceRepository;
	}

	@Override
	protected SequenceGenerator apply() throws Exception {

		for(int seed : IntStream.range(0, (int)((maxCenterId+1)*(maxWorkerId+1))).toArray()) {

			long current = System.currentTimeMillis();
			int centerId = (int)(seed/maxCenterId);
			int workerId = (int)(seed%maxWorkerId);
			String id = id(centerId, workerId);

			// 查询节点
			SequenceInstance<SnowflakeInstancePayload> instance = this.instanceRepository.findById(id);

			if(instance == null || instance.getPayload().getExpiredAt() < current) {

				// 负载信息
				SnowflakeInstancePayload payload = new SnowflakeInstancePayload();
				payload.setCenterId(centerId);
				payload.setWorkerId(workerId);
				payload.setEffectAt(current);
				payload.setExpiredAt(current + this.liveTimeMillis);

				// 实例信息
				SequenceInstance<SnowflakeInstancePayload> newInstance = new SequenceInstance<SnowflakeInstancePayload>();
				newInstance.setId(id);
				newInstance.setName(id);
				newInstance.setPayload(payload);
				newInstance.setVersion(Optional.ofNullable(instance).map(SequenceInstance::getVersion).filter(Objects::nonNull).orElse(1));

				// 注册或更新节点
				if(instance == null) {
					log.info("Register snowflake instance : " + newInstance);
					this.instanceRepository.save(newInstance);
				} else {
					log.info("Refresh snowflake instance : " + newInstance);
					this.instanceRepository.update(newInstance);
				}

				return new SnowflakeSequenceGenerator(centerId, workerId, payload.getEffectAt(), payload.getExpiredAt());

			}

		}

		throw new RuntimeException("Unable to find available payload !");
	}

	private String id(int centerId, int workerId) {
		return new StringBuilder().append(centerId).append('-').append(workerId).toString();
	}

}