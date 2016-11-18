package night.core.persistencia;

import night.core.impl.dao.CategoriaPrincipalDAO;
import night.core.impl.dao.ClienteDAO;
import night.core.impl.dao.ComandaDAO;
import night.core.impl.dao.EntradaDAO;
import night.core.impl.dao.GraficosDAO;
import night.core.impl.dao.ItemConsumoDAO;
import night.core.impl.dao.ItemEstoqueConsumoDAO;
import night.core.impl.dao.ItemEstoqueDAO;
import night.core.impl.dao.MedidaDAO;
import night.core.impl.dao.PagamentoDAO;
import night.core.impl.dao.SubCategoriaDAO;
import night.core.persistencia.hibernate.HibernateDAOFactory;

public abstract class DAOFactory {

	private static final DAOFactory HIBERNATE = new HibernateDAOFactory();

	/**
	 * Default factory implementation
	 */
	public static final DAOFactory DEFAULT = HIBERNATE;

	public static DAOFactory instance(Class factory) {
		try {
			return (DAOFactory) factory.newInstance();
		} catch (Exception ex) {
			throw new RuntimeException("Couldn't create DAOFactory: " + factory);
		}
	}

	public abstract ClienteDAO buildClienteDAO();

	public abstract CategoriaPrincipalDAO buildCategoriaPrincipalDAO();

	public abstract SubCategoriaDAO buildSubCategoriaDAO();

	public abstract ItemEstoqueDAO buildItemEstoqueDAO();

	public abstract ComandaDAO buildComandaDAO();

	public abstract EntradaDAO buildEntradaDAO();

	public abstract ItemConsumoDAO buildItemConsumoDAO();

	public abstract MedidaDAO buildMedidaDAO();

	public abstract PagamentoDAO buildPagamentoDAO();

	public abstract ItemEstoqueConsumoDAO buildItemEstoqueConsumoDAO();

	public abstract GraficosDAO buildGraficosDAO();

}