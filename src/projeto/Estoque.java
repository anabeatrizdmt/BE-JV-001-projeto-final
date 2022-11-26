package projeto;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Estoque {

    static Path fileName = Path.of("estoque_de_produtos.txt");

    static List<Map<String, Object>> listaProdutos = new ArrayList<>();


    static List<String> dadosGravarFile = new ArrayList<>();
    static List<String> dadosLidosFile = new ArrayList<>();

    static void salvarEstoque() {
        try {
            dadosGravarFile.clear();
            concatenarDadosArquivo();
            Files.write(fileName, dadosGravarFile);

        } catch (
                FileAlreadyExistsException ex) {
            System.out.println("File já existe");
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void lerEstoque() {
        try {
            verificarArquivo();
            dadosLidosFile = Files.readAllLines(fileName);
            obterProdutosArquivo();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void verificarArquivo() {
        if (!Files.exists(fileName)){
            try{
                Files.createFile(fileName);
            } catch (IOException exception){
                exception.printStackTrace();
            }
        }
    }

    static void obterProdutosArquivo(){

        for (String linhaFile : dadosLidosFile){

            try{
                String[] produtoLido = linhaFile.split(";");
                String nome = produtoLido[0];
                Integer quantidade = Integer.valueOf(produtoLido[1]);
                Float preco = Float.valueOf(produtoLido[2]);

                Map<String, Object> produto = new LinkedHashMap<>();
                produto.put("nome", nome);
                produto.put("quantidade", quantidade);
                produto.put("preco", preco);
                listaProdutos.add(produto);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException exception){
                System.out.println("\u001B[31mArquivo de dados corrompido\u001B[0m");
                System.out.println("\u001B[33mCorrija o arquivo ou delete-o, e rode o programa novamente\u001B[0m");
                System.exit(0);
            }
        }
    }

    static void concatenarDadosArquivo(){
        for (Map<String, Object> produto : listaProdutos){
            String produtoLinha = produto.get("nome") + ";" +
                    produto.get("quantidade") + ";" + produto.get("preco");
            dadosGravarFile.add(produtoLinha);
        }
    }

    static int pegarQuantidadeDeProdutos(){
        return listaProdutos.size();
    }
}
