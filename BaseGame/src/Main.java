import java.util.Scanner;

/**
 * Ponto de entrada — delega o menu e a batalha a outras classes.
 */
public class Main {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            EstadoJogo estado = new EstadoJogo();
            MenuPrincipal.executarLoop(scanner, estado);
        }
    }
}
/*TODO:
*       randomizar ordem de ataque com base nos personagens
*       implementar ataques específicos da classe
*       implementar sistema de mana para os ataques específicos
*       TALVEZ adicionar um sleep para que fique mais legal acompanhar os ataques
* */