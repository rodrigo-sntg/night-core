package night.core.persistencia.hibernate.impl;

import java.util.List;

import org.hibernate.query.Query;

import night.core.impl.dao.ClienteDAO;
import night.core.persistencia.hibernate.AbstractHibernateDAO;
import night.dominio.Cliente;

public class ClienteHibernateDAO extends AbstractHibernateDAO<Cliente, Long> implements ClienteDAO {

	@Override
	public List<Cliente> findByStatus(String status) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * faz o teste pelo long passado, pode ser cpf ou rg
	 * 
	 * @param long
	 *            doc - documento passado como long, ou seja, sem formatação
	 *            alguma
	 */
	public Cliente getByDocumento(long doc) {
		if (!getSession().getTransaction().isActive())
			getSession().beginTransaction();
		Cliente cliente = null;
		try {
			Query query = getSession().createQuery("from Cliente where CPF=:cpf");
			query.setParameter("cpf", doc);
			cliente = (Cliente) query.uniqueResult();

			if (cliente == null) {
				query = getSession().createQuery("from Cliente where RG=:rg");
				query.setParameter("rg", doc);
				cliente = (Cliente) query.uniqueResult();

			}

		} catch (Exception e) {
			getSession().close();
		} finally {
		}

		return cliente;

	}

}