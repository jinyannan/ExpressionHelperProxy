package gov.customs.rule.data;

// Generated Jul 22, 2015 10:19:43 AM by Hibernate Tools 3.4.0.CR1

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import static org.hibernate.criterion.Example.create;

/**
 * Home object for domain model class RuleEntityMapping.
 * @see gov.customs.rule.data.RuleEntityMapping
 * @author Hibernate Tools
 */
public class RuleEntityMappingHome {

	private static final Log log = LogFactory
			.getLog(RuleEntityMappingHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext()
					.lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException(
					"Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(RuleEntityMapping transientInstance) {
		log.debug("persisting RuleEntityMapping instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(RuleEntityMapping instance) {
		log.debug("attaching dirty RuleEntityMapping instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(RuleEntityMapping instance) {
		log.debug("attaching clean RuleEntityMapping instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(RuleEntityMapping persistentInstance) {
		log.debug("deleting RuleEntityMapping instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public RuleEntityMapping merge(RuleEntityMapping detachedInstance) {
		log.debug("merging RuleEntityMapping instance");
		try {
			RuleEntityMapping result = (RuleEntityMapping) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public RuleEntityMapping findById(long id) {
		log.debug("getting RuleEntityMapping instance with id: " + id);
		try {
			RuleEntityMapping instance = (RuleEntityMapping) sessionFactory
					.getCurrentSession().get(
							"gov.customs.rule.data.RuleEntityMapping", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<RuleEntityMapping> findByExample(RuleEntityMapping instance) {
		log.debug("finding RuleEntityMapping instance by example");
		try {
			List<RuleEntityMapping> results = (List<RuleEntityMapping>) sessionFactory
					.getCurrentSession()
					.createCriteria("gov.customs.rule.data.RuleEntityMapping")
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
