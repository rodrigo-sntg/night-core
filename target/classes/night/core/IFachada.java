package night.core;

import java.util.List;
import java.util.Map;

import night.core.aplicacao.Resultado;
import night.dominio.ChartData;
import night.dominio.IEntidade;

public interface IFachada {

	public Resultado salvar(IEntidade entidade);

	public Resultado alterar(IEntidade entidade);

	public Resultado excluir(IEntidade entidade);

	public Resultado consultar(IEntidade entidade);

	public Resultado visualizar(IEntidade entidade);

	public Resultado buscarTodos(IEntidade entidade);

	public Resultado buscarSubCategoria(IEntidade entidade);

	public Map<Integer, Double> vendasAno();

	public Map<Integer, Integer> clientesAno();

	public List<ChartData> getDetalhesMes(int mes);

	public List<ChartData> getDetalhesSemana(int semana);

}
