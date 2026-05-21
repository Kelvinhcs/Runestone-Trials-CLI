import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Menu principal do jogo (opções 1–4) e fluxo associado.
 */
public final class MenuPrincipal {
    enum OpcaoMenu {
        CRIAR_PERSONAGEM,
        PERSONAGEM_ALEATORIO,
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
        System.out.println("  2 - Personagem aleatório");
        System.out.println("  3 - Ver ficha");
        System.out.println("  4 - Atacar inimigo");
        System.out.println("  5 - Sair");
        System.out.println("======================================");
        System.out.print("Escolha uma opção: ");
    }

    private static OpcaoMenu lerOpcao(Scanner scanner) {
        if (!scanner.hasNextLine()) {
            return OpcaoMenu.SAIR;
        }
        String linha = scanner.nextLine().trim();
        try {
            TimeUnit.MILLISECONDS.sleep(600);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return switch (linha) {
            case "1" -> OpcaoMenu.CRIAR_PERSONAGEM;
            case "2" -> OpcaoMenu.PERSONAGEM_ALEATORIO;
            case "3" -> OpcaoMenu.VER_FICHA;
            case "4" -> OpcaoMenu.ATACAR_INIMIGO;
            case "5" -> OpcaoMenu.SAIR;
            default -> OpcaoMenu.INVALIDA;
        };
    }

    private static void tratarEscolha(OpcaoMenu opcao, Scanner scanner, EstadoJogo estado) {
        System.out.println();
        switch (opcao) {
            case CRIAR_PERSONAGEM -> criarPersonagem(scanner, estado);
            case PERSONAGEM_ALEATORIO -> criarPersonagemAleatorio(scanner, estado);
            case VER_FICHA -> verFicha(estado);
            case ATACAR_INIMIGO -> Batalha.executar(scanner, estado);
            case SAIR -> System.out.println("Até a próxima aventura!");
            case INVALIDA -> System.out.println("Opção inválida. Use 1, 2, 3 ou 4.");
        }
    }

    private static void criarPersonagem(Scanner scanner, EstadoJogo estado) {
        boolean criado = false;
        while (!criado) {
            System.out.print("Nome do personagem: ");
            String nome = scanner.nextLine().trim();
            if (nome.isEmpty()) {
                System.out.println("O nome não pode ser vazio!");
                continue;
            }
            Personagem jogador = selecionarClasse(scanner, nome);
            if (jogador == null) continue;

            Armas arma = selecionarArma(scanner);
            if (arma != null) jogador.setArma(arma);

            Armaduras armadura = selecionarArmadura(scanner);
            if (armadura != null) jogador.setArmadura(armadura);

            Armaduras escudo = selecionarEscudo(scanner);
            if (escudo != null) jogador.setEscudo(escudo);

            estado.setJogador(jogador);
            System.out.println("Personagem criado: " + jogador.getNome() + ".");
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            jogador.exibirFicha();
            try {
                TimeUnit.MILLISECONDS.sleep(1500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            criado = true;
        }
    }

    private static Personagem selecionarClasse(Scanner scanner, String nome) {
        System.out.println("Classes disponíveis:");
        System.out.println("  1 - Guerreiro (120 PV | Ataque: 8 | CA: 10)");
        System.out.println("  2 - Mago      ( 60 PV | Ataque: 12 | CA: 12)");
        System.out.println("  3 - Arqueiro  ( 85 PV | Ataque: 10 | CA: 14)");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.print("Escolha a sua classe: ");

        String op = scanner.hasNextLine() ? scanner.nextLine().trim() : "";
        Personagem jogador = switch (op) {
            case "1" -> new Guerreiro(nome);
            case "2" -> new Mago(nome);
            case "3" -> new Arqueiro(nome);
            default  -> null;
        };

        if (jogador == null) System.out.println("Classe inválida. Tente novamente.");
        return jogador;
    }

    private static Armas selecionarArma(Scanner scanner) {
        Armas[] armas = Armas.values();
        System.out.println();
        System.out.println("Armas disponíveis:");
        System.out.println("  0 - Nenhuma");
        for (int i = 0; i < armas.length; i++) {
            System.out.printf("  %-2d- %s%n", (i + 1), armas[i]);
        }
        System.out.print("Escolha uma arma: ");

        int escolha = lerInteiro(scanner);
        if (escolha == 0) return null;
        if (escolha < 1 || escolha > armas.length) {
            System.out.println("Opção inválida. Nenhuma arma equipada.");
            return null;
        }
        return armas[escolha - 1];
    }

    private static Armaduras selecionarArmadura(Scanner scanner) {
        Armaduras[] armaduras = Arrays.stream(Armaduras.values())
                .filter(a -> a.getType() != Armaduras.ArmorType.SHIELD
                        && a.getType() != Armaduras.ArmorType.UNARMORED)
                .toArray(Armaduras[]::new);

        System.out.println();
        System.out.println("Armaduras disponíveis:");
        System.out.println("  0 - Nenhuma");
        for (int i = 0; i < armaduras.length; i++) {
            System.out.printf("  %-2d- %s%n", (i + 1), armaduras[i]);
        }
        System.out.print("Escolha uma armadura: ");

        int escolha = lerInteiro(scanner);
        if (escolha == 0) return null;
        if (escolha < 1 || escolha > armaduras.length) {
            System.out.println("Opção inválida. Nenhuma armadura equipada.");
            return null;
        }
        return armaduras[escolha - 1];
    }

    private static Armaduras selecionarEscudo(Scanner scanner) {
        Armaduras[] escudos = Arrays.stream(Armaduras.values())
                .filter(a -> a.getType() == Armaduras.ArmorType.SHIELD)
                .toArray(Armaduras[]::new);

        System.out.println();
        System.out.println("Escudos disponíveis:");
        System.out.println("  0 - Nenhum");
        for (int i = 0; i < escudos.length; i++) {
            System.out.printf("  %-2d- %s%n", (i + 1), escudos[i]);
        }
        System.out.print("Escolha um escudo: ");

        int escolha = lerInteiro(scanner);
        if (escolha == 0) return null;
        if (escolha < 1 || escolha > escudos.length) {
            System.out.println("Opção inválida. Nenhum escudo equipado.");
            return null;
        }
        return escudos[escolha - 1];
    }

    /** Lê um inteiro do scanner, retornando -1 em caso de entrada inválida. */
    private static int lerInteiro(Scanner scanner) {
        String linha = scanner.hasNextLine() ? scanner.nextLine().trim() : "";
        try {
            return Integer.parseInt(linha);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void criarPersonagemAleatorio(Scanner scanner, EstadoJogo estado) {
        System.out.print("Nome do personagem: ");
        String nome = scanner.nextLine().trim();
        if (nome.isEmpty()) {
            System.out.println("O nome não pode ser vazio!");
            return;
        }
        Random rng = new Random();
        Personagem jogador = switch (rng.nextInt(3)) {
            case 0 -> new Guerreiro(nome);
            case 1 -> new Mago(nome);
            default -> new Arqueiro(nome);
        };
        // Arma aleatória compatível com a classe
        Armas[] todasArmas = Armas.values();
        Armas arma = todasArmas[rng.nextInt(todasArmas.length)];
        jogador.setArma(arma);
        // Armadura aleatória (exceto escudos — slot separado)
        Armaduras[] todasArmaduras = Arrays.stream(Armaduras.values())
                .filter(a -> a.getType() != Armaduras.ArmorType.UNARMORED
                        && a.getType() != Armaduras.ArmorType.SHIELD)
                .toArray(Armaduras[]::new);
        // Escudo — sorteio separado, incluindo chance de não ter
        Armaduras[] opcoesEscudo = Arrays.stream(Armaduras.values())
                .filter(a -> a.getType() == Armaduras.ArmorType.SHIELD)
                .toArray(Armaduras[]::new);
        // 50% de chance de receber um escudo
                if (rng.nextBoolean()) {
                    Armaduras escudo = opcoesEscudo[rng.nextInt(opcoesEscudo.length)];
                    jogador.setEscudo(escudo);
                }

        Armaduras armadura = todasArmaduras[rng.nextInt(todasArmaduras.length)];
        jogador.setArmadura(armadura);
        estado.setJogador(jogador);
        System.out.println();
        System.out.println("Personagem gerado aleatoriamente!");
        jogador.exibirFicha();
    }

    private static void verFicha(EstadoJogo estado) {
        if (!estado.temJogador()) {
            System.out.println("Nenhum personagem criado. Use a opção 1 primeiro.");
            return;
        }
        estado.getJogador().exibirFicha();
    }
}
