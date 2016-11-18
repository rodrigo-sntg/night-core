package night.core.impl.dao;

import java.util.List;

import night.core.persistencia.GenericDAO;
import night.dominio.SubCategoria;

public interface SubCategoriaDAO extends GenericDAO<SubCategoria, Long> {

	/*
	 * TODO : Regras específicas de negócios aqui Estes metodos serão
	 * sobrescritos se você gerar novamente essa interface você pode herdar
	 * dessa interface para mudar o retorno da dao factory para trazer uma nova
	 * implementação de buildClienteDAO()
	 */

	public List<SubCategoria> findByIdCategoria(SubCategoria entidade);

}