package night.core.persistencia.hibernate.impl;

import org.hibernate.query.Query;

import night.core.impl.dao.ComandaDAO;
import night.core.persistencia.hibernate.AbstractHibernateDAO;
import night.dominio.Comanda;

public class ComandaHibernateDAO extends AbstractHibernateDAO<Comanda, Long> implements ComandaDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see night.core.impl.dao.ComandaDAO#getByCodigoComanda(long)
	 */

	public Comanda getByCodigoComanda(long cod) {
		if (!getSession().getTransaction().isActive())
			getSession().beginTransaction();
		Comanda comanda = null;
		try {
			Query query = getSession().createQuery("from Comanda where NUMERO_COMANDA=:cod and STATUS = 1");
			query.setParameter("cod", cod);
			query.getResultList();
			comanda = (Comanda) query.getSingleResult();

		} catch (Exception e) {
			getSession().close();
		} finally {
		}

		return comanda;

	}

}