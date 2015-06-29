package gov.customs.rule.data;

// Generated Jun 26, 2015 2:18:55 PM by Hibernate Tools 3.4.0.CR1

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import static org.hibernate.criterion.Example.create;

/**
 * Home object for domain model class EntityJar.
 * @see gov.customs.rule.data.EntityJar
 * @author Hibernate Tools
 */
public class EntityJarHome {

	private static final Log log = LogFactory.getLog(EntityJarHome.class);

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

	public void persist(EntityJar transientInstance) {
		log.debug("persisting EntityJar instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(EntityJar instance) {
		log.debug("attaching dirty EntityJar instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(EntityJar instance) {
		log.debug("attaching clean EntityJar instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(EntityJar persistentInstance) {
		log.debug("deleting EntityJar instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public EntityJar merge(EntityJar detachedInstance) {
		log.debug("merging EntityJar instance");
		try {
			EntityJar result = (EntityJar) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public EntityJar findById(long id) {
		log.debug("getting EntityJar instance with id: " + id);
		try {
			EntityJar instance = (EntityJar) sessionFactory.getCurrentSession()
					.get("gov.customs.rule.data.EntityJar", id);
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

	public List<EntityJar> findByExample(EntityJar instance) {
		log.debug("finding EntityJar instance by example");
		try {
			List<EntityJar> results = (List<EntityJar>) sessionFactory
					.getCurrentSession()
					.createCriteria("gov.customs.rule.data.EntityJar")
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
