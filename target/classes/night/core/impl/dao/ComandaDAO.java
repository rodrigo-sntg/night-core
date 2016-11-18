package night.core.impl.dao;

import night.core.persistencia.GenericDAO;
import night.dominio.Comanda;

public interface ComandaDAO extends GenericDAO<Comanda, Long> {

	/*
	 * TODO : Regras específicas de negócios aqui Estes metodos serão
	 * sobrescritos se você gerar novamente essa interface você pode herdar
	 * dessa interface para mudar o retorno da dao factory para trazer uma nova
	 * implementação de buildClienteDAO()
	 */

	/**
	 * 
	 * @param cod
	 * @return
	 */
	public Comanda getByCodigoComanda(long cod);

}