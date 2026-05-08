import java.util.Scanner;

/**
 * Menu principal do jogo (opções 1–4) e fluxo associado.
 */
public final class MenuPrincipal {

    enum OpcaoMenu {
        CRIAR_PERSONAGEM,
        VER_FICHA,
        ATACAR_INIMIGO,
        SAIR,
        INVALIDA
    }

    private MenuPrincipal() {
    }

    public static void executarLoop(Scanner scanner, EstadoJogo estado) {
        boolean rodando = true;
        while (rodando) {
            exibirMenu();
            OpcaoMenu escolha = lerOpcao(scanner);
            tratarEscolha(escolha, scanner, estado);
            if (escolha == OpcaoMenu.SAIR) {
                rodando = false;
            }
        }
    }

    private static void exibirMenu() {
        System.out.println();
        System.out.println("========== RUNESTONE TRIALS ==========");
        System.out.println("  1 - Criar personagem");
        System.out.println("  2 - Ver ficha");
        System.out.println("  3 - Atacar inimigo");
        System.out.println("  4 - Sair");
        System.out.println("======================================");
        System.out.print("Escolha uma opção: ");
    }

    private static OpcaoMenu lerOpcao(Scanner scanner) {
        if (!scanner.hasNextLine()) {
            return OpcaoMenu.SAIR;
        }
        String linha = scanner.nextLine().trim();
        return switch (linha) {
            case "1" -> OpcaoMenu.CRIAR_PERSONAGEM;
            case "2" -> OpcaoMenu.VER_FICHA;
            case "3" -> OpcaoMenu.ATACAR_INIMIGO;
            case "4" -> OpcaoMenu.SAIR;
            default -> OpcaoMenu.INVALIDA;
        };
    }

    private static void tratarEscolha(OpcaoMenu opcao, Scanner scanner, EstadoJogo estado) {
        System.out.println();
        switch (opcao) {
            case CRIAR_PERSONAGEM -> criarPersonagem(scanner, estado);
            case VER_FICHA -> verFicha(estado);
            case ATACAR_INIMIGO -> Batalha.executar(scanner, estado);
            case SAIR -> System.out.println("Até a próxima aventura!");
            case INVALIDA -> System.out.println("Opção inválida. Use 1, 2, 3 ou 4.");
        }
    }

    private static void criarPersonagem(Scanner scanner, EstadoJogo estado) {
        boolean criado = false;
        while (!criado){
            System.out.print("Nome do personagem: ");
            String nome = scanner.nextLine().trim();
            if (nome.isEmpty()) {
                System.out.println("O nome não pode ser vazio! Personagem não foi criado.");
                continue;
            }
            System.out.println("Estas são as classes disponíveis:");
            System.out.println("  1 - Guerreiro");
            System.out.println("  2 - Mago");
            System.out.println("  3 - Arqueiro");
            System.out.print("Escolha a sua classe: ");
            String c = scanner.hasNextLine() ? scanner.nextLine().trim() : "";
            Personagem jogador = switch (c) {
                case "1" -> new Guerreiro(nome);
                case "2" -> new Mago(nome);
                case "3" -> new Arqueiro(nome);
                default -> null;
            };
            if (jogador == null) {
                System.out.println("Classe inválida. Personagem não foi criado.");
            } else {
                estado.setJogador(jogador);
                System.out.println("Personagem criado: " + jogador.getNome() + ".");
                criado = true;
            }
        }
    }

    private static void verFicha(EstadoJogo estado) {
        if (!estado.temJogador()) {
            System.out.println("Nenhum personagem criado. Use a opção 1 primeiro.");
            return;
        }
        estado.getJogador().exibirFicha();
    }
}
