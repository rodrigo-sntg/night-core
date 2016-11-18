package night.core.impl.negocio;

import java.util.Date;

import night.core.IStrategy;
import night.dominio.IEntidade;

public class ComplementarDtCadastro implements IStrategy {

	@SuppressWarnings("null")
	@Override
	public String processar(IEntidade entidade) {

		if (entidade != null) {
			Date data = new Date();
			entidade.setDtCadastro(data);
		} else {
			return "Entidade: " + entidade.getClass().getCanonicalName() + " nula!";
		}

		return null;
	}

}
