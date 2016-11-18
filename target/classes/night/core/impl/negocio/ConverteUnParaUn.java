package night.core.impl.negocio;

import night.core.IConversionStrategy;
import night.dominio.IEntidade;
import night.dominio.Medida;

public class ConverteUnParaUn implements IConversionStrategy {

	@Override
	public double processar(IEntidade entidade) {

		if (entidade != null) {
			Medida medida = (Medida) entidade;
			return medida.getMedidaPorUnidade();
		} else {
			return 0;
		}
	}

}
