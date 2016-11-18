package night.core.impl.negocio;

import night.core.IConversionStrategy;
import night.dominio.IEntidade;
import night.dominio.Medida;

public class ConverteMiliGramasParaKilos implements IConversionStrategy {

	@Override
	public double processar(IEntidade entidade) {

		if (entidade != null) {
			Medida medida = (Medida) entidade;
			return medida.getMedidaPorUnidade() / (Math.pow(10, 6));
		} else {
			return 0;
		}
	}

}
