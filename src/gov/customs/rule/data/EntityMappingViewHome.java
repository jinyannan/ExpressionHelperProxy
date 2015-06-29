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
 * Home object for domain model class EntityMappingView.
 * @see gov.customs.rule.data.EntityMappingView
 * @author Hibernate Tools
 */
public class EntityMappingViewHome {

	private static final Log log = LogFactory
			.getLog(EntityMappingViewHome.class);

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

	public void persist(EntityMappingView transientInstance) {
		log.debug("persisting EntityMappingView instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(EntityMappingView instance) {
		log.debug("attaching dirty EntityMappingView instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(EntityMappingView instance) {
		log.debug("attaching clean EntityMappingView instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(EntityMappingView persistentInstance) {
		log.debug("deleting EntityMappingView instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public EntityMappingView merge(EntityMappingView detachedInstance) {
		log.debug("merging EntityMappingView instance");
		try {
			EntityMappingView result = (EntityMappingView) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public EntityMappingView findById(
			gov.customs.rule.data.EntityMappingViewId id) {
		log.debug("getting EntityMappingView instance with id: " + id);
		try {
			EntityMappingView instance = (EntityMappingView) sessionFactory
					.getCurrentSession().get(
							"gov.customs.rule.data.EntityMappingView", id);
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

	public List<EntityMappingView> findByExample(EntityMappingView instance) {
		log.debug("finding EntityMappingView instance by example");
		try {
			List<EntityMappingView> results = (List<EntityMappingView>) sessionFactory
					.getCurrentSession()
					.createCriteria("gov.customs.rule.data.EntityMappingView")
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
