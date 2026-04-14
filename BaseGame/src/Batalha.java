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
        System.out.println("================= BATALHA =================");

        int rodada = 0;
        while (!jogador.estaDerrotado() && !goblinDeCombate.estaDerrotado()) {
            rodada++;
            System.out.println();
            System.out.println("------------- Rodada " + rodada + " -------------");
            System.out.println(jogador.getNome() + ": " + jogador.getPontosDeVida() + " PV  |  Goblin: " + goblinDeCombate.getPontosDeVida() + " PV");
            System.out.println("  1 - Atacar o Goblin nesta rodada");
            System.out.println("  2 - Sair da batalha (seus PV perdidos continuarão)");
            System.out.print("Escolha: ");

            while (true) {
                String op = scanner.hasNextLine() ? scanner.nextLine().trim() : "";
                if ("1".equals(op)) {
                    break;
                }
                if ("2".equals(op)) {
                    reiniciarEncontro(estado);
                    System.out.println("Você deixou o combate. O encontro foi reiniciado — seus pontos de vida ainda foram contabilizados.");
                    return;
                }
                System.out.println("Opção inválida. Use 1 ou 2.");
            }

            System.out.println();
            System.out.println(">>>>>>>>>>    Sua Ação    <<<<<<<<<<");
            jogador.atacar(goblinDeCombate);

            if (goblinDeCombate.estaDerrotado()) {
                System.out.println();
                System.out.println("Vitória! O Goblin foi derrotado.");
                estado.setGoblinDeCombate(null);
                break;
            }
            if (jogador.estaDerrotado()) {
                break;
            }

            System.out.println();
            System.out.println(">>>>>>>>>> Ação Inimiga <<<<<<<<<<");
            goblinDeCombate.atacar(jogador);

            if (jogador.estaDerrotado()) {
                System.out.println();
                System.out.println("Você foi derrotado! Crie outro personagem na opção 1.");
                break;
            }
        }

        System.out.println("===========================================");
    }

    private static void reiniciarEncontro(EstadoJogo estado) {
        estado.setGoblinDeCombate(new Goblin());
    }
}
