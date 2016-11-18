package night.core.persistencia.hibernate.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.query.Query;

import night.core.impl.dao.GraficosDAO;
import night.core.persistencia.hibernate.AbstractHibernateDAO;
import night.dominio.ChartData;
import night.dominio.SubCategoria;

public class GraficosHibernateDAO extends AbstractHibernateDAO<SubCategoria, Long> implements GraficosDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see night.core.impl.dao.GraficosDAO#getVendasAno()
	 */
	@Override
	public Map<Integer, Double> getVendasAno() {
		if (!getSession().getTransaction().isActive())
			getSession().beginTransaction();

		Query sqlQuery = getSession().createSQLQuery("SELECT MONTH(DATA_CADASTRO), SUM(VALOR_TOTAL) "
				+ "FROM Comanda WHERE DATA_CADASTRO IS NOT NULL GROUP BY YEAR(DATA_CADASTRO), MONTH(DATA_CADASTRO)");

		Map<Integer, Double> mapa = new HashMap<Integer, Double>();
		List lista = sqlQuery.getResultList();
		for (Object object : lista) {
			Object[] listObject = (Object[]) object;
			mapa.put(Integer.valueOf(String.valueOf(listObject[0])), Double.valueOf(String.valueOf(listObject[1])));
		}
		return mapa;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see night.core.impl.dao.GraficosDAO#getVendasAno()
	 */
	@Override
	public Map<Integer, Integer> getClientesAno() {
		if (!getSession().getTransaction().isActive())
			getSession().beginTransaction();

		Query sqlQuery = getSession().createSQLQuery("SELECT MONTH(DATA_CADASTRO), COUNT(VALOR_TOTAL) "
				+ "FROM Comanda WHERE DATA_CADASTRO IS NOT NULL GROUP BY YEAR(DATA_CADASTRO), MONTH(DATA_CADASTRO)");

		Map<Integer, Integer> mapa = new HashMap<Integer, Integer>();
		List lista = sqlQuery.getResultList();
		for (Object object : lista) {
			Object[] listObject = (Object[]) object;
			mapa.put(Integer.valueOf(String.valueOf(listObject[0])), Integer.valueOf(String.valueOf(listObject[1])));
		}
		return mapa;
	}

	@Override
	public List<ChartData> getDetalhesMes(int mes) {
		if (!getSession().getTransaction().isActive())
			getSession().beginTransaction();

		List<ChartData> listChartData = new ArrayList<>();

		Query sqlQuery = getSession()
				.createSQLQuery("SELECT WEEK(DATA_CADASTRO) as semana, " + "SUM(VALOR_TOTAL) as total "
						+ "FROM Comanda WHERE MONTH(DATA_CADASTRO) = :mes " + "GROUP BY WEEK(DATA_CADASTRO);");
		sqlQuery.setParameter("mes", mes);

		List lista = sqlQuery.getResultList();
		for (Object object : lista) {
			ChartData chartData = new ChartData();
			Object[] listObject = (Object[]) object;
			int semana = Integer.valueOf(String.valueOf(listObject[0]));
			double valor = Double.valueOf(String.valueOf(listObject[1]));
			chartData.setName("Semana " + semana);
			chartData.setY(valor);
			chartData.setDrilldown("Semana " + semana);
			listChartData.add(chartData);
		}
		return listChartData;
	}

	@Override
	public List<ChartData> getDetalhesSemana(int semana) {
		if (!getSession().getTransaction().isActive())
			getSession().beginTransaction();

		List<ChartData> listChartData = new ArrayList<>();

		Query sqlQuery = getSession().createSQLQuery("SELECT DAYOFMONTH(DATA_CADASTRO), SUM(VALOR_TOTAL) as total "
				+ "FROM Comanda WHERE WEEKOFYEAR(DATA_CADASTRO) = :semana " + "GROUP BY DAYOFMONTH(DATA_CADASTRO);");
		sqlQuery.setParameter("semana", semana);

		List lista = sqlQuery.getResultList();
		for (Object object : lista) {
			ChartData chartData = new ChartData();
			Object[] listObject = (Object[]) object;
			int dia = Integer.valueOf(String.valueOf(listObject[0]));
			double valor = Double.valueOf(String.valueOf(listObject[1]));
			chartData.setName(String.valueOf(dia));
			chartData.setY(valor);
			chartData.setDrilldown(String.valueOf(dia));
			listChartData.add(chartData);
		}
		return listChartData;
	}
}