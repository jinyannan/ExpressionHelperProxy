package gov.customs.rule.data;

// Generated Jul 22, 2015 10:19:43 AM by Hibernate Tools 3.4.0.CR1

/**
 * RuleEntityJar generated by hbm2java
 */
public class RuleEntityJar implements java.io.Serializable {

	private long id;
	private Long subSystemId;
	private String jarFullpath;
	private String jarName;
	private Boolean isShare;

	public RuleEntityJar() {
	}

	public RuleEntityJar(long id) {
		this.id = id;
	}

	public RuleEntityJar(long id, Long subSystemId, String jarFullpath,
			String jarName, Boolean isShare) {
		this.id = id;
		this.subSystemId = subSystemId;
		this.jarFullpath = jarFullpath;
		this.jarName = jarName;
		this.isShare = isShare;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getSubSystemId() {
		return this.subSystemId;
	}

	public void setSubSystemId(Long subSystemId) {
		this.subSystemId = subSystemId;
	}

	public String getJarFullpath() {
		return this.jarFullpath;
	}

	public void setJarFullpath(String jarFullpath) {
		this.jarFullpath = jarFullpath;
	}

	public String getJarName() {
		return this.jarName;
	}

	public void setJarName(String jarName) {
		this.jarName = jarName;
	}

	public Boolean getIsShare() {
		return this.isShare;
	}

	public void setIsShare(Boolean isShare) {
		this.isShare = isShare;
	}

}
