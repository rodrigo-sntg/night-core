import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.query.Query;

import night.core.impl.dao.ClienteDAO;
import night.core.impl.dao.ComandaDAO;
import night.core.impl.dao.ItemComandaDAO;
import night.core.impl.dao.ItemConsumoDAO;
import night.core.impl.dao.SubCategoriaDAO;
import night.core.persistencia.hibernate.HibernateDAOFactory;
import night.core.util.HibernateUtil;

public class TestExample {

	/**
	 * @param args
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		HibernateDAOFactory factory = new HibernateDAOFactory();
		ComandaDAO comDao = factory.buildComandaDAO();
		ClienteDAO cliDao = factory.buildClienteDAO();
		ItemConsumoDAO itemConsumoDao = factory.buildItemConsumoDAO();
		SubCategoriaDAO subCategoriaDao = factory.buildSubCategoriaDAO();
		ItemComandaDAO itemComandaDao = factory.buildItemComandaDAO();

		Session s = HibernateUtil.getSessionFactory().openSession();
		s.beginTransaction();

		Query sqlQuery = s.createSQLQuery("SELECT MONTH(DATA_CADASTRO), SUM(VALOR_TOTAL) "
				+ "FROM Comanda WHERE DATA_CADASTRO IS NOT NULL GROUP BY YEAR(DATA_CADASTRO), MONTH(DATA_CADASTRO)");

		Map<Integer, Double> mapa = new HashMap<Integer, Double>();
		List lista = sqlQuery.getResultList();
		for (Object object : lista) {
			Object[] listObject = (Object[]) object;
			mapa.put(Integer.valueOf(String.valueOf(listObject[0])), Double.valueOf(String.valueOf(listObject[1])));
		}

		mapa.get(0);

		// Cliente cli = new Cliente();
		//
		// ItemConsumo itemConsumo = new ItemConsumo();
		// SubCategoria subCategoria = new SubCategoria();
		// subCategoria.setNome("Chapelaria");
		// List<SubCategoria> subs = (List<SubCategoria>)
		// subCategoriaDao.findByExample(subCategoria, null);
		//
		// Criteria criteria =
		// HibernateUtil.getSessionFactory().getCurrentSession().createCriteria(SubCategoria.class);
		// criteria.add(Expression.eq("nome", "cerveja"));
		// subCategoria = (SubCategoria) criteria.list().get(0);
		//
		// ItemComanda itemComanda = new ItemComanda();
		//
		// Criteria criteriaConsumo =
		// HibernateUtil.getSessionFactory().getCurrentSession()
		// .createCriteria(ItemConsumo.class);
		// criteriaConsumo.add(Expression.eq("subCategoria", subCategoria));
		// List<ItemConsumo> itens = (List<ItemConsumo>) criteriaConsumo.list();
		//
		// List<ItemComanda> listaItens = new ArrayList<ItemComanda>();
		//
		// Criteria criteriaItemCOmanda =
		// HibernateUtil.getSessionFactory().getCurrentSession()
		// .createCriteria(ItemComanda.class);
		// for (ItemConsumo i : itens) {
		// }
		// criteriaItemCOmanda.add(Expression.in("item", itens));
		// listaItens = (List<ItemComanda>) criteriaItemCOmanda.list();

		// Comanda com = comDao.getByCodigoComanda(5);
		// for (ItemComanda i : com.getListaItemComanda()) {
		// for (ItemEstoqueConsumo item :
		// i.getItem().getListaItemEstoqueConsumo()) {
		// System.out.println(item.getItemEstoque().getId() + " - ");
		// System.out.println(item.getItemEstoque().getQtdEstoque());
		// item.getItemEstoque().setQtdEstoque(item.getItemEstoque().getQtdEstoque()
		// + 1);
		// System.out.println(item.getItemEstoque().getQtdEstoque());
		// }
		// }
		// comDao.update(com);
		//
		// for (ItemComanda i : com.getListaItemComanda()) {
		//
		// for (ItemEstoqueConsumo item :
		// i.getItem().getListaItemEstoqueConsumo()) {
		// System.out.println(item.getItemEstoque().getId() + " - ");
		// System.out.println(item.getItemEstoque().getQtdEstoque());
		// item.getItemEstoque().setQtdEstoque(item.getItemEstoque().getQtdEstoque()
		// + 1);
		// System.out.println(item.getItemEstoque().getQtdEstoque());
		// }
		// }
		// comDao.update(com);

		// cli.setCpf(Long.valueOf("37443472811"));
		// cli = cliDao.getByDocumento(cli.getCpf());
		// cli.setTelefone(123123123);
		// cliDao.update(cli);
		//
		// cli.setTelefone(989898989);
		// cliDao.update(cli);

		// Medida de = new Medida();
		// Medida para = new Medida();
		//
		// de.setMedidaPorUnidade(1);
		// de.setUnidadeMedida(Medida.UNIDADE_KILO);
		//
		// para.setMedidaPorUnidade(1);
		// para.setUnidadeMedida(Medida.UNIDADE_MILIGRAMA);
		//
		// MedidaUtils medidaUtils = new MedidaUtils();
		//
		// double convertido = medidaUtils.converte(de, para);
		// System.out.println(convertido);

		// Cozinha cozinha = new Cozinha();
		// ItemCozinha itemCozinha = new ItemCozinha();
		// ItemConsumo itemConsumo = new ItemConsumo();
		//
		// itemConsumo.setId(12);

		// Comanda com = new Comanda();
		// com.setNumeroComanda(5);
		// com = comDao.getByCodigoComanda(com.getNumeroComanda());
		// com.setStatus(true);
		// com.setCliente(cli);
		// com.setNumeroComanda(5);
		// ItemComanda item = new ItemComanda();
		// ItemConsumo itemConsumo = new ItemConsumo();
		// ItemConsumoDAO consumoDao = factory.buildItemConsumoDAO();
		// itemConsumo = consumoDao.getById(2L);
		// item.setQuantidade(2);
		// item.setItem(itemConsumo);
		//
		// com.getListaItemComanda().add(item);
		//
		// System.out.println(com.getValorTotal());

		// comDao.save(com);

		// CategoriaPrincipalDAO catDao = factory.buildCategoriaPrincipalDAO();
		//
		// // categoria Bebidas
		// CategoriaPrincipal cat = new CategoriaPrincipal();
		// cat.setDtCadastro(new Date());
		// cat.setNome("Bebidas");
		// catDao.save(cat);
		//
		// // categoria alimentos
		// CategoriaPrincipal cat2 = new CategoriaPrincipal();
		// cat2.setDtCadastro(new Date());
		// cat2.setNome("Alimentos");
		// catDao.save(cat2);
		//
		// // categoria serviços
		// CategoriaPrincipal cat3 = new CategoriaPrincipal();
		// cat3.setDtCadastro(new Date());
		// cat3.setNome("Serviços");
		// catDao.save(cat3);
		//
		// List<CategoriaPrincipal> lista = catDao.findAll();
		// for (CategoriaPrincipal categoriaPrincipal : lista) {
		// System.out.println(categoriaPrincipal.getNome());
		// }
		//
		// SubCategoriaDAO subDao = factory.buildSubCategoriaDAO();
		//
		// SubCategoria sub1 = new SubCategoria();
		// sub1.setCategoria(cat);
		// sub1.setDtCadastro(new Date());
		// sub1.setNome("Cerveja");
		// subDao.save(sub1);
		//
		// SubCategoria sub2 = new SubCategoria();
		// sub2.setCategoria(cat);
		// sub2.setDtCadastro(new Date());
		// sub2.setNome("Vodka");
		// subDao.save(sub2);
		//
		// SubCategoria sub = new SubCategoria();
		// sub.setCategoria(cat);
		// sub.setDtCadastro(new Date());
		// sub.setNome("Whisky");
		// subDao.save(sub);
		//
		// SubCategoria subM = new SubCategoria();
		// subM.setCategoria(cat);
		// subM.setDtCadastro(new Date());
		// subM.setNome("Conhaque");
		// subDao.save(subM);
		//
		// SubCategoria sub3 = new SubCategoria();
		// sub3.setCategoria(cat2);
		// sub3.setDtCadastro(new Date());
		// sub3.setNome("Porção de batata");
		// subDao.save(sub3);
		//
		// SubCategoria sub4 = new SubCategoria();
		// sub4.setCategoria(cat2);
		// sub4.setDtCadastro(new Date());
		// sub4.setNome("porção de calabresa");
		// subDao.save(sub4);
		//
		// SubCategoria sub5 = new SubCategoria();
		// sub5.setCategoria(cat3);
		// sub5.setDtCadastro(new Date());
		// sub5.setNome("Chapelaria");
		// subDao.save(sub5);

	}

}