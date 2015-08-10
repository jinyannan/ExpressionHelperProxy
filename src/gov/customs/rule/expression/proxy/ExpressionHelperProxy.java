package gov.customs.rule.expression.proxy;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import javassist.expr.NewArray;

import org.antlr.ext.ConditionExpression.*;
import org.antlr.ext.ConditionExpression.Utility.GetTypeUtility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.ThreadContext;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.internal.util.type.PrimitiveWrapperHelper.LongDescriptor;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.hibernate.transform.Transformers;
import org.jboss.logging.Logger;

import gov.customs.rule.data.*;

public class ExpressionHelperProxy {

	private static final org.apache.logging.log4j.Logger logger = LogManager
			.getLogger("gov.customs.rule.expression.proxy");

	public enum LogType {
		Error, Trace;
	}

	public Object ExecuteExpression(RuleLogData logData, Object data) {
		HashMap<String, Object> hmlocal = new HashMap<String, Object>();
		return ExecuteExpression(logData, data, (Object) hmlocal);
	}

	/**
	 * 这个方法可以记录日志，但必须将日志需要的信息传递进来，推荐使用 id 规则名称 规则类型 起始时间 结束时间 执行时间 是否命中 评估标志
	 * 
	 * @param logData
	 * @param data
	 * @return
	 */
	public Object ExecuteExpression(RuleLogData logData, Object data,
			Object local) {
		Object result;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd H:m:s");
		long beginTime = new Date().getTime();
		result = new Expression().ExecuteExpression(logData.getRuleCond(),
				data, local);
		long endTime = new Date().getTime();
		if (logData.getIsLog()) {
			String message = String.format("%d;%s;%s;%s;%d;%s;%s", logData
					.getRuleId().intValue(), logData.getRuleName(), format
					.format(beginTime), format.format(endTime), endTime
					- beginTime, String.valueOf(result), String.valueOf(logData
					.getIsEstimate()));
			printLog(message, LogType.Trace);
		}

		return result;
	}

	public Object ExecuteExpression(BigDecimal ruleId, Object data) {
		HashMap<String, Object> hmlocal = new HashMap<String, Object>();
		return ExecuteExpression(ruleId, data, (Object) hmlocal);
	}

	/**
	 * 通过ruleid执行规则
	 * 
	 * @param ruleId
	 * @param data
	 * @param local
	 * @return
	 */
	public Object ExecuteExpression(BigDecimal ruleId, Object data, Object local) {
		RuleData ruleData = getRuleData(ruleId);
		return ExecuteExpression(ruleData,  data,  local);
	}

	public Object ExecuteExpression(RuleData ruleData, Object data, Object local) {
		Boolean preResult = false;
		Object result = false;

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd H:m:s");
		long beginTime = new Date().getTime();

		String preRuleCond = ruleData.getPreRuleCond();
		String ruleCond = ruleData.getRuleCond();

		if (preRuleCond == null || preRuleCond.trim().equals("")) {
			preResult = true;
		} else {
			preResult = (Boolean) new Expression().ExecuteExpression(
					preRuleCond, data, local);
		}
		if (preResult) {
			if (ruleCond == null || ruleCond.trim().equals("")) {
				result = (Boolean) true;
			} else {
				result = new Expression().ExecuteExpression(
						ruleData.getRuleCond(), data, local);
			}
		}

		long endTime = new Date().getTime();

		if (ruleData.getIsLog()) {
			String message = String.format("%d;%s;%s;%s;%d;%s;%s", ruleData
					.getRuleId().intValue(), ruleData.getRuleName(), format
					.format(beginTime), format.format(endTime), endTime
					- beginTime, String.valueOf(result), String
					.valueOf(ruleData.getIsEstimate()));
			printLog(message, LogType.Trace);
		}

		return result;
	}

	
	public Object ExecuteExpression(String exprCond, Object data) {
		return new Expression().ExecuteExpression(exprCond, data);
	}

	public Object ExecuteExpression(String exprCond, Object data, Object local) {
		return new Expression().ExecuteExpression(exprCond, data, local);
	}

	public Object ExecuteExpression(String exprCond, Object data, Object local,
			Object cache) {
		return new Expression().ExecuteExpression(exprCond, data, local, cache);
	}

	public final Object[] GetKeys(String exprCond) {
		return new Expression().GetKeys(exprCond);
	}

