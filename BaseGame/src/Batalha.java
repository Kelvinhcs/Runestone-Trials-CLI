import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public final class Batalha {

    private Batalha() {}

    public static void executar(Scanner scanner, EstadoJogo estado) {
        if (!estado.temJogador()) {
            Log.info("Nenhum personagem criado. Use a opção 1 primeiro.");
            return;
        }
        if (estado.jogadorDerrotado()) {
            Log.info("Seu personagem está derrotado. Crie outro na opção 1.");
            return;
        }

        Personagem jogador = estado.getJogador();
        List<Personagem> inimigos = GerenciadorEncontro.gerarEncontro();

        Log.info("");
        Log.info("================= BATALHA =================");
        GerenciadorEncontro.anunciarEncontro(inimigos);
        pausar(1000);

        int caOriginal = jogador.getCa();

        for (int i = 0; i < inimigos.size(); i++) {
            Personagem inimigoAtual = inimigos.get(i);

            if (inimigos.size() > 1) {
                Log.info("");
                Log.info(">>> Combate " + (i + 1) + " de " + inimigos.size()
                        + " — " + inimigoAtual.getNome() + " <<<");
                pausar(800);
            }

            boolean continuar = executarCombate(scanner, jogador, inimigoAtual, caOriginal, estado);

            if (!continuar || jogador.estaDerrotado()) break;

            if (i < inimigos.size() - 1) {
                Log.info("");
                Log.info("O próximo inimigo avança!");
                pausar(1200);
            }
        }

        Log.info("===========================================");
    }

    /** Executa o loop de combate entre o jogador e um único inimigo.
     *  Retorna false se o jogador fugiu. */
    private static boolean executarCombate(Scanner scanner, Personagem jogador,
                                           Personagem inimigo, int caOriginal, EstadoJogo estado) {

        int rodada = 0;

        while (!jogador.estaDerrotado() && !inimigo.estaDerrotado()) {
            rodada++;
            imprimirCabecalhoRodada(rodada, jogador, inimigo);

            boolean fugiu = executarTurnoJogador(scanner, jogador, inimigo, caOriginal, estado);
            if (fugiu) return false;

            if (inimigo.estaDerrotado()) {
                Log.info("");
                Log.info("Vitória! " + inimigo.getNome() + " foi derrotado.");
                pausar(800);
                break;
            }

            executarTurnoInimigo(inimigo, jogador);

            if (jogador.estaDerrotado()) {
                Log.info("");
                Log.info("Você foi derrotado! Crie outro personagem na opção 1.");
                break;
            }
        }

        return true;
    }

    private static void imprimirCabecalhoRodada(int rodada, Personagem jogador, Personagem inimigo) {
        Log.info("");
        Log.info("------------- Rodada " + rodada + " -------------");
        Log.info(jogador.getNome() + ": " + jogador.getPontosDeVida()
                + " PV  |  " + inimigo.getNome() + ": " + inimigo.getPontosDeVida() + " PV");
        Log.info("  1 - Atacar o " + inimigo.getNome());
        Log.info("  2 - Postura Defensiva (1 rodada)");
        Log.info("  3 - Fugir da batalha (seus PV perdidos continuarão)");
        System.out.print("Escolha: ");
    }

    private static boolean executarTurnoJogador(Scanner scanner, Personagem jogador,
                                                Personagem inimigo, int caOriginal, EstadoJogo estado) {

        jogador.setCa(caOriginal);

        while (true) {
            String op = scanner.hasNextLine() ? scanner.nextLine().trim() : "";

            if ("1".equals(op)) {
                Log.info("");
                Log.info(">>>>>>>>>>    Sua Ação    <<<<<<<<<<");
                jogador.atacar(inimigo);
                pausar(1000);
                return false;

            } else if ("2".equals(op)) {
                Log.info("");
                Log.info(">>>>>>>>>>    Sua Ação    <<<<<<<<<<");
                int valorDefesa = jogador.defender();
                jogador.setCa(caOriginal + valorDefesa);
                pausar(1000);
                return false;

            } else if ("3".equals(op)) {
                Log.info("Você fugiu do combate. Seus pontos de vida perdidos ainda foram contabilizados.");
                pausar(1000);
                return true;

            } else {
                System.out.print("Opção inválida. Use 1, 2 ou 3: ");
            }
        }
    }

    private static void executarTurnoInimigo(Personagem inimigo, Personagem jogador) {
        Log.info("");
        Log.info(">>>>>>>>>> Ação Inimiga <<<<<<<<<<");
        inimigo.atacar(jogador);
        pausar(1000);
    }

    private static void pausar(int milissegundos) {
        try {
            TimeUnit.MILLISECONDS.sleep(milissegundos);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}