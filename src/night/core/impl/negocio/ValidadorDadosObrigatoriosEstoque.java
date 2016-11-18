package night.core.impl.negocio;

import night.core.IStrategy;
import night.dominio.IEntidade;
import night.dominio.ItemEstoque;

public class ValidadorDadosObrigatoriosEstoque implements IStrategy {

	@Override
	public String processar(IEntidade entidade) {

		if (entidade instanceof ItemEstoque) {
			ItemEstoque estoque = (ItemEstoque) entidade;

			String nome = estoque.getNomeItem();
			String descricao = estoque.getDescricao();
			String categoria = estoque.getSubCategoria().getCategoria().getNome();
			String subCategoria = estoque.getSubCategoria().getNome();
			double medidaPorUnidade = estoque.getMedida().getMedidaPorUnidade();
			String unidadeMedida = estoque.getMedida().getUnidadeMedida();
			long qtdCritica = estoque.getQtdCritica();

			if (nome == null || descricao == null || categoria == null || subCategoria == null || unidadeMedida == null
					|| (medidaPorUnidade == 0 && qtdCritica == 0)) {
				return "Os dados Nome, Descrição, Categoria, subCategoria, qtdPorUnidade, Un de medida e "
						+ "qtd Critica são de preenchimento obrigatório!";
			}

			if (nome.trim().equals("") || descricao.trim().equals("") || categoria.trim().equals("")
					|| subCategoria.trim().equals("") || unidadeMedida.trim().equals("")) {
				return "Os dados Nome, Descrição, Categoria, subCategoria, qtdPorUnidade, Un de medida e "
						+ "qtd Critica são de preenchimento obrigatório!";
			}

		} else {
			return "Deve ser registrado um ItemEstoque!\n";
		}

		return null;
	}

}
