package night.core.persistencia.hibernate;

import org.hibernate.Session;

import night.core.impl.dao.CategoriaPrincipalDAO;
import night.core.impl.dao.ClienteDAO;
import night.core.impl.dao.ComandaDAO;
import night.core.impl.dao.EntradaDAO;
import night.core.impl.dao.GraficosDAO;
import night.core.impl.dao.ItemComandaDAO;
import night.core.impl.dao.ItemConsumoDAO;
import night.core.impl.dao.ItemEstoqueConsumoDAO;
import night.core.impl.dao.ItemEstoqueDAO;
import night.core.impl.dao.MedidaDAO;
import night.core.impl.dao.PagamentoDAO;
import night.core.impl.dao.SubCategoriaDAO;
import night.core.persistencia.DAOFactory;
import night.core.persistencia.hibernate.impl.CategoriaPrincipalHibernateDAO;
import night.core.persistencia.hibernate.impl.ClienteHibernateDAO;
import night.core.persistencia.hibernate.impl.ComandaHibernateDAO;
import night.core.persistencia.hibernate.impl.EntradaHibernateDAO;
import night.core.persistencia.hibernate.impl.GraficosHibernateDAO;
import night.core.persistencia.hibernate.impl.ItemComandaHibernateDAO;
import night.core.persistencia.hibernate.impl.ItemConsumoHibernateDAO;
import night.core.persistencia.hibernate.impl.ItemEstoqueConsumoHibernateDAO;
import night.core.persistencia.hibernate.impl.ItemEstoqueHibernateDAO;
import night.core.persistencia.hibernate.impl.MedidaHibernateDAO;
import night.core.persistencia.hibernate.impl.PagamentoHibernateDAO;
import night.core.persistencia.hibernate.impl.SubCategoriaHibernateDAO;
import night.core.util.HibernateUtil;

public class HibernateDAOFactory extends DAOFactory {

	// You could override this if you don't want HibernateUtil for lookup
	protected Session getCurrentSession() {
		return HibernateUtil.getSessionFactory().getCurrentSession();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see night.core.persistencia.persistence.DAOFactory#buildClienteDAO()
	 */

	@Override
	public ClienteDAO buildClienteDAO() {
		return new ClienteHibernateDAO();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see night.core.persistencia.DAOFactory#buildCategoriaPrincipalDAO()
	 */
	@Override
	public CategoriaPrincipalDAO buildCategoriaPrincipalDAO() {
		return new CategoriaPrincipalHibernateDAO();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see night.core.persistencia.DAOFactory#buildSubCategoriaDAO()
	 */
	@Override
	public SubCategoriaDAO buildSubCategoriaDAO() {
		return new SubCategoriaHibernateDAO();
	}

	@Override
	public ItemEstoqueDAO buildItemEstoqueDAO() {
		return new ItemEstoqueHibernateDAO();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see night.core.persistencia.DAOFactory#buildComandaDAO()
	 */
	@Override
	public ComandaDAO buildComandaDAO() {
		return new ComandaHibernateDAO();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see night.core.persistencia.DAOFactory#buildEntradaDAO()
	 */
	@Override
	public EntradaDAO buildEntradaDAO() {
		return new EntradaHibernateDAO();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see night.core.persistencia.DAOFactory#buildItemConsumoDAO()
	 */
	@Override
	public ItemConsumoDAO buildItemConsumoDAO() {
		return new ItemConsumoHibernateDAO();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see night.core.persistencia.DAOFactory#buildMedidaDAO()
	 */
	@Override
	public MedidaDAO buildMedidaDAO() {
		return new MedidaHibernateDAO();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see night.core.persistencia.DAOFactory#buildPagamentoDAO()
	 */
	@Override
	public PagamentoDAO buildPagamentoDAO() {
		return new PagamentoHibernateDAO();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see night.core.persistencia.DAOFactory#buildItemEstoqueConsumoDAO()
	 */
	@Override
	public ItemEstoqueConsumoDAO buildItemEstoqueConsumoDAO() {
		return new ItemEstoqueConsumoHibernateDAO();
	}

	/**
	 * @return
	 */
	public ItemComandaDAO buildItemComandaDAO() {
		return new ItemComandaHibernateDAO();
	}

	/**
	 * teste utilizando reflection na instanciação do DAO
	 * 
	 * @return
	 */
	public ClienteDAO getClienteDAO() {
		return (ClienteDAO) instantiateDAO(ClienteHibernateDAO.class);
	}

	private AbstractHibernateDAO instantiateDAO(Class daoClass) {
		try {
			AbstractHibernateDAO dao = (AbstractHibernateDAO) daoClass.newInstance();
			dao.setSession(getCurrentSession());
			return dao;
		} catch (Exception ex) {
			throw new RuntimeException("Can not instantiate DAO: " + daoClass, ex);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see night.core.persistencia.DAOFactory#buildGraficosDAO()
	 */
	@Override
	public GraficosDAO buildGraficosDAO() {
		return new GraficosHibernateDAO();
	}

}