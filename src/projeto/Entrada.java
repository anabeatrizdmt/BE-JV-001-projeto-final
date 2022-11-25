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
                System.out.println("\u001B[31mO valor não pode ser vazio ou somente espaços.\u001B[0m");
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
                if (retorno < 0) {
                    throw new NumberFormatException();
                }
                repetir = false;
            } catch (NumberFormatException | NullPointerException ex) {
                System.out.println("\n\u001B[31mO valor entrado não é válido\u001B[0m");
                System.out.print("Entre com valor numérico positivo: ");
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
                if (retorno < 0) {
                    throw new NumberFormatException();
                }
                repetir = false;
            } catch (NumberFormatException | NullPointerException ex) {
                System.out.println("\u001B[31mO valor entrado não é válido!\u001B[0m");
                System.out.print("Entre com um número inteiro positivo: ");

            }
        }
        return retorno;
    }
}







