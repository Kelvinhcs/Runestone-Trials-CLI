import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Menu principal do jogo (opções 1–5) e fluxo associado.
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

    private MenuPrincipal() {}

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
        Log.info("");
        Log.info("========== RUNESTONE TRIALS ==========");
        Log.info("  1 - Criar personagem");
        Log.info("  2 - Personagem aleatório");
        Log.info("  3 - Ver ficha");
        Log.info("  4 - Atacar inimigo");
        Log.info("  5 - Sair");
        Log.info("======================================");
        System.out.print("Escolha uma opção: ");
    }

    private static OpcaoMenu lerOpcao(Scanner scanner) {
        if (!scanner.hasNextLine()) return OpcaoMenu.SAIR;
        String linha = scanner.nextLine().trim();
        return switch (linha) {
            case "1" -> OpcaoMenu.CRIAR_PERSONAGEM;
            case "2" -> OpcaoMenu.PERSONAGEM_ALEATORIO;
            case "3" -> OpcaoMenu.VER_FICHA;
            case "4" -> OpcaoMenu.ATACAR_INIMIGO;
            case "5" -> OpcaoMenu.SAIR;
            default  -> OpcaoMenu.INVALIDA;
        };
    }

    private static void tratarEscolha(OpcaoMenu opcao, Scanner scanner, EstadoJogo estado) {
        Log.info("");
        switch (opcao) {
            case CRIAR_PERSONAGEM     -> criarPersonagem(scanner, estado);
            case PERSONAGEM_ALEATORIO -> criarPersonagemAleatorio(scanner, estado);
            case VER_FICHA            -> verFicha(estado);
            case ATACAR_INIMIGO       -> Batalha.executar(scanner, estado);
            case SAIR                 -> Log.info("Até a próxima aventura!");
            case INVALIDA             -> Log.info("Opção inválida. Use 1, 2, 3, 4 ou 5.");
        }
    }

    // ─── Criação manual ───────────────────────────────────────────────────────

    private static void criarPersonagem(Scanner scanner, EstadoJogo estado) {
        boolean criado = false;
        while (!criado) {
            System.out.print("Nome do personagem: ");
            String nome = scanner.nextLine().trim();
            if (nome.isEmpty()) {
                Log.info("O nome não pode ser vazio!");
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
            Log.info("Personagem criado: " + jogador.getNome() + ".");
            pausar(800);
            jogador.exibirFicha();
            pausar(1500);
            criado = true;
        }
    }

    private static Personagem selecionarClasse(Scanner scanner, String nome) {
        Log.info("Classes disponíveis:");
        Log.info("  1 - Guerreiro (120 PV | Ataque: 8  | CA: 10)");
        Log.info("  2 - Mago      ( 60 PV | Ataque: 12 | CA: 12)");
        Log.info("  3 - Arqueiro  ( 85 PV | Ataque: 10 | CA: 14)");
        System.out.print("Escolha a sua classe: ");

        String op = scanner.hasNextLine() ? scanner.nextLine().trim() : "";
        Personagem jogador = switch (op) {
            case "1" -> new Guerreiro(nome);
            case "2" -> new Mago(nome);
            case "3" -> new Arqueiro(nome);
            default  -> null;
        };

        if (jogador == null) Log.info("Classe inválida. Tente novamente.");
        return jogador;
    }

    private static Armas selecionarArma(Scanner scanner) {
        Armas[] armas = Armas.values();
        Log.info("");
        Log.info("Armas disponíveis:");
        Log.info("  0  - Nenhuma");
        for (int i = 0; i < armas.length; i++) {
            System.out.printf("  %-2d - %s%n", (i + 1), armas[i]);
        }
        System.out.print("Escolha uma arma: ");

        int escolha = lerInteiro(scanner);
        if (escolha == 0) return null;
        if (escolha < 1 || escolha > armas.length) {
            Log.info("Opção inválida. Nenhuma arma equipada.");
            return null;
        }
        return armas[escolha - 1];
    }

    private static Armaduras selecionarArmadura(Scanner scanner) {
        Armaduras[] armaduras = Arrays.stream(Armaduras.values())
                .filter(a -> a.getType() != Armaduras.ArmorType.SHIELD
                        && a.getType() != Armaduras.ArmorType.UNARMORED)
                .toArray(Armaduras[]::new);

        Log.info("");
        Log.info("Armaduras disponíveis:");
        Log.info("  0  - Nenhuma");
        for (int i = 0; i < armaduras.length; i++) {
            System.out.printf("  %-2d - %s%n", (i + 1), armaduras[i]);
        }
        System.out.print("Escolha uma armadura: ");

        int escolha = lerInteiro(scanner);
        if (escolha == 0) return null;
        if (escolha < 1 || escolha > armaduras.length) {
            Log.info("Opção inválida. Nenhuma armadura equipada.");
            return null;
        }
        return armaduras[escolha - 1];
    }

    private static Armaduras selecionarEscudo(Scanner scanner) {
        Armaduras[] escudos = Arrays.stream(Armaduras.values())
                .filter(a -> a.getType() == Armaduras.ArmorType.SHIELD)
                .toArray(Armaduras[]::new);

        Log.info("");
        Log.info("Escudos disponíveis:");
        Log.info("  0  - Nenhum");
        for (int i = 0; i < escudos.length; i++) {
            System.out.printf("  %-2d - %s%n", (i + 1), escudos[i]);
        }
        System.out.print("Escolha um escudo: ");

        int escolha = lerInteiro(scanner);
        if (escolha == 0) return null;
        if (escolha < 1 || escolha > escudos.length) {
            Log.info("Opção inválida. Nenhum escudo equipado.");
            return null;
        }
        return escudos[escolha - 1];
    }

    // ─── Criação aleatória ────────────────────────────────────────────────────

    private static void criarPersonagemAleatorio(Scanner scanner, EstadoJogo estado) {
        System.out.print("Nome do personagem: ");
        String nome = scanner.nextLine().trim();
        if (nome.isEmpty()) {
            Log.info("O nome não pode ser vazio!");
            return;
        }

        Random rng = new Random();

        Personagem jogador = switch (rng.nextInt(3)) {
            case 0  -> new Guerreiro(nome);
            case 1  -> new Mago(nome);
            default -> new Arqueiro(nome);
        };

        Armas[] todasArmas = Armas.values();
        jogador.setArma(todasArmas[rng.nextInt(todasArmas.length)]);

        Armaduras[] opcoesArmadura = Arrays.stream(Armaduras.values())
                .filter(a -> a.getType() != Armaduras.ArmorType.SHIELD
                        && a.getType() != Armaduras.ArmorType.UNARMORED)
                .toArray(Armaduras[]::new);
        jogador.setArmadura(opcoesArmadura[rng.nextInt(opcoesArmadura.length)]);

        Armaduras[] opcoesEscudo = Arrays.stream(Armaduras.values())
                .filter(a -> a.getType() == Armaduras.ArmorType.SHIELD)
                .toArray(Armaduras[]::new);
        if (rng.nextBoolean()) {
            jogador.setEscudo(opcoesEscudo[rng.nextInt(opcoesEscudo.length)]);
        }

        estado.setJogador(jogador);
        Log.info("");
        Log.info("Personagem gerado aleatoriamente!");
        pausar(800);
        jogador.exibirFicha();
        pausar(1500);
    }

    // ─── Ver ficha ────────────────────────────────────────────────────────────

    private static void verFicha(EstadoJogo estado) {
        if (!estado.temJogador()) {
            Log.info("Nenhum personagem criado. Use a opção 1 primeiro.");
            return;
        }
        estado.getJogador().exibirFicha();
    }

    // ─── Utilitários ──────────────────────────────────────────────────────────

    private static int lerInteiro(Scanner scanner) {
        String linha = scanner.hasNextLine() ? scanner.nextLine().trim() : "";
        try {
            return Integer.parseInt(linha);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void pausar(int milissegundos) {
        try {
            TimeUnit.MILLISECONDS.sleep(milissegundos);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}