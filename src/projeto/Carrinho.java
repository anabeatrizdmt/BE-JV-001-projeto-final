package projeto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Carrinho {

    List<List<String>> listaComprados = new ArrayList<>();

    void menu() {

        Maquina maquina = new Maquina();
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
    }

    public void editarProduto() {
    }

    public void excluirProduto() {
    }

    public void pesquisarProduto() {
    }

    public void comprarProduto() {
    }

}
