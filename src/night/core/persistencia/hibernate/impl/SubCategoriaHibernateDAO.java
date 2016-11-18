package night.core.persistencia.hibernate.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.query.Query;

import night.core.impl.dao.SubCategoriaDAO;
import night.core.persistencia.hibernate.AbstractHibernateDAO;
import night.dominio.SubCategoria;

public class SubCategoriaHibernateDAO extends AbstractHibernateDAO<SubCategoria, Long> implements SubCategoriaDAO {

	public List<SubCategoria> findByIdCategoria(SubCategoria entidade) {
		if (!getSession().getTransaction().isActive())
			getSession().beginTransaction();
		getSession().flush();

		List<SubCategoria> subCategorias = new ArrayList<>();
		try {
			Query query = getSession().createQuery("from SubCategoria where categoria_ID=:idCategoria");
			query.setParameter("idCategoria", entidade.getCategoria().getId());
			subCategorias = (List<SubCategoria>) query.getResultList();

		} catch (Exception e) {
			getSession().close();
		} finally {
		}
		return subCategorias;
	}

}