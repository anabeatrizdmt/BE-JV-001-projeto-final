package projeto;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Carrinho {
    List<Map<String, Object>> listaComprados = new ArrayList<>();

    Maquina maquina = new Maquina();

    void menu() {

        boolean continuar = true;

        while (continuar) {
            int opcao = maquina.pegarEscolhaMenu();
            switch (opcao) {
                case 1 -> criarProduto();
                case 2 -> editarProduto();
                case 3 -> excluirProduto();
                case 4 -> pesquisarProduto();
                case 5 -> comprarProduto();
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
        } else {
            System.out.println("""
                                        
                    Já existe um produto com esse nome! Edite o produto existente ou entre com outro nome.
                                        
                    """);
        }
    }

    public void editarProduto() {
        pesquisarProduto();
        int identificadorProduto = maquina.pegarIdentificadorProduto();

        if (identificadorProduto < 0 || identificadorProduto >= Estoque.listaProdutos.size()) {
            System.out.println("O identificador selecionado é inválido!\n");
        } else {
            final var produtoEditado = maquina.pegarDadosProduto();
            final var semProdutosIguais = Estoque.listaProdutos.stream()
                    .filter(p -> p.get("nome").equals(produtoEditado.get("nome")))
                    .toList().isEmpty();





            Estoque.listaProdutos.set(identificadorProduto, produtoEditado);

            System.out.println("Produto com identificador " + identificadorProduto + " editado com sucesso");
        }


    }

    public void excluirProduto() {
        pesquisarProduto();
        int identificadorProduto = maquina.pegarIdentificadorProduto();
        if (identificadorProduto >= 0 && identificadorProduto < Estoque.listaProdutos.size()) {
            Estoque.listaProdutos.remove(identificadorProduto);
            System.out.println("O item selecionado foi removido.\n");
        } else {
            System.out.println("O identificador selecionado é inválido!\n");
        }
    }

    public void pesquisarProduto() {

        String termoPesquisa = maquina.definirPesquisaProdutos();
        maquina.listarProdutos(termoPesquisa);
    }


    public void comprarProduto() {
        pesquisarProduto();
        int identificadorProduto = maquina.pegarIdentificadorProduto();

        if (identificadorProduto < 0 || identificadorProduto >= Estoque.listaProdutos.size()) {
            System.out.println("O identificador selecionado é inválido!\n");

        } else {
            final var produtoCompra = Estoque.listaProdutos.get(identificadorProduto);
            final var quantidadeCompra = maquina.pegarQuantidadeProduto();

            if (quantidadeCompra > (int) produtoCompra.get("quantidade")) {
                System.out.println("\nA quantidade selecionada não está disponível.\n");
            } else {
                final var saldo = (int) produtoCompra.get("quantidade") - quantidadeCompra;
                produtoCompra.put("quantidade", saldo);

                Map<String, Object> produtoCarrinho = new LinkedHashMap<>();
                produtoCarrinho.put("nome", produtoCompra.get("nome"));
                produtoCarrinho.put("preco", produtoCompra.get("preco"));
                produtoCarrinho.put("quantidade", quantidadeCompra);
                listaComprados.add(produtoCarrinho);

                maquina.listarCarrinho(listaComprados);
            }
        }
    }

}
