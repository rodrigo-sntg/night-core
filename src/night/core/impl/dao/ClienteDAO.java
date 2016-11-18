package night.core.impl.dao;

import java.util.List;

import night.core.persistencia.GenericDAO;
import night.dominio.Cliente;

public interface ClienteDAO extends GenericDAO<Cliente, Long> {

	/*
	 * TODO : Regras específicas de negócios aqui Estes metodos serão
	 * sobrescritos se você gerar novamente essa interface você pode herdar
	 * dessa interface para mudar o retorno da dao factory para trazer uma nova
	 * implementação de buildClienteDAO()
	 */

	/**
	 * Find Contact by name
	 */
	public List<Cliente> findByStatus(String status);

	/**
	 * pesquisa o cliente pelo documento passado, podendo ser um rg ou cpf
	 * 
	 * @param doc
	 *            - long rg ou cpf sem formatação
	 * @return
	 */
	public Cliente getByDocumento(long doc);

}