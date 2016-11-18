
package night.core.aplicacao;

import java.util.List;

import night.dominio.IEntidade;

public class Resultado extends EntidadeAplicacao {

	private String msg;
	private List<IEntidade> entidades;

	/**
	 * Método de recuperação do campo msg
	 *
	 * @return valor do campo msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * Valor de msg atribuído a msg
	 *
	 * @param msg
	 *            Atributo da Classe
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * Método de recuperação do campo entidades
	 *
	 * @return valor do campo entidades
	 */
	public List<IEntidade> getEntidades() {
		return entidades;
	}

	/**
	 * Valor de entidades atribuído a entidades
	 *
	 * @param entidades
	 *            Atributo da Classe
	 */
	public void setEntidades(List<IEntidade> entidades) {
		this.entidades = entidades;
	}

}
