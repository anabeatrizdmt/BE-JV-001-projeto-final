package projeto;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Estoque<T> {

    static Path fileName = Path.of("estoque_de_produtos");

    static List<List<String>> listaProdutos = new ArrayList<>();


    static List<String> dadosGravarFile = new ArrayList<>();
    static List<String> dadosLidosFile = new ArrayList<>();

    static void salvarEstoque() {
        try {

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
            dadosLidosFile = Files.readAllLines(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
