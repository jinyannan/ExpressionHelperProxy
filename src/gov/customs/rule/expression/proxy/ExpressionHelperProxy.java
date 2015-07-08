package gov.customs.rule.expression.proxy;

import java.util.ArrayList;
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
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;




import org.hibernate.transform.Transformers;





















//import gov.customs.jm.data.RuleRelData;
import gov.customs.rule.data.*;

public class ExpressionHelperProxy {
	public Object ExecuteExpression(String exprCond, Object data){
		return new Expression().ExecuteExpression(exprCond, data);
	}
	
	public Object ExecuteExpression(String exprCond, Object data, Object local){
		return new Expression().ExecuteExpression(exprCond, data, local);
	}
	
	public Object ExecuteExpression(String exprCond, Object data, Object local, Object cache){
		return new Expression().ExecuteExpression(exprCond, data, local, cache);
	}
	
	public final Object[] GetKeys(String exprCond){
		return new Expression().GetKeys(exprCond);
	}

	/**
	 * 根据表达式获得返回的数据类型
	 * @param exprCond
	 * @return
	 * @throws Exception 
	 */
	public Class<?> getType(String exprCond, BigDecimal subsys) throws Exception{
		HashMap<String, Class<?>> hmData = null;
		try {
			hmData = getUsedClass(subsys);
		} catch (MalformedURLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return (Class<?>)new Expression().getType(exprCond, (Object)hmData);
	}

	/**
	 * 根据表达式获得返回的数据类型
	 * @param exprCond
	 * @return
	 * @throws Exception 
	 */
	public Class<?> getType(String exprCond, HashMap<String, Class<?>> hmdata) throws Exception{
		return (Class<?>)new Expression().getType(exprCond, (Object)hmdata);
	}

	/**
	 * 返回表达式类型的methods
	 * @param exprCond
	 * @param subsys
	 * @return
	 * @throws Exception 
	 */
	public ArrayList<Method> getTypeMethods(String exprCond, BigDecimal subsys) throws Exception{
		HashMap<String, Class<?>> hmData = null;
		try {
			hmData = getUsedClass(subsys);
		} catch (MalformedURLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return new Expression().getTypeMethods(exprCond, (Object)hmData);
	}
	
	/**
	 * 返回表达式类型的fields
	 * @param exprCond
	 * @param subsys
	 * @return
	 * @throws Exception 
	 */
	public ArrayList<Field> getTypeFields(String exprCond, BigDecimal subsys) throws Exception{
		HashMap<String, Class<?>> hmData = null;
		try {
			hmData = getUsedClass(subsys);
		} catch (MalformedURLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return new Expression().getTypeFields(exprCond, (Object)hmData);
	}
	
	/**
	 * 使用完整路径获得class实例
	 * @param jarFullPath
	 * @return
	 * @throws MalformedURLException
	 * @throws ClassNotFoundException
	 */
	public Class<?> getClassByJar(String jarFullPath, String classname) throws MalformedURLException, ClassNotFoundException {
		URL url = new URL(jarFullPath); 
        URLClassLoader urlCL = new URLClassLoader(new URL[]{url}); 
        return urlCL.loadClass(classname); 
	}
	
	/**
	 * 
	 * @return
	 * @throws MalformedURLException
	 * @throws ClassNotFoundException
	 */
	public HashMap<BigDecimal, HashMap<String, Class<?>>> getAllUsedClass() throws MalformedURLException, ClassNotFoundException{
		String sql = "select * from RULE_ENTITY_JAR";
		HashMap<BigDecimal, HashMap<String, Class<?>>> allSubsysJars = new HashMap<BigDecimal, HashMap<String, Class<?>>>();
		Query query = getSessionFactory().createSQLQuery(sql).addEntity(RuleEntityJar.class);
		List<RuleEntityJar> list = query.list();
		for (RuleEntityJar entityJar : list) {
			allSubsysJars.put(BigDecimal.valueOf(entityJar.getSubSystemId()), getUsedClass(BigDecimal.valueOf(entityJar.getSubSystemId())));
		}
		return allSubsysJars;
	}
	
	/**
	 * 获取需要使用key和class构成的hashmap
	 * @return
	 * @throws ClassNotFoundException 
	 * @throws MalformedURLException 
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Class<?>> getUsedClass(BigDecimal subsys) throws MalformedURLException, ClassNotFoundException {
		HashMap<String, Class<?>> hmdata = new HashMap<String, Class<?>>();
		Session s = getSessionFactory();
		
		String sql = "select ENTITY_MAPPING.KEY, ENTITY_MAPPING.CLASS_FULLNAME, ENTITY_JAR.SUB_SYSTEM,ENTITY_JAR.JAR_FULLPATH "
		+ "	FROM ENTITY_MAPPING INNER JOIN ENTITY_JAR on ENTITY_MAPPING.JAR_ID = ENTITY_JAR.ID "
		+ " WHERE ENTITY_JAR.SUB_SYSTEM = " + subsys;

		Query query = s.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		
		List list = query.list();
		
		String key = "";
		String jarFullPath = "";
		String classFuleName = "";
		Map map = null;
		Iterator iterator = list.iterator();

		while (iterator.hasNext()) {
			map = (Map)iterator.next();
			key = (String)map.get("KEY");
			jarFullPath = (String) map.get("JAR_FULLPATH");
			classFuleName = (String)map.get("CLASS_FULLNAME");
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
	 * @param exprCond
	 * @param subsys
	 * @return
	 */
	public String checkExpression(String exprCond, BigDecimal subsys)  {
		try {
			Class<?> class1 = getType(exprCond, subsys);
			return "";
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	/**
	 * 获取当前hibernate的session
	 * @return
	 */
	private Session getSessionFactory() {
		SessionFactory sessionFactory = null;
		try {
			sessionFactory= new Configuration().configure()
					.buildSessionFactory();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return sessionFactory.openSession();
	}
	
	/**
	 * 计算表达式返回类型时，剪切不合规表达式字符串
	 * @param exp
	 * @return
	 */
	public String cutExpression(String exp) {
		return new Expression().cutExpression(exp);
	}
	
}
