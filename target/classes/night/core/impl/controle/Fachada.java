package night.core.impl.controle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import night.core.IFachada;
import night.core.IStrategy;
import night.core.aplicacao.Resultado;
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
import night.core.impl.negocio.ComplementarDtCadastro;
import night.core.impl.negocio.ValidadorCpf;
import night.core.impl.negocio.ValidadorDadosObrigatoriosCliente;
import night.core.impl.negocio.ValidadorDadosObrigatoriosEstoque;
import night.core.impl.negocio.ValidadorDadosObrigatoriosPesquisaCliente;
import night.core.impl.negocio.ValidadorDocumentoDuplicado;
import night.core.persistencia.GenericDAO;
import night.core.persistencia.hibernate.HibernateDAOFactory;
import night.dominio.CategoriaPrincipal;
import night.dominio.ChartData;
import night.dominio.Cliente;
import night.dominio.Comanda;
import night.dominio.Entrada;
import night.dominio.IEntidade;
import night.dominio.ItemComanda;
import night.dominio.ItemConsumo;
import night.dominio.ItemEstoque;
import night.dominio.ItemEstoqueConsumo;
import night.dominio.Medida;
import night.dominio.Pagamento;
import night.dominio.SubCategoria;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class Fachada implements IFachada {

	public static final String SALVAR = "SALVAR";
	public static final String CONSULTAR = "CONSULTAR";
	public static final String EXCLUIR = "EXCLUIR";
	public static final String ALTERAR = "ALTERAR";

	/**
	 * Mapa de DAOS, será indexado pelo nome da entidade O valor é uma instância
	 * do DAO para uma dada entidade;
	 */
	private Map<String, GenericDAO> daosGeneric;

	/**
	 * Mapa para conter as regras de negócio de todas operações por entidade; O
	 * valor é um mapa que de regras de negócio indexado pela operação
	 */
	private Map<String, Map<String, List<IStrategy>>> rns;

	private Resultado resultado;

	public Fachada() {
		/* Intânciando o Map de DAOS */

		daosGeneric = new HashMap<String, GenericDAO>();

		/* Intânciando o Map de Regras de Negócio */
		rns = new HashMap<String, Map<String, List<IStrategy>>>();

		/**************************************************************************
		 * Criando instâncias dos DAOs a serem utilizados
		 **************************************************************************
		 */

		HibernateDAOFactory factory = new HibernateDAOFactory();
		ClienteDAO cliDAO = factory.buildClienteDAO();
		CategoriaPrincipalDAO catPrincipalDAO = factory.buildCategoriaPrincipalDAO();
		SubCategoriaDAO subCategoriaDAO = factory.buildSubCategoriaDAO();
		ComandaDAO comandaDAO = factory.buildComandaDAO();
		ItemEstoqueDAO itemEstoqueDAO = factory.buildItemEstoqueDAO();
		ItemEstoqueConsumoDAO itemEstoqueConsumoDAO = factory.buildItemEstoqueConsumoDAO();
		ItemConsumoDAO itemConsumoDAO = factory.buildItemConsumoDAO();
		MedidaDAO medidaDAO = factory.buildMedidaDAO();
		EntradaDAO entradaDAO = factory.buildEntradaDAO();
		PagamentoDAO pagamentoDAO = factory.buildPagamentoDAO();
		ItemComandaDAO itemComandaDAO = factory.buildItemComandaDAO();
		GraficosDAO graficosDAO = factory.buildGraficosDAO();

		/**************************************************************************
		 * Adicionando cada dao no MAP indexando pelo nome da classe
		 **************************************************************************
		 */

		daosGeneric.put(Cliente.class.getName(), cliDAO);
		daosGeneric.put(CategoriaPrincipal.class.getName(), catPrincipalDAO);
		daosGeneric.put(SubCategoria.class.getName(), subCategoriaDAO);
		daosGeneric.put(Comanda.class.getName(), comandaDAO);
		daosGeneric.put(ItemEstoque.class.getName(), itemEstoqueDAO);
		daosGeneric.put(ItemEstoqueConsumo.class.getName(), itemEstoqueConsumoDAO);
		daosGeneric.put(ItemConsumo.class.getName(), itemConsumoDAO);
		daosGeneric.put(Medida.class.getName(), medidaDAO);
		daosGeneric.put(Entrada.class.getName(), entradaDAO);
		daosGeneric.put(Pagamento.class.getName(), pagamentoDAO);
		daosGeneric.put(ItemComanda.class.getName(), itemComandaDAO);
		daosGeneric.put("graficos", graficosDAO);

		/**************************************************************************
		 * Criando instâncias de regras de negócio a serem utilizados
		 **************************************************************************
		 */

		ValidadorDadosObrigatoriosCliente vrDadosObrigatoriosCliente = new ValidadorDadosObrigatoriosCliente();
		ComplementarDtCadastro cDtCadastro = new ComplementarDtCadastro();
		ValidadorCpf vCpf = new ValidadorCpf();
		ValidadorDocumentoDuplicado vCpfDuplicado = new ValidadorDocumentoDuplicado();
		ValidadorDadosObrigatoriosPesquisaCliente vDadosPesquisa = new ValidadorDadosObrigatoriosPesquisaCliente();

		/**************************************************************************
		 * Strategies Pagamento
		 **************************************************************************
		 */

		/*
		 * Criando uma lista para conter as regras de negócio de estoque quando
		 * a operação for this.SALVAR
		 */
		List<IStrategy> rnsSalvarPagamento = new ArrayList<IStrategy>();
		/*
		 * Adicionando as regras a serem utilizadas na operação salvar do
		 * cliente
		 */
		rnsSalvarPagamento.add(cDtCadastro);

		/*
		 * Cria o mapa que poderá conter todas as listas de regras de negócio
		 * específica por operação do cliente
		 */
		Map<String, List<IStrategy>> rnsPagamento = new HashMap<String, List<IStrategy>>();
		/*
		 * Adiciona a listra de regras na operação salvar no mapa do cliente
		 * (lista criada na linha 93)
		 */
		rnsPagamento.put(Fachada.SALVAR, rnsSalvarPagamento);

		/**************************************************************************
		 * Strategies itemEstoque
		 **************************************************************************
		 */
		ValidadorDadosObrigatoriosEstoque vDadosObrigatoriosEstoque = new ValidadorDadosObrigatoriosEstoque();

		/*
		 * Criando uma lista para conter as regras de negócio de estoque quando
		 * a operação para this.SALVAR
		 */
		List<IStrategy> rnsSalvarItemEstoque = new ArrayList<IStrategy>();
		/*
		 * Adicionando as regras a serem utilizadas na operação salvar do
		 * cliente
		 */
		rnsSalvarItemEstoque.add(vDadosObrigatoriosEstoque);
		rnsSalvarItemEstoque.add(cDtCadastro);

		/*
		 * Cria o mapa que poderá conter todas as listas de regras de negócio
		 * específica por operação do cliente
		 */
		Map<String, List<IStrategy>> rnsEstoque = new HashMap<String, List<IStrategy>>();
		/*
		 * Adiciona a listra de regras na operação salvar no mapa do cliente
		 * (lista criada na linha 93)
		 */
		rnsEstoque.put(Fachada.SALVAR, rnsSalvarItemEstoque);

		/**************************************************************************
		 * Strategies Cliente
		 ************************************************************************** 
		 */
		/*
		 * Criando uma lista para conter as regras de negócio de cliente quando
		 * a operação para this.SALVAR
		 */
		List<IStrategy> rnsSalvarCliente = new ArrayList<IStrategy>();
		/*
		 * Adicionando as regras a serem utilizadas na operação salvar do
		 * cliente
		 */
		rnsSalvarCliente.add(cDtCadastro);
		rnsSalvarCliente.add(vrDadosObrigatoriosCliente);
		rnsSalvarCliente.add(vCpf);
		rnsSalvarCliente.add(vCpfDuplicado);

		/*
		 * Criando uma lista para conter as regras de negócio de cliente quando
		 * a operação for this.CONSULTAR
		 */
		List<IStrategy> rnsConsultarCliente = new ArrayList<IStrategy>();
		/*
		 * Adicionando as regras a serem utilizadas na operação salvar do
		 * cliente
		 */
		rnsConsultarCliente.add(vDadosPesquisa);

		/*
		 * Criando uma lista para conter as regras de negócio de cliente quando
		 * a operação para this.CONSULTAR
		 */
		List<IStrategy> rnsAlterarCliente = new ArrayList<IStrategy>();
		/*
		 * Adicionando as regras a serem utilizadas na operação salvar do
		 * cliente
		 */
		rnsAlterarCliente.add(vrDadosObrigatoriosCliente);
		rnsAlterarCliente.add(vCpf);

		/*
		 * Cria o mapa que poderá conter todas as listas de regras de negócio
		 * específica por operação do cliente
		 */
		Map<String, List<IStrategy>> rnsCliente = new HashMap<String, List<IStrategy>>();
		/*
		 * Adiciona a listra de regras na operação salvar no mapa do cliente
		 * (lista criada na linha 93)
		 */
		rnsCliente.put(Fachada.SALVAR, rnsSalvarCliente);
		rnsCliente.put(Fachada.CONSULTAR, rnsConsultarCliente);
		rnsCliente.put(Fachada.ALTERAR, rnsAlterarCliente);

		/*************************************************************************
		 * Strategies ItemComanda
		 *************************************************************************
		 */

		/*
		 * Criando uma lista para conter as regras de negócio de cliente quando
		 * a operação para Fachada.SALVAR
		 */
		List<IStrategy> rnsSalvarItemComanda = new ArrayList<IStrategy>();
		/*
		 * Adicionando as regras a serem utilizadas na operação salvar do
		 * cliente
		 */
		rnsSalvarItemComanda.add(cDtCadastro);

		/*
		 * Cria o mapa que poderá conter todas as listas de regras de negócio
		 * específica por operação do ItemComanda
		 */
		Map<String, List<IStrategy>> rnsItemComanda = new HashMap<String, List<IStrategy>>();

		rnsItemComanda.put(Fachada.SALVAR, rnsSalvarItemComanda);

		/*************************************************************************
		 * Inserindo os maps no map de regras de negócio
		 *************************************************************************
		 */

		/*
		 * Adiciona o mapa(criado na linha 101) com as regras indexadas pelas
		 * operações no mapa geral indexado pelo nome da entidade. Observe que
		 * este mapa (rns) é o mesmo utilizado na linha 88.
		 */
		rns.put(Cliente.class.getName(), rnsCliente);
		rns.put(ItemEstoque.class.getName(), rnsEstoque);
		rns.put(ItemComanda.class.getName(), rnsItemComanda);
		rns.put(Pagamento.class.getName(), rnsPagamento);

	}

	@Override
	public Resultado salvar(IEntidade entidade) {
		resultado = new Resultado();
		String nmClasse = entidade.getClass().getName();

		String msg = executarRegras(entidade, Fachada.SALVAR);

		if (msg == null) {
			GenericDAO dao = daosGeneric.get(nmClasse);
			try {
				dao.save(entidade);
				List<IEntidade> entidades = new ArrayList<IEntidade>();
				entidades.add(entidade);
				resultado.setEntidades(entidades);
			} catch (Exception e) {
				e.printStackTrace();
				resultado.setMsg("Não foi possível realizar o registro!");

			}

		} else {
			resultado.setMsg(msg);
		}

		return resultado;
	}

	@Override
	public Resultado alterar(IEntidade entidade) {
		resultado = new Resultado();
		String nmClasse = entidade.getClass().getName();

		String msg = executarRegras(entidade, Fachada.ALTERAR);

		if (msg == null) {
			GenericDAO dao = daosGeneric.get(nmClasse);
			try {
				dao.update(entidade);
				List<IEntidade> entidades = new ArrayList<IEntidade>();
				entidades.add(entidade);
				resultado.setEntidades(entidades);
			} catch (Exception e) {
				e.printStackTrace();
				resultado.setMsg("Não foi possível realizar o registro!");

			}
		} else {
			resultado.setMsg(msg);

		}

		return resultado;

	}

	@Override
	public Resultado excluir(IEntidade entidade) {
		resultado = new Resultado();
		String nmClasse = entidade.getClass().getName();

		String msg = executarRegras(entidade, Fachada.EXCLUIR);

		if (msg == null) {
			GenericDAO dao = daosGeneric.get(nmClasse);
			try {
				dao.delete(entidade);
				List<IEntidade> entidades = new ArrayList<IEntidade>();
				entidades.add(entidade);
				resultado.setEntidades(entidades);
			} catch (Exception e) {
				e.printStackTrace();
				resultado.setMsg("Nao foi possevel realizar o registro!");

			}
		} else {
			resultado.setMsg(msg);

		}

		return resultado;

	}

	@Override
	public Resultado consultar(IEntidade entidade) {
		resultado = new Resultado();
		String nmClasse = entidade.getClass().getName();

		String msg = executarRegras(entidade, Fachada.CONSULTAR);

		if (msg == null) {
			GenericDAO dao = daosGeneric.get(nmClasse);
			try {
				if (nmClasse.equals(Cliente.class.getName())) {
					Cliente cli = null;
					ClienteDAO cliDao = (ClienteDAO) dao;
					if (((Cliente) entidade).getCpf() != 0)
						cli = cliDao.getByDocumento(((Cliente) entidade).getCpf());
					if (((Cliente) entidade).getRg() != 0)
						cli = cliDao.getByDocumento(((Cliente) entidade).getRg());

					if (cli != null)
						resultado.setEntidades(Arrays.asList(cli));

				} else if (entidade.getId() != 0) {
					resultado.setEntidades(Arrays.asList((IEntidade) dao.getById(entidade.getId())));

				} else if (nmClasse.equals(Comanda.class.getName())) {
					Comanda com = null;
					ComandaDAO comDao = (ComandaDAO) dao;
					resultado.setEntidades(Arrays
							.asList((IEntidade) comDao.getByCodigoComanda(((Comanda) entidade).getNumeroComanda())));

				} else {
					resultado.setEntidades(dao.findByExample(entidade, new String[] {}));

				}
			} catch (Exception e) {
				e.printStackTrace();
				resultado.setMsg("Nao foi possevel realizar a consulta!");

			}
			if (resultado.getEntidades() == null || resultado.getEntidades().get(0) == null)
				resultado.setMsg("Não foi possível encontrar resultados.");
		} else {
			resultado.setMsg(msg);
		}
		return resultado;

	}

	@Override
	public Resultado buscarTodos(IEntidade entidade) {
		resultado = new Resultado();
		String nmClasse = entidade.getClass().getName();

		String msg = executarRegras(entidade, Fachada.CONSULTAR);

		if (msg == null) {
			GenericDAO dao = daosGeneric.get(nmClasse);
			try {
				resultado.setEntidades(dao.findAll());

			} catch (Exception e) {
				e.printStackTrace();
				resultado.setMsg("Nao foi possevel realizar a consulta!");

			}
			if (resultado.getEntidades() == null)
				resultado.setMsg("Não foi possível encontrar o cliente.");
		} else {
			resultado.setMsg(msg);
		}
		return resultado;

	}

	@Override
	public Resultado visualizar(IEntidade entidade) {
		resultado = new Resultado();
		resultado.setEntidades(new ArrayList<IEntidade>(1));
		resultado.getEntidades().add(entidade);
		return resultado;

	}

	private String executarRegras(IEntidade entidade, String operacao) {
		String nmClasse = entidade.getClass().getName();
		StringBuilder msg = new StringBuilder();

		Map<String, List<IStrategy>> regrasOperacao = rns.get(nmClasse);

		if (regrasOperacao != null) {
			List<IStrategy> regras = regrasOperacao.get(operacao);

			if (regras != null) {
				regras.forEach(r -> {
					String m = r.processar(entidade);
					if (m != null) {
						msg.append(m);
						msg.append("\n");
					}
				});

				// for (IStrategy s : regras) {
				// String m = s.processar(entidade);
				//
				// if (m != null) {
				// msg.append(m);
				// msg.append("\n");
				// }
				// }
			}

		}

		if (msg.length() > 0)
			return msg.toString();
		else
			return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see night.core.IFachada#buscarSubCategoria(night.dominio.IEntidade)
	 */
	@Override
	public Resultado buscarSubCategoria(IEntidade entidade) {
		resultado = new Resultado();
		String nmClasse = entidade.getClass().getName();
		String msg = null;

		if (msg == null) {
			GenericDAO dao = daosGeneric.get(nmClasse);
			try {
				if (entidade instanceof SubCategoria) {
					SubCategoria sub = (SubCategoria) entidade;
					SubCategoriaDAO subDao = (SubCategoriaDAO) dao;

					List<SubCategoria> subCategorias = subDao.findByIdCategoria(sub);
					List<IEntidade> entidades = new ArrayList<>();
					subCategorias.forEach(s -> {
						entidades.add(s);
					});
					resultado.setEntidades(entidades);
				}

			} catch (Exception e) {
				e.printStackTrace();
				resultado.setMsg("Nao foi possevel realizar a consulta!");

			}
			if (resultado.getEntidades() == null)
				resultado.setMsg("Não foi possível encontrar o cliente.");
		} else {
			resultado.setMsg(msg);
		}
		return resultado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see night.core.IFachada#vendasAno(night.dominio.IEntidade)
	 */
	@Override
	public Map<Integer, Double> vendasAno() {
		GenericDAO dao = daosGeneric.get("graficos");
		GraficosDAO graficosDao = (GraficosDAO) dao;
		return graficosDao.getVendasAno();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see night.core.IFachada#vendasAno(night.dominio.IEntidade)
	 */
	@Override
	public Map<Integer, Integer> clientesAno() {
		GenericDAO dao = daosGeneric.get("graficos");
		GraficosDAO graficosDao = (GraficosDAO) dao;
		return graficosDao.getClientesAno();

	}

	@Override
	public List<ChartData> getDetalhesMes(int mes) {
		GenericDAO dao = daosGeneric.get("graficos");
		GraficosDAO graficosDao = (GraficosDAO) dao;
		List<ChartData> listaChartData = graficosDao.getDetalhesMes(mes);
		return listaChartData;

	}

	public List<ChartData> getDetalhesSemana(int semana) {
		GenericDAO dao = daosGeneric.get("graficos");
		GraficosDAO graficosDao = (GraficosDAO) dao;
		List<ChartData> listaChartData = graficosDao.getDetalhesSemana(semana);
		return listaChartData;

	}
}
