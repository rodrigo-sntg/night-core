package night.core.persistencia.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;

import night.core.persistencia.GenericDAO;
import night.core.util.HibernateUtil;
import night.dominio.Dominio;

public abstract class AbstractHibernateDAO<T, ID extends Serializable> implements GenericDAO<T, ID> {

	private Session session;

	private Class<T> persistentClass;

	public AbstractHibernateDAO() {

		this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	public void setSession(Session session) {
		this.session = session;
	}

	protected Session getSession() {
		if (session == null)
			session = HibernateUtil.getSessionFactory().getCurrentSession();
		if (!session.isOpen())
			session = HibernateUtil.getSessionFactory().openSession();
		return session;
	}

	public Class<T> getPersistentClass() {
		return persistentClass;
	}

	public T getById(ID id) {
		if (!getSession().getTransaction().isActive())
			getSession().beginTransaction();
		T retorno = null;
		try {
			retorno = (T) getSession().get(getPersistentClass(), id);

		} catch (Exception e) {
		} finally {
		}

		return retorno;

	}

	@SuppressWarnings("deprecation")
	public T getById(ID id, boolean lock) {
		if (!getSession().getTransaction().isActive())
			getSession().beginTransaction();
		T retorno = null;
		try {
			if (lock) {
				retorno = (T) getSession().get(getPersistentClass(), id, LockMode.UPGRADE);
			} else
				retorno = getById(id);

		} catch (Exception e) {
		} finally {
		}

		return retorno;

	}

	public T loadById(ID id) {
		if (!getSession().getTransaction().isActive())
			getSession().beginTransaction();
		T retorno = null;
		try {
			retorno = (T) getSession().load(getPersistentClass(), id);

		} catch (Exception e) {
		} finally {
		}

		return retorno;
	}

	public void save(T entity) {
		if (!getSession().getTransaction().isActive()) {
			getSession().beginTransaction();
		}
		try {
			// T newEntityRef = (T) getSession().merge(entity);
			getSession().save(entity);
			// this.saveOrUpdate(entity);
			getSession().flush();
			getSession().getTransaction().commit();
		} catch (Exception e) {
			getSession().getTransaction().rollback();
		} finally {
		}
	}

	public void update(T entity) {
		if (!getSession().getTransaction().isActive())
			getSession().beginTransaction();
		try {
			T newEntityRef = (T) getSession().merge(entity);
			getSession().saveOrUpdate(newEntityRef);
			getSession().getTransaction().commit();
			// getSession().flush();
			getSession().close();
			System.out.println("passou");

		} catch (Exception e) {
			getSession().getTransaction().rollback();
		} finally {
		}

	}

	public void saveOrUpdate(T entity) {
		if (!getSession().getTransaction().isActive())
			getSession().beginTransaction();
		try {

			T newEntityRef = (T) getSession().merge(entity);
			getSession().merge(newEntityRef);
			getSession().saveOrUpdate(newEntityRef);
			getSession().flush();
			getSession().getTransaction().commit();
		} catch (Exception e) {
			getSession().getTransaction().rollback();
		} finally {
		}

	}

	public void delete(T entity) {
		if (!getSession().getTransaction().isActive())
			getSession().getTransaction().begin();

		try {
			T newEntityRef = (T) getSession().merge(entity);
			getSession().delete(newEntityRef);
			getSession().flush();
			getSession().getTransaction().commit();

		} catch (Exception e) {
			getSession().getTransaction().rollback();
		} finally {
		}

	}

	public void deleteById(ID id) {
		if (!getSession().getTransaction().isActive())
			getSession().beginTransaction();
		try {

			getSession().delete(loadById(id));
			getSession().flush();
			getSession().getTransaction().commit();
		} catch (Exception e) {
			getSession().getTransaction().rollback();
		} finally {
		}

	}

	public List<T> findAll() {
		return findByCriteria();
	}

	/**
	 * Use this inside subclasses as a convenience method.
	 */
	@SuppressWarnings("unchecked")
	protected List<T> findByCriteria(Criterion... criterion) {
		Criteria crit = null;
		if (!getSession().getTransaction().isActive())
			getSession().beginTransaction();
		try {

			crit = getSession().createCriteria(getPersistentClass());
			for (Criterion c : criterion) {
				crit.add(c);
			}

		} catch (Exception e) {
		} finally {
		}

		return crit.list();
	}

	/**
	 * Find by criteria.
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByCriteria(Map criterias) {
		Criteria criteria = null;
		if (!getSession().getTransaction().isActive())
			getSession().beginTransaction();

		try {

			criteria = getSession().createCriteria(getPersistentClass());
			criteria.add(Restrictions.allEq(criterias));
		} catch (Exception e) {
		} finally {
		}

		return criteria.list();
	}

	/**
	 * This method will execute an HQL query and return the number of affected
	 * entities.
	 */
	protected int executeQuery(String query, String namedParams[], Object params[]) {
		Query q;
		int ret = 0;
		if (!getSession().getTransaction().isActive())
			getSession().beginTransaction();

		try {
			q = getSession().createQuery(query);
			if (namedParams != null) {
				for (int i = 0; i < namedParams.length; i++) {
					q.setParameter(namedParams[i], params[i]);
				}
			}
			ret = q.executeUpdate();
			getSession().getTransaction().commit();
		} catch (Exception e) {
			getSession().getTransaction().rollback();
		} finally {
		}
		return ret;
	}

	protected int executeQuery(String query) {
		int ret = 0;
		if (!getSession().getTransaction().isActive())
			getSession().beginTransaction();

		try {
			ret = executeQuery(query, null, null);
			getSession().getTransaction().commit();
		} catch (Exception e) {
			getSession().getTransaction().rollback();
		} finally {
		}
		return ret;
	}

	/**
	 * This method will execute a Named HQL query and return the number of
	 * affected entities.
	 */
	protected int executeNamedQuery(String namedQuery, String namedParams[], Object params[]) {

		int ret = 0;
		if (!getSession().getTransaction().isActive())
			getSession().beginTransaction();

		try {
			Query q = getSession().getNamedQuery(namedQuery);
			if (namedParams != null) {
				for (int i = 0; i < namedParams.length; i++) {
					q.setParameter(namedParams[i], params[i]);
				}
			}
			ret = q.executeUpdate();
			getSession().getTransaction().commit();
		} catch (Exception e) {
			getSession().getTransaction().rollback();
		} finally {
		}
		return ret;
	}

	protected int executeNamedQuery(String namedQuery) {
		int ret = 0;
		if (!getSession().getTransaction().isActive())
			getSession().beginTransaction();

		try {
			ret = executeNamedQuery(namedQuery, null, null);
			getSession().getTransaction().commit();
		} catch (Exception e) {
			getSession().getTransaction().rollback();
		} finally {
		}
		return ret;
	}

	@SuppressWarnings("unchecked")
	public List<T> findByExample(T exampleInstance, String[] excludeProperty) {

		Criteria crit = null;
		if (!getSession().getTransaction().isActive())
			getSession().beginTransaction();
		try {
			crit = getSession().createCriteria(getPersistentClass());
			Example example = Example.create(exampleInstance).excludeZeroes().enableLike().ignoreCase();
			for (String exclude : excludeProperty) {
				example.excludeProperty(exclude);
			}
			crit.add(example);
		} catch (Exception e) {
		} finally {
		}
		return crit.list();
	}

	public List<T> findByStoreDate(Timestamp date) {
		List<T> lista = null;
		if (!getSession().getTransaction().isActive())
			getSession().beginTransaction();
		try {
			lista = findByCriteria(Restrictions.eq(Dominio.DATA_CADASTRO, date));
		} catch (Exception e) {
		} finally {
		}

		return lista;
	}
}