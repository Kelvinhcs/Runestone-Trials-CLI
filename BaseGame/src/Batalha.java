import java.util.Scanner;
public final class Batalha {

    //Loop de combate até a vitória, derrota ou fuga (reinicia o encontro).
    public static void executar(Scanner scanner, EstadoJogo estado) {
        Personagem jogador = estado.getJogador();

        if (!estado.temJogador()) {
            System.out.println("Nenhum personagem criado. Use a opção 1 primeiro.");
            return;
        }if (jogador.estaDerrotado()) {
            System.out.println("Seu personagem está derrotado. Crie outro na opção 1.");
            return;
        }
        Goblin goblin = estado.getAndCreateGoblin();
        System.out.println();
        System.out.println("================= BATALHA =================");

        int rodada = 0;
        int caOriginal = jogador.getCa();

        while (!jogador.estaDerrotado() && !goblin.estaDerrotado()) {
            rodada++;
            imprimirCabecalhoRodada(rodada, jogador, goblin);

            boolean fugiu = executarTurnoJogador(scanner, jogador, goblin, caOriginal, estado);
            if (fugiu) return;

            if (!estado.temGoblin()){
                goblin = estado.getAndCreateGoblin();
            } if (goblin.estaDerrotado()) {
                System.out.println();
                System.out.println("Vitória! O Goblin foi derrotado.");
                estado.limparGoblin();
                break;
            }
            executarTurnoInimigo(goblin, jogador);
            if (jogador.estaDerrotado()) {
                System.out.println();
                System.out.println("Você foi derrotado! Crie outro personagem na opção 1.");
                break;
            }
        }
        System.out.println("===========================================");
    }


    private static void imprimirCabecalhoRodada(int rodada, Personagem jogador, Goblin goblin) {
        System.out.println();
        System.out.println("------------- Rodada " + rodada + " -------------");
        System.out.println(jogador.getNome() + ": " + jogador.getPontosDeVida() + " PV  |  Goblin: " + goblin.getPontosDeVida() + " PV");
        System.out.println("  1 - Atacar o Goblin");
        System.out.println("  2 - Postura Defensiva (1 rodada)");
        System.out.println("  3 - Fugir da batalha (seus PV perdidos continuarão)");
        System.out.print("Escolha: ");
    }

    //Lê e executa a ação do jogador para a rodada atual.
    private static boolean executarTurnoJogador(Scanner scanner, Personagem jogador, Goblin goblin, int caOriginal, EstadoJogo estado) {

        // Restaura a CA original ao início de cada turno, antes de qualquer modificação.
        jogador.setCa(caOriginal);

        while (true) {
            String op = scanner.hasNextLine() ? scanner.nextLine().trim() : "";
            if ("1".equals(op)) {
                System.out.println();
                System.out.println();
                System.out.println();
                System.out.println();
                System.out.println(">>>>>>>>>>    Sua Ação    <<<<<<<<<<");
                jogador.atacar(goblin);
                return false;
            } else if ("2".equals(op)) {
                System.out.println();
                System.out.println();
                System.out.println();
                System.out.println();
                System.out.println(">>>>>>>>>>    Sua Ação    <<<<<<<<<<");
                int valorDefesa = jogador.defender();
                jogador.setCa(caOriginal + valorDefesa);
                return false;
            } else if ("3".equals(op)) {
                reiniciarEncontro(estado);
                System.out.println("Você fugiu do combate. Seus pontos de vida perdidos ainda foram contabilizados.");
                return true;
            } else {
                System.out.print("Opção inválida. Use 1, 2 ou 3: ");
            }
        }
    }

    private static void executarTurnoInimigo(Goblin goblin, Personagem jogador) {
        System.out.println();
        System.out.println(">>>>>>>>>> Ação Inimiga <<<<<<<<<<");
        goblin.atacar(jogador);
        System.out.println();
        System.out.println();
    }

    private static void reiniciarEncontro(EstadoJogo estado) {
        estado.limparGoblin();
        estado.getAndCreateGoblin();
    }
}
