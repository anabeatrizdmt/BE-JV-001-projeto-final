package projeto;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Maquina {

    public int pegarEscolhaMenu() {
        System.out.println("""
                \u001B[36m
                ***********************************
                *  Loja do Santander-Code by Ada  *
                ***********************************
                
                \u001B[0m""");
        System.out.println("\u001B[36mDigite a opção desejada: \n");
        System.out.println("1 - Criar Produtos");
        System.out.println("2 - Editar Produtos");
        System.out.println("3 - Excluir Produtos");
        System.out.println("4 - Pesquisar Produtos");
        System.out.println("5 - Comprar Produtos");
        System.out.println("6 - Sair\u001B[0m");
        System.out.println();

        return Entrada.lerInt();
    }

    public int pegarIdentificadorProduto() {
        System.out.print("\nDigite o identificador do Produto: ");
        return Entrada.lerInt();
    }

    public Map<String, Object> pegarDadosProduto() {
        Map<String, Object> produto = new LinkedHashMap<>();

        produto.put("nome", pegarNomeProduto());
        produto.put("preco", pegarPrecoProduto());
        produto.put("quantidade", pegarQuantidadeProduto());

        return produto;
    }

    String pegarNomeProduto() {
        System.out.print("\nDigite o nome do produto: ");
        return Entrada.lerString();
    }

    Float pegarPrecoProduto() {
        System.out.print("\nDigite o preco do produto: ");
        return Entrada.lerFloat();
    }

    Integer pegarQuantidadeProduto() {
        System.out.print("\nDigite a quantidade do produto: ");
        return Entrada.lerInt();
    }

    public String definirPesquisaProdutos() {
        System.out.print("\nDigite o nome ou parte do nome do produto: (\"*\" para listar todos) ");
        return Entrada.lerString();
    }

    public boolean listarProdutos(String filtro) {

        if (Estoque.listaProdutos.size() == 0) {
            System.out.println("""
                    \u001B[33m
                    A lista de produtos está vazia.
                    \u001B[0m""");
            return false;
        } else {
            List<Boolean> nenhumProdutoEncontrado = retornarSeHaProdutoEncontrado(filtro);

            if (!nenhumProdutoEncontrado.contains(false)) {
                System.out.println(""" 
                        \u001B[33m
                        Nenhum produto encontrado.
                        \u001B[0m""");
                return false;
            }
        }
        imprimirProdutos(filtro);
        return true;
    }

    public void imprimirProdutos(String filtro) {

        List<Boolean> nenhumProdutoEncontrado = new ArrayList<>();
        for (int i = 0; i < Estoque.listaProdutos.size(); i++) {
            Map<String, Object> produto = Estoque.listaProdutos.get(i);
            final var nomeProduto = produto.get("nome").toString();
            if (filtro.equals("*")) {
                if (!nenhumProdutoEncontrado.contains(false)) {
                    imprimirCabecalho();
                }
                imprimirProduto(i, produto);
                nenhumProdutoEncontrado.add(false);
            } else if (nomeProduto.contains(filtro)) {
                if (!nenhumProdutoEncontrado.contains(false)) {
                    imprimirCabecalho();
                }
                imprimirProduto(i, produto);
                nenhumProdutoEncontrado.add(false);
            }
        }
    }

    public void imprimirCabecalho() {
        System.out.printf(
                "%n%n| %-13s | %-25s | %10s | %12s |%n",
                "Identificador", "nome", "preco", "quantidade"
        );
    }

    public void imprimirProduto(int i, Map<String, Object> produto) {
        System.out.printf(
                "| % 4d          | %-25s | R$%8.2f | %12d |%n",
                i, produto.get("nome"), (float) produto.get("preco"), (int) produto.get("quantidade")
        );
    }

    public List<Boolean> retornarSeHaProdutoEncontrado(String filtro) {
        List<Boolean> nenhumProdutoEncontrado = new ArrayList<>();
        for (int i = 0; i < Estoque.listaProdutos.size(); i++) {
            Map<String, Object> produto = Estoque.listaProdutos.get(i);
            final var nomeProduto = produto.get("nome").toString();
            if (filtro.equals("*")) {
                nenhumProdutoEncontrado.add(false);
            } else if (nomeProduto.contains(filtro)) {
                nenhumProdutoEncontrado.add(false);
            } else {
                nenhumProdutoEncontrado.add(true);
            }
        }
        return nenhumProdutoEncontrado;
    }

    public void listarCarrinho(List<Map<String, Object>> listaCarrinho) {

        if (listaCarrinho.size() == 0) {
            System.out.println("""
                    \u001B[31m
                    O carrinho está vazio.
                    \u001B[0m
                    """);
        } else {
            System.out.printf(
                    "%n%n| %-4s | %-25s | %10s | %12s | %13s |%n",
                    "item", "nome", "preco unit", "quantidade", "Preco total"
            );
            for (int i = 0; i < listaCarrinho.size(); i++) {
                Map<String, Object> produto = listaCarrinho.get(i);
                System.out.printf(
                        "|  %03d | %-25s | R$%8.2f | %12d | R$%11.2f |%n",
                        i, produto.get("nome"), (float) produto.get("preco"), (int) produto.get("quantidade"),
                        (float) produto.get("preco") * (int) produto.get("quantidade")
                );
            }
            final var valorTotalCarrinho = listaCarrinho.stream()
                    .map(p -> (float) p.get("preco") * (int) p.get("quantidade"))
                    .reduce(0f, Float::sum);
            System.out.printf("%nO valor total do carrinho é de R$ %.2f%n", valorTotalCarrinho);
            System.out.println("\n");
        }
    }

    public boolean confirmarCompra() {
        System.out.print("\nSe desejar finalizar a compra digite <s> ");
        String resposta = Entrada.lerString();
        return resposta.equals("s");
    }
}
