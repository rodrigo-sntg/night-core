package night.core.impl.negocio;

import java.util.Date;

import night.core.IStrategy;
import night.dominio.Cliente;
import night.dominio.IEntidade;

public class ValidadorDadosObrigatoriosCliente implements IStrategy {

	@Override
	public String processar(IEntidade entidade) {

		if (entidade instanceof Cliente) {
			Cliente cliente = (Cliente) entidade;

			String nome = cliente.getNome();
			long cpf = cliente.getCpf();
			long rg = cliente.getRg();
			Date dtNascimento = cliente.getDtNascimento();
			String cidade = cliente.getEndereco().getCidade();
			String estado = cliente.getEndereco().getEstado();
			String rua = cliente.getEndereco().getRua();

			if (nome == null || cidade == null || rua == null || estado == null || (rg == 0 && cpf == 0)
					|| dtNascimento == null) {
				return "Nome, 	endereço e rg ou cpf são de preenchimento obrigatório!";
			}

			if (nome.trim().equals("") || cidade.trim().equals("") || rua.trim().equals("")
					|| estado.trim().equals("")) {
				return "Nome, e endereço são de preenchimento obrigatórios!\n";
			}

		} else {
			return "Deve ser registrado um cliente!\n";
		}

		return null;
	}

}
