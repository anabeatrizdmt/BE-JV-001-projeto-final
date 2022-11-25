package projeto;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Carrinho {
    List<Map<String, Object>> listaComprados = new ArrayList<>();

    Maquina maquina = new Maquina();

    void menu() {
        Estoque.lerEstoque();
        boolean continuar = true;

        while (continuar) {
            int opcao = maquina.pegarEscolhaMenu();
            switch (opcao) {
                case 1 -> criarProduto();
                case 2 -> editarProduto();
                case 3 -> excluirProduto();
                case 4 -> pesquisarProduto();
                case 5 -> comprarProdutos();
                case 6 -> continuar = false;
                default -> System.out.println("Opção Inválida.");
            }
        }
        Entrada.sc.close();
        Estoque.salvarEstoque();
        System.out.println("Saindo do sistema...");
    }

    public void criarProduto() {
        Map<String, Object> produto = maquina.pegarDadosProduto();

        final var semProdutosIguais = Estoque.listaProdutos.stream()
                .filter(p -> p.get("nome").equals(produto.get("nome")))
                .toList().isEmpty();

        if (semProdutosIguais) {
            Estoque.listaProdutos.add(produto);
            Estoque.salvarEstoque();
        } else {
            System.out.println("""
                       \u001B[33m                 
                    Já existe um produto com esse nome! Edite o produto existente ou entre com outro nome.
                        \u001B[0m                
                    """);
        }
    }

    public void editarProduto() {
        boolean pesquisaVazia = pesquisarProduto();
        if (!pesquisaVazia) {
            while (!pesquisaVazia) {
                System.out.println("\u001B[31mNenhum item encontrado! Tente novamente.\u001B[0m");
                pesquisaVazia = pesquisarProduto();
            }
        }
        if (Estoque.listaProdutos.size() == 0) {
            System.out.println("\u001B[31mLista de produtos vazia! Nada para editar.\u001B[0m");
        } else {
            int identificadorProduto = maquina.pegarIdentificadorProduto();

            if (identificadorProduto < 0 || identificadorProduto >= Estoque.listaProdutos.size()) {
                System.out.println("\u001B[31mO identificador selecionado é inválido!\u001B[0m");
            } else {
                final var produtoEditado = Estoque.listaProdutos.get(identificadorProduto);
                produtoEditado.put("preco", maquina.pegarPrecoProduto());
                produtoEditado.put("quantidade", maquina.pegarQuantidadeProduto());
                Estoque.listaProdutos.set(identificadorProduto, produtoEditado);
                Estoque.dadosGravarFile.clear();
                Estoque.salvarEstoque();
                System.out.println("Produto com identificador " + identificadorProduto + " editado com sucesso\n");
            }
        }
    }

    public void excluirProduto() {
        boolean pesquisaVazia = pesquisarProduto();
        if (!pesquisaVazia) {
            while (!pesquisaVazia) {
                System.out.println("\u001B[31mNenhum item encontrado! Tente novamente.\u001B[0m");
                pesquisaVazia = pesquisarProduto();
            }
        }
        if (Estoque.listaProdutos.size() == 0) {
            System.out.println("\u001B[31mLista de produtos vazia! Nada para excluir.\u001B[0m");
        } else {
            int identificadorProduto = maquina.pegarIdentificadorProduto();
            if (identificadorProduto >= 0 && identificadorProduto < Estoque.listaProdutos.size()) {
                Estoque.listaProdutos.remove(identificadorProduto);
                System.out.println("O item selecionado foi removido.\n");
                Estoque.dadosGravarFile.clear();
                Estoque.salvarEstoque();
            } else {
                System.out.println("\u001B[31mO identificador selecionado é inválido!\u001B[0m");
            }
        }
    }

    public boolean pesquisarProduto() {
        String termoPesquisa = maquina.definirPesquisaProdutos();
        return maquina.listarProdutos(termoPesquisa);
    }

    public void comprarProdutos() {
        boolean continuar = true;
        while (continuar) {
            final var produtoAdicionadoCarrinho = adicionarProdutoCarrinho();
            if (!produtoAdicionadoCarrinho || maquina.confirmarCompra()) {
                continuar = false;
            }
        }
        maquina.listarCarrinho(listaComprados);
    }

    public boolean adicionarProdutoCarrinho() {
        boolean pesquisaVazia = pesquisarProduto();
        if (!pesquisaVazia) {
            while (!pesquisaVazia) {
                System.out.println("\u001B[31mNenhum item encontrado! Tente novamente.\u001B[0m");
                pesquisaVazia = pesquisarProduto();
            }
        }
        if (Estoque.listaProdutos.size() == 0) {
            System.out.println("\u001B[31mLista de produtos vazia! Nada para comprar.\u001B[0m");
        } else {
            int identificadorProduto = maquina.pegarIdentificadorProduto();

            if (identificadorProduto < 0 || identificadorProduto >= Estoque.listaProdutos.size()) {
                System.out.println("\u001B[31mO identificador selecionado é inválido!\u001B[0m");
                return false;
            }

            final var produtoCompra = Estoque.listaProdutos.get(identificadorProduto);
            final var quantidadeCompra = maquina.pegarQuantidadeProduto();

            if (quantidadeCompra > (int) produtoCompra.get("quantidade")) {
                System.out.println("\n\u001B[31mA quantidade selecionada não está disponível.\n\u001B[0m");
                return false;
            }
            if (quantidadeCompra == 0) {
                System.out.println("\n\u001B[31mNão é possível adicionar a quantidade '0'.\n\u001B[0m");
                return false;
            }

            final var saldo = (int) produtoCompra.get("quantidade") - quantidadeCompra;
            produtoCompra.put("quantidade", saldo);
            Estoque.dadosGravarFile.clear();
            Estoque.salvarEstoque();

            Map<String, Object> produtoCarrinho = new LinkedHashMap<>();
            produtoCarrinho.put("nome", produtoCompra.get("nome"));
            produtoCarrinho.put("preco", produtoCompra.get("preco"));
            produtoCarrinho.put("quantidade", quantidadeCompra);

            final var naoTemNoCarrinho = listaComprados.stream()
                    .filter(m -> m.get("nome").equals(produtoCarrinho.get("nome")))
                    .toList().isEmpty();

            if (naoTemNoCarrinho) {
                listaComprados.add(produtoCarrinho);
                return true;
            }

            for (Map<String, Object> produtoLista : listaComprados) {
                if (produtoLista.get("nome").equals(produtoCarrinho.get("nome"))) {
                    produtoLista.put("quantidade", (int) produtoLista.get("quantidade") + quantidadeCompra);
                    break;
                }
            }
        }
        return true;
    }
}


