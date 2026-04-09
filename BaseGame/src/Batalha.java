import java.util.Scanner;

/**
 * Loop de combate contra o Goblin até vitória, derrota ou fuga (reinicia o encontro).
 */
public final class Batalha {

    private Batalha() {
    }

    public static void executar(Scanner scanner, EstadoJogo estado) {
        Personagem jogador = estado.getJogador();
        Goblin goblinDeCombate = estado.getGoblinDeCombate();

        if (jogador == null) {
            System.out.println("Nenhum personagem criado. Use a opção 1 primeiro.");
            return;
        }
        if (jogador.estaDerrotado()) {
            System.out.println("Seu personagem está derrotado. Crie outro na opção 1.");
            return;
        }
        if (goblinDeCombate == null || goblinDeCombate.estaDerrotado()) {
            goblinDeCombate = new Goblin();
            estado.setGoblinDeCombate(goblinDeCombate);
            System.out.println("Um Goblin surge (50 PV)!");
        }

        System.out.println();
        System.out.println("========== BATALHA ==========");

        int rodada = 0;
        while (!jogador.estaDerrotado() && !goblinDeCombate.estaDerrotado()) {
            rodada++;
            System.out.println();
            System.out.println("--- Rodada " + rodada + " ---");
            System.out.println(jogador.getNome() + ": " + jogador.getPontosDeVida() + " PV  |  Goblin: "
                    + goblinDeCombate.getPontosDeVida() + " PV");
            System.out.println("  1 - Atacar o Goblin nesta rodada");
            System.out.println("  2 - Sair da batalha (abandona e reinicia o encontro)");
            System.out.print("Escolha: ");

            String op = scanner.hasNextLine() ? scanner.nextLine().trim() : "";
            if ("2".equals(op)) {
                reiniciarEncontro(estado);
                System.out.println("Você deixou o combate. O encontro foi reiniciado — um Goblin fresco aguarda na próxima batalha.");
                break;
            }
            if (!"1".equals(op)) {
                System.out.println("Opção inválida. Use 1 ou 2.");
                rodada--;
                continue;
            }

            System.out.print("Preparar bloqueio extra (1d6) contra o golpe do Goblin ao fim desta rodada? (s/n): ");
            String resp = scanner.hasNextLine() ? scanner.nextLine().trim().toLowerCase() : "";
            if (resp.equals("s") || resp.equals("sim")) {
                jogador.prepararBloqueioProximoGolpe();
            }

            System.out.println();
            System.out.println(">>> Seu turno");
            jogador.atacar(goblinDeCombate);

            if (goblinDeCombate.estaDerrotado()) {
                jogador.limparEstadoCombate();
                System.out.println();
                System.out.println("Vitória! O Goblin foi derrotado.");
                estado.setGoblinDeCombate(null);
                break;
            }
            if (jogador.estaDerrotado()) {
                break;
            }

            System.out.println();
            System.out.println(">>> Turno do Goblin");
            goblinDeCombate.atacar(jogador);

            if (jogador.estaDerrotado()) {
                jogador.limparEstadoCombate();
                System.out.println();
                System.out.println("Você foi derrotado! Crie outro personagem na opção 1.");
                break;
            }
        }

        System.out.println("============================");
    }

    private static void reiniciarEncontro(EstadoJogo estado) {
        Personagem jogador = estado.getJogador();
        if (jogador != null) {
            jogador.limparEstadoCombate();
        }
        estado.setGoblinDeCombate(new Goblin());
    }
}
