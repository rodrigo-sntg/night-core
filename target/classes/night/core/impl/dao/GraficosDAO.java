package night.core.impl.dao;

import java.util.List;
import java.util.Map;

import night.core.persistencia.GenericDAO;
import night.dominio.ChartData;
import night.dominio.SubCategoria;

public interface GraficosDAO extends GenericDAO<SubCategoria, Long> {

	/*
	 * TODO : Regras específicas de negócios aqui Estes metodos serão
	 * sobrescritos se você gerar novamente essa interface você pode herdar
	 * dessa interface para mudar o retorno da dao factory para trazer uma nova
	 * implementação de buildClienteDAO()
	 */

	public Map<Integer, Double> getVendasAno();

	public Map<Integer, Integer> getClientesAno();

	public List<ChartData> getDetalhesMes(int mes);

	public List<ChartData> getDetalhesSemana(int semana);

}