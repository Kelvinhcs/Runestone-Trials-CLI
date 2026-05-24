/**
 * Centraliza toda a saída de texto do jogo, desacoplando lógica de apresentação.
 */
public final class Log {

    private Log() {}

    public static void rolagem(String rotulo, int faces, int resultado) {
        System.out.println(" « " + rotulo + ": 1d" + faces + " → [" + resultado + "] » ");
    }

    public static void dano(String breakdown) {
        System.out.println(" « Dano: " + breakdown + " » ");
    }

    public static void danoCausado(String nome, int dano, int pvRestantes) {
        System.out.println(" « " + nome + " sofre " + dano + " de dano | PV restantes: " + pvRestantes + " » ");
    }

    public static void acerto(String nome, int rolagem, int ca, boolean acertou) {
        System.out.println(" « " + nome + " rolou " + rolagem + " contra CA " + ca
                + " — " + (acertou ? "ACERTO!" : "ERROU!") + " » ");
    }

    public static void criticoAcerto() {
        System.out.println(" « ACERTO CRÍTICO! » ");
    }

    public static void criticoErro(String nome) {
        System.out.println(" « ERRO CRÍTICO! " + nome + " se feriu com o próprio golpe! » ");
    }

    public static void info(String mensagem) {
        System.out.println(mensagem);
    }
}