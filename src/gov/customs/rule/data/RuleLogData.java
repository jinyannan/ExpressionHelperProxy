package gov.customs.rule.data;

import java.math.BigDecimal;
import java.util.Date;

public class RuleLogData {
// 目前日志包括如下内容：
//id
//规则名称
//规则类型
//起始时间
//结束时间
//执行时间
//是否命中
//评估标志
	
	private BigDecimal ruleId;
	private String ruleName;
	private String ruleCond;
	private String ruleType;
	private Boolean isEstimate;
	private Boolean isLog;

	public RuleLogData() {
		super();
	}

	public RuleLogData(BigDecimal ruleId, String ruleName, String ruleCond,
			String ruleType, Boolean isEstimate, Boolean isLog) {
		super();
		this.ruleId = ruleId;
		this.ruleName = ruleName;
		this.ruleCond = ruleCond;
		this.ruleType = ruleType;
		this.isEstimate = isEstimate;
		this.isLog = isLog;
	}
	
	public BigDecimal getRuleId() {
		return ruleId;
	}
	public void setRuleId(BigDecimal ruleId) {
		this.ruleId = ruleId;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public String getRuleType() {
		return ruleType;
	}
	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}
	public Boolean getIsEstimate() {
		return isEstimate;
	}
	public void setIsEstimate(Boolean isEstimate) {
		this.isEstimate = isEstimate;
	}
	
	public String getRuleCond() {
		return ruleCond;
	}
	public void setRuleCond(String ruleCond) {
		this.ruleCond = ruleCond;
	}

	public Boolean getIsLog() {
		return isLog;
	}

	public void setIsLog(Boolean isLog) {
		this.isLog = isLog;
	}

}
