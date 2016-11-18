package night.core.impl.negocio;

import night.core.IStrategy;
import night.core.impl.dao.ClienteDAO;
import night.core.persistencia.hibernate.HibernateDAOFactory;
import night.dominio.Cliente;
import night.dominio.IEntidade;

public class ValidadorDocumentoDuplicado implements IStrategy {

	@Override
	public String processar(IEntidade entidade) {

		ClienteDAO cliDAO = new HibernateDAOFactory().buildClienteDAO();
		StringBuilder msg = new StringBuilder();
		if (entidade instanceof Cliente) {
			Cliente cliente = (Cliente) entidade;
			Cliente cpfDuplicado = cliDAO.getByDocumento(cliente.getCpf());
			Cliente rgDuplicado = cliDAO.getByDocumento(cliente.getRg());
			if (cpfDuplicado != null) {
				msg.append("CPF Duplicado! ");
			}
			if (rgDuplicado != null) {
				msg.append("RG Duplicado! ");
			}

		} else {
			msg.append("CPF não pode ser validado pois a entidade não é um cliente!");
		}

		if (msg.toString().length() == 0)
			return null;
		else
			return msg.toString();
	}

}
