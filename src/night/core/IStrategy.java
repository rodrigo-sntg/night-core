package night.core;

import night.dominio.IEntidade;

public interface IStrategy {

	public String processar(IEntidade entidade);

}
