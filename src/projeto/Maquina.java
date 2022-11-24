package projeto;

import monitoria01.crud.model.Aluno;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Maquina {

    Scanner sc = new Scanner(System.in);

    public String pegarEscolhaMenu() {
        System.out.println("Digite a opção desejada: ");
        System.out.println("1 - Criar Produtos");
        System.out.println("2 - Editar Produtos");
        System.out.println("3 - Excluir Produtos");
        System.out.println("4 - Pesquisar Produtos");
        System.out.println("5 - Comprar Produtos");
        System.out.println("6 - Sair");

        return sc.next();
    }

    public String pegarIdentificadoProduto() {
        System.out.println("Digite o identificador do Aluno: ");
        return sc.next();
    }

    public Map<String, Object> pegarDadosProduto() {

        Map<String, Object> produto = new HashMap<>();

        System.out.println("Digite o nome do produto: ");
        produto.put("nome", sc.next());

        System.out.println("Digite o preco do produto: ");
        produto.put("preco", sc.next());

        System.out.println("Digite a quantidade do produto: ");
        produto.put("quantidade", sc.nextDouble());

        return produto;
    }

    public void listarProdutos() {


    }




}
