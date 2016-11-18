package night.core.impl.negocio;

import night.core.IStrategy;
import night.dominio.Cliente;
import night.dominio.IEntidade;

public class ValidadorDadosObrigatoriosPesquisaCliente implements IStrategy {

	@Override
	public String processar(IEntidade entidade) {

		if (entidade instanceof Cliente) {
			Cliente cliente = (Cliente) entidade;

			long cpf = cliente.getCpf();
			long rg = cliente.getRg();

			if ((rg == 0 && cpf == 0)) {
				return "RG ou CPF são obrigatórios para efetuar pesquisa!";
			}

		} else {
			return "Deve ser registrado um cliente!\n";
		}

		return null;
	}

}
