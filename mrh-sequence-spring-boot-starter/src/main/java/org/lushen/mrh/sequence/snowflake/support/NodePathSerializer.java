package org.lushen.mrh.sequence.snowflake.support;

/**
 * 注册路径序列化工具
 * 
 * @author hlm
 */
public class NodePathSerializer implements SnowflakeSerializer<NodePathSerializer.Node, String> {

	@Override
	public String serialize(Node payload) {
		try {
			StringBuilder builder = new StringBuilder();
			if(payload.getCenterId() < 10) {
				builder.append('0');
			}
			builder.append(payload.getCenterId());
			builder.append('-');
			if(payload.getWorkerId() < 10) {
				builder.append('0');
			}
			builder.append(payload.getWorkerId());
			return builder.toString();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public Node deserialize(String buffer) {
		try {
			int index = buffer.indexOf('-');
			Node node = new Node();
			node.setCenterId(Integer.parseInt(buffer.substring(0, index)));
			node.setWorkerId(Integer.parseInt(buffer.substring(index+1)));
			return node;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static class Node {

		private int centerId;

		private int workerId;

		public Node() {
			super();
		}

		public Node(int centerId, int workerId) {
			super();
			this.centerId = centerId;
			this.workerId = workerId;
		}

		public int getCenterId() {
			return centerId;
		}

		public void setCenterId(int centerId) {
			this.centerId = centerId;
		}

		public int getWorkerId() {
			return workerId;
		}

		public void setWorkerId(int workerId) {
			this.workerId = workerId;
		}

	}

}
