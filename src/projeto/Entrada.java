package projeto;

import java.util.Scanner;

public class Entrada {

    static Scanner sc = new Scanner(System.in);

    public static String lerString() {
        boolean repetir = true;
        String retorno = "";

        while (repetir) {
            retorno = sc.nextLine().trim().toLowerCase();
            if (retorno.equals("")) {
                System.out.println("""
                        O valor não pode ser vazio ou somente espaços.
                        """);
            } else {
                repetir = false;
            }
        }
        return retorno;
    }

    public static Float lerFloat() {
        boolean repetir = true;
        float retorno = 0f;

        while (repetir) {

            String valor = sc.nextLine();
            try {
                retorno = Float.parseFloat(valor);
                repetir = false;
            } catch (NumberFormatException | NullPointerException ex) {
                System.out.println("""
                        O valor entrado não é válido, entre com valor numérico.
                        """);
            }
        }
        return retorno;
    }

    public static Integer lerInt() {
        boolean repetir = true;
        int retorno = 0;

        while (repetir) {

            String valor = sc.nextLine();
            try {
                retorno = Integer.parseInt(valor);
                repetir = false;
            } catch (NumberFormatException | NullPointerException ex) {
                System.out.println("""
                        O valor entrado não é válido, entre com um número inteiro.
                        """);
            }
        }
        return retorno;
    }
}







