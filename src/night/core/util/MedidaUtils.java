/**
 * 
 */
package night.core.util;

import java.util.HashMap;
import java.util.Map;

import night.core.IConversionStrategy;
import night.core.impl.negocio.ConverteGramasParaKilos;
import night.core.impl.negocio.ConverteGramasParaMiliGramas;
import night.core.impl.negocio.ConverteKiloParaMiliGramas;
import night.core.impl.negocio.ConverteKilosParaGramas;
import night.core.impl.negocio.ConverteLitrosParaMl;
import night.core.impl.negocio.ConverteMiliGramasParaGramas;
import night.core.impl.negocio.ConverteMiliGramasParaKilos;
import night.core.impl.negocio.ConverteMlParaLitros;
import night.core.impl.negocio.ConverteUnParaUn;
import night.dominio.Medida;

/**
 * @author rodrigo
 *
 */
public class MedidaUtils {

	private static Map<String, IConversionStrategy> mapa;

	public MedidaUtils() {
		super();

		setMapa(new HashMap<>());

		String stringGrparaKg = Medida.UNIDADE_GRAMA + "-" + Medida.UNIDADE_KILO;
		String stringGrParaMg = Medida.UNIDADE_GRAMA + "-" + Medida.UNIDADE_MILIGRAMA;
		String stringKgParaMg = Medida.UNIDADE_KILO + "-" + Medida.UNIDADE_MILIGRAMA;
		String stringKgParaGr = Medida.UNIDADE_KILO + "-" + Medida.UNIDADE_GRAMA;
		String stringLParaMl = Medida.UNIDADE_LITRO + "-" + Medida.UNIDADE_MILILITRO;
		String stringMgParaGr = Medida.UNIDADE_MILIGRAMA + "-" + Medida.UNIDADE_GRAMA;
		String stringMgParaKg = Medida.UNIDADE_MILIGRAMA + "-" + Medida.UNIDADE_KILO;
		String stringMlParaL = Medida.UNIDADE_MILILITRO + "-" + Medida.UNIDADE_LITRO;
		String stringUnidade = Medida.UNIDADE_UNIDADE;

		getMapa().put(stringGrparaKg, new ConverteGramasParaKilos());
		getMapa().put(stringGrParaMg, new ConverteGramasParaMiliGramas());
		getMapa().put(stringKgParaMg, new ConverteKiloParaMiliGramas());
		getMapa().put(stringKgParaGr, new ConverteKilosParaGramas());
		getMapa().put(stringLParaMl, new ConverteLitrosParaMl());
		getMapa().put(stringMgParaGr, new ConverteMiliGramasParaGramas());
		getMapa().put(stringMgParaKg, new ConverteMiliGramasParaKilos());
		getMapa().put(stringMlParaL, new ConverteMlParaLitros());
		getMapa().put(stringUnidade, new ConverteUnParaUn());

	}

	public double converte(Medida de, Medida para) {
		String chave = criaChaveMapa(de, para);
		IConversionStrategy strategy = mapa.get(chave);
		return strategy.processar(de);
	}

	private static String criaChaveMapa(Medida de, Medida para) {
		String key = de.getUnidadeMedida() + "-" + para.getUnidadeMedida();
		if (de.getUnidadeMedida().equals(Medida.UNIDADE_UNIDADE)
				|| para.getUnidadeMedida().equals(Medida.UNIDADE_UNIDADE)
				|| de.getUnidadeMedida().equals(para.getUnidadeMedida().equals(Medida.UNIDADE_UNIDADE)))
			key = Medida.UNIDADE_UNIDADE;

		return key;
	}

	@Deprecated
	private static double converte(String de, String para, double valor) {
		switch (de) {
		case "L":
			switch (para) {
			case "L":
				return valor;
			case "ml":
				converteLitrosParaMl(valor);
			case "kg":
				return valor;
			case "g":
				converteKilosParaGramas(valor);
			case "mg":
				return 0;
			case "un":
				return valor;
			}
			break;
		case "ml":
			switch (para) {
			case "L":
				return converteMlParaLitros(valor);
			case "ml":
				return valor;
			case "kg":
				return converteMlParaLitros(valor);
			case "g":
				return valor;
			case "mg":
				return converteGramasParaMiliGramas(valor);
			case "un":
				return valor;
			}
			break;
		case "kg":
			switch (para) {
			case "L":
				return valor;
			case "ml":
				return converteGramasParaKilos(valor);
			case "kg":
				return valor;
			case "g":
				return converteKilosParaGramas(valor);
			case "mg":
				return converteKiloParaMiliGramas(valor);
			case "un":
				return valor;
			}
			break;
		case "g":
			switch (para) {
			case "L":
				return converteGramasParaKilos(valor);
			case "ml":
				return valor;
			case "kg":
				return converteGramasParaKilos(valor);
			case "g":
				return valor;
			case "mg":
				return converteGramasParaMiliGramas(valor);
			case "un":
				return valor;
			}
			break;
		case "mg":
			switch (para) {
			case "L":
				return converteMiliGramasParaKilos(valor);
			case "ml":
				return converteMiliGramasParaGramas(valor);
			case "kg":
				return converteMiliGramasParaKilos(valor);
			case "g":
				return converteMiliGramasParaGramas(valor);
			case "mg":
				return valor;
			case "un":
				return valor;
			}
			break;
		case "un":
			switch (para) {
			case "L":
				return valor;
			case "ml":
				return valor;
			case "kg":
				return valor;
			case "g":
				return valor;
			case "mg":
				return valor;
			case "un":
				return valor;
			}
			break;
		}
		return 0;
	}

	@Deprecated
	private static double converteLitrosParaMl(double valor) {
		return valor * (Math.pow(10, 3));
	}

	@Deprecated
	private static double converteMlParaLitros(double valor) {
		return valor / (Math.pow(10, 3));
	}

	@Deprecated
	private static double converteKilosParaGramas(double valor) {
		return valor * (Math.pow(10, 3));
	}

	@Deprecated
	private static double converteGramasParaKilos(double valor) {
		return valor / (Math.pow(10, 3));
	}

	@Deprecated
	private static double converteGramasParaMiliGramas(double valor) {
		return valor * (Math.pow(10, 3));
	}

	@Deprecated
	private static double converteMiliGramasParaGramas(double valor) {
		return valor / (Math.pow(10, 3));
	}

	@Deprecated
	private static double converteKiloParaMiliGramas(double valor) {
		return valor * (Math.pow(10, 6));
	}

	private static double converteMiliGramasParaKilos(double valor) {
		return valor / (Math.pow(10, 6));
	}

	private static Map<String, IConversionStrategy> getMapa() {
		return mapa;
	}

	private void setMapa(Map<String, IConversionStrategy> mapa) {
		this.mapa = mapa;
	}

}
