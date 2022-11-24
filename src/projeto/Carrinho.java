package projeto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Carrinho {

    List<Map<String, Object>> listaComprados = new ArrayList<>();

    Maquina maquina = new Maquina();

    void menu() {

        boolean continuar = true;

        while (continuar) {
            String opcao = maquina.pegarEscolhaMenu();
            switch (opcao) {
                case "1" -> criarProduto();
                case "2" -> editarProduto();
                case "3" -> excluirProduto();
                case "4" -> pesquisarProduto();
                case "5" -> comprarProduto();
                case "6" -> continuar = false;
                default -> System.out.println("Opção Inválida.");
            }
            Estoque.salvarEstoque();
            System.out.println("Saindo do sistema...");
        }


    }

    public void criarProduto() {
        Map<String, Object> produto = maquina.pegarDadosProduto();
        Estoque.listaProdutos.add(produto);
    }

    public void editarProduto() {
    }

    public void excluirProduto() {
    }

    public void pesquisarProduto() {
        String termoPesquisa = maquina.definirPesquisaProdutos();

        for (int i = 0; i < Estoque.listaProdutos.size(); i++) {
            Map<String, Object> produto = Estoque.listaProdutos.get(i);
            final var nomeProduto = produto.get("nome").toString();
            if (nomeProduto.contains(termoPesquisa)) {
                System.out.println(i + "-" + produto);
            }
        }


    }


    public void listarProdutos() {
        for (int i = 0; i < Estoque.listaProdutos.size(); i++) {
            Map<String, Object> produto = Estoque.listaProdutos.get(i);
            System.out.println(i + "-" + produto);
        }
    }

    public void comprarProduto() {
    }


}
