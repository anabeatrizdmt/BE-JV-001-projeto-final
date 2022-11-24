package projeto;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Maquina {


    public int pegarEscolhaMenu() {
        IntStream.range(0, 2).forEach(n -> System.out.println());
        System.out.println("Digite a opção desejada: ");
        System.out.println("1 - Criar Produtos");
        System.out.println("2 - Editar Produtos");
        System.out.println("3 - Excluir Produtos");
        System.out.println("4 - Pesquisar Produtos");
        System.out.println("5 - Comprar Produtos");
        System.out.println("6 - Sair");
        System.out.println("\n");

        return Entrada.lerInt();
    }

    public int pegarIdentificadorProduto() {
        System.out.println("Digite o identificador do Produto: ");
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
        System.out.println("Digite o nome do produto: ");
        return Entrada.lerString();
    }

    Float pegarPrecoProduto() {
        System.out.println("Digite o preco do produto: ");
        return Entrada.lerFloat();
    }

    Integer pegarQuantidadeProduto() {
        System.out.println("Digite a quantidade do produto: ");
        return Entrada.lerInt();
    }

    public String definirPesquisaProdutos() {
        System.out.println("Digite o nome ou parte do nome do produto: (\"*\" para listar todos)");
        return Entrada.lerString();
    }

    public void listarProdutos(String filtro) {

        if (Estoque.listaProdutos.size() == 0) {
            System.out.println("""
                                        
                    A lista de produtos está vazia.
                                        
                    """);
        } else {
            System.out.printf(
                    "%n%n| %-13s | %-25s | %10s | %12s |%n",
                    "Identificador", "nome", "preco", "quantidade"
            );
            for (int i = 0; i < Estoque.listaProdutos.size(); i++) {
                Map<String, Object> produto = Estoque.listaProdutos.get(i);
                final var nomeProduto = produto.get("nome").toString();
                if (filtro.equals("*")) {
                    System.out.printf(
                            "| % 4d          | %-25s | R$%8.2f | %12d |%n",
                            i, produto.get("nome"), (float) produto.get("preco"), (int) produto.get("quantidade")
                    );
                } else if (nomeProduto.contains(filtro)) {
                    System.out.printf(
                            "| % 4d          | %-25s | R$%8.2f | %12d |%n",
                            i, produto.get("nome"), (float) produto.get("preco"), (int) produto.get("quantidade")
                    );
                }
            }
            System.out.println("\n");
        }
    }

    public void listarCarrinho(List<Map<String,Object>> listaCarrinho) {

        if (listaCarrinho.size() == 0) {
            System.out.println("""
                                        
                    O carrinho está vazio.
                                        
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
            System.out.printf("%nO valor toral do carrinho é de R$ %.2f%n", valorTotalCarrinho);
            System.out.println("\n");
        }
    }


}