	/**
	 * 根据表达式获得返回的数据类型
	 * 
	 * @param exprCond
	 * @return
	 * @throws Exception
	 */
	public Class<?> getType(String exprCond, BigDecimal subsys, String path)
			throws Exception {
		HashMap<String, Class<?>> hmData = null;
		try {
			hmData = getUsedClass(subsys, path);
		} catch (MalformedURLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return (Class<?>) new Expression().getType(exprCond, (Object) hmData);
	}

	/**
	 * 根据表达式获得返回的数据类型
	 * 
	 * @param exprCond
	 * @return
	 * @throws Exception
	 */
	public Class<?> getType(String exprCond, HashMap<String, Class<?>> hmdata)
			throws Exception {
		return (Class<?>) new Expression().getType(exprCond, (Object) hmdata);
	}

	/**
	 * 返回表达式类型的methods
	 * 
	 * @param exprCond
	 * @param subsys
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Method> getTypeMethods(String exprCond, BigDecimal subsys,
			String path) throws Exception {
		HashMap<String, Class<?>> hmData = null;
		try {
			hmData = getUsedClass(subsys, path);
		} catch (MalformedURLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return new Expression().getTypeMethods(exprCond, (Object) hmData);
	}

	/**
	 * 返回表达式类型的fields
	 * 
	 * @param exprCond
	 * @param subsys
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Field> getTypeFields(String exprCond, BigDecimal subsys,
			String path) throws Exception {
		HashMap<String, Class<?>> hmData = null;
		try {
			hmData = getUsedClass(subsys, path);
		} catch (MalformedURLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return new Expression().getTypeFields(exprCond, (Object) hmData);
	}

	/**
	 * 使用完整路径获得class实例
	 * 
	 * @param jarFullPath
	 * @return
	 * @throws MalformedURLException
	 * @throws ClassNotFoundException
	 */
	public Class<?> getClassByJar(String jarFullPath, String classname)
			throws MalformedURLException, ClassNotFoundException {
		URL url = new URL(jarFullPath);
		URLClassLoader urlCL = new URLClassLoader(new URL[] { url });
		return urlCL.loadClass(classname);
	}

	/**
	 * 
	 * @return
	 * @throws MalformedURLException
	 * @throws ClassNotFoundException
	 */
	public HashMap<BigDecimal, HashMap<String, Class<?>>> getAllUsedClass(
			String path) throws MalformedURLException, ClassNotFoundException {
		String sql = "select * from RULE_ENTITY_JAR";
		HashMap<BigDecimal, HashMap<String, Class<?>>> allSubsysJars = new HashMap<BigDecimal, HashMap<String, Class<?>>>();
		Query query = getSessionNew().createSQLQuery(sql).addEntity(
				RuleEntityJar.class);
		List<RuleEntityJar> list = query.list();
		for (RuleEntityJar entityJar : list) {
			allSubsysJars.put(
					BigDecimal.valueOf(entityJar.getSubSystemId()),
					getUsedClass(
							BigDecimal.valueOf(entityJar.getSubSystemId()),
							path));
		}
		return allSubsysJars;
	}

	/**
	 * 获取需要使用key和class构成的hashmap
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws MalformedURLException
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Class<?>> getUsedClass(BigDecimal subsys, String path)
			throws MalformedURLException, ClassNotFoundException {
		HashMap<String, Class<?>> hmdata = new HashMap<String, Class<?>>();

		String sql = "select RULE_ENTITY_MAPPING.KEY, RULE_ENTITY_MAPPING.CLASS_FULLNAME, RULE_ENTITY_JAR.SUB_SYSTEM_ID,RULE_ENTITY_JAR.JAR_FULLPATH "
				+ " FROM RULE_ENTITY_MAPPING INNER JOIN RULE_ENTITY_JAR on RULE_ENTITY_MAPPING.JAR_ID = RULE_ENTITY_JAR.ID "
				+ " WHERE RULE_ENTITY_JAR.SUB_SYSTEM_ID = " + subsys;
		Query query = getSessionNew().createSQLQuery(sql).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);

		List list = query.list();

		String key = "";
		String jarFullPath = "";
		String classFuleName = "";
		Map map = null;
		Iterator iterator = list.iterator();

		while (iterator.hasNext()) {
			map = (Map) iterator.next();
			key = (String) map.get("KEY");
			// FIXME
			jarFullPath = path + (String) map.get("JAR_FULLPATH");
			//jarFullPath = "file:/templib/TestCaseForRule.jar";
			classFuleName = (String) map.get("CLASS_FULLNAME");
			hmdata.put(key, getClassByJar(jarFullPath, classFuleName));
		}

		return hmdata;
	}

	public String checkCompileExpression(String exprCond) {
		try {
			new Expression(exprCond).Compile();
			return "";
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	/**
	 * 检测表达式是否符合规则
	 * 
	 * @param exprCond
	 * @param subsys
	 * @return
	 */
	public String checkExpression(String exprCond, BigDecimal subsys,
			String path) {
		try {
			Class<?> class1 = getType(exprCond, subsys, path);
			return "";
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	/**
	 * 打印日志
	 * 
	 * @param message
	 * @param logType
	 */
	public void printLog(String message, LogType logType) {
		ThreadContext.put("ThreadID",
				String.valueOf(Thread.currentThread().getId()));
		switch (logType) {
		case Error:
			logger.error(message);
			break;
		case Trace:
			logger.trace(message);
			break;
		default:
			break;
		}
	}

	/**
	 * 获取当前hibernate的session
	 * 
	 * @return
	 */
	@Deprecated
	private Session getSessionFactory() {
		SessionFactory sessionFactory = null;
		try {
			sessionFactory = new Configuration().configure()
					.buildSessionFactory();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return sessionFactory.openSession();
	}

	private Session getSessionNew() {
		Configuration cfg = new Configuration().configure();
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
				.applySettings(cfg.getProperties()).buildServiceRegistry();
		SessionFactory factory = cfg.buildSessionFactory(serviceRegistry);
		return factory.openSession();
	}

	public RuleData getRuleData(BigDecimal ruleId) {
		String sql = "select * from RULE_DATA where RULE_ID = " + ruleId;
		Query query = getSessionNew().createSQLQuery(sql).addEntity(
				RuleData.class);
		List<RuleData> list = query.list();
		return list.get(0);
	}

	/**
	 * 计算表达式返回类型时，剪切不合规表达式字符串
	 * 
	 * @param exp
	 * @return
	 */
	public String cutExpression(String exp) {
		return new Expression().cutExpression(exp);
	}

}
