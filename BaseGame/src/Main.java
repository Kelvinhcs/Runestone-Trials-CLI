import java.util.Scanner;

/**
 * Ponto de entrada do Runestone Trials CLI — menu inicial.
 */
public class Main {

    private enum OpcaoMenu {
        SAIR,
        NOVO_PERSONAGEM,
        FICHA_ATUAL,
        TRIAL_CHAMBERS,
        INVALIDA
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean rodando = true;
            while (rodando) {
                exibirMenu();
                OpcaoMenu escolha = lerOpcao(scanner);
                tratarEscolha(escolha);
                if (escolha == OpcaoMenu.SAIR) {
                    rodando = false;
                } else if (escolha != OpcaoMenu.INVALIDA) {
                    aguardarEnter(scanner);
                }
            }
        }
    }

    private static void exibirMenu() {
        System.out.println();
        System.out.println("========== RUNESTONE TRIALS ==========");
        System.out.println("  1 - Criar novo personagem");
        System.out.println("  2 - Mostrar ficha atual");
        System.out.println("  3 - Entrar nas Trial Chambers");
        System.out.println("  0 - Sair");
        System.out.println("======================================");
        System.out.print("Escolha uma opção: ");
    }

    private static OpcaoMenu lerOpcao(Scanner scanner) {
        if (!scanner.hasNextLine()) {
            return OpcaoMenu.SAIR;
        }
        String linha = scanner.nextLine().trim();
        return switch (linha) {
            case "0" -> OpcaoMenu.SAIR;
            case "1" -> OpcaoMenu.NOVO_PERSONAGEM;
            case "2" -> OpcaoMenu.FICHA_ATUAL;
            case "3" -> OpcaoMenu.TRIAL_CHAMBERS;
            default -> OpcaoMenu.INVALIDA;
        };
    }

    private static void tratarEscolha(OpcaoMenu opcao) {
        System.out.println();
        switch (opcao) {
            case SAIR -> System.out.println("Opção registrada: SAIR — Até logo!");
            case NOVO_PERSONAGEM ->
                    System.out.println("Opção registrada: CRIAR NOVO PERSONAGEM (em breve).");
            case FICHA_ATUAL ->
                    System.out.println("Opção registrada: MOSTRAR FICHA ATUAL (em breve).");
            case TRIAL_CHAMBERS ->
                    System.out.println("Opção registrada: ENTRAR NAS TRIAL CHAMBERS (em breve).");
            case INVALIDA ->
                    System.out.println("Opção inválida. Use 0, 1, 2 ou 3.");
        }
    }

    private static void aguardarEnter(Scanner scanner) {
        System.out.print("\nPressione ENTER para voltar ao menu...");
        scanner.nextLine();
    }
}
