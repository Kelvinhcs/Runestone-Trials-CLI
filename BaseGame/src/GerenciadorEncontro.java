import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Gera encontros com um ou mais inimigos aleatórios.
 * A chance de encontros adicionais diminui progressivamente.
 */
public final class GerenciadorEncontro {

    private static final Random RNG = new Random();

    /** Probabilidades de cada inimigo adicional aparecer */
    private static final double CHANCE_SEGUNDO  = 0.40;
    private static final double CHANCE_TERCEIRO = 0.20;
    private static final double CHANCE_QUARTO   = 0.08;

    private GerenciadorEncontro() {}

    /**
     * Gera uma lista de inimigos para o encontro atual.
     * Sempre tem pelo menos 1, podendo ter até 4.
     */
    public static List<Personagem> gerarEncontro() {
        List<Personagem> inimigos = new ArrayList<>();

        inimigos.add(gerarInimigoAleatorio());

        if (RNG.nextDouble() < CHANCE_SEGUNDO) {
            inimigos.add(gerarInimigoAleatorio());
        }
        if (RNG.nextDouble() < CHANCE_TERCEIRO) {
            inimigos.add(gerarInimigoAleatorio());
        }
        if (RNG.nextDouble() < CHANCE_QUARTO) {
            inimigos.add(gerarInimigoAleatorio());
        }

        return inimigos;
    }

    /** Sorteia um inimigo aleatório entre os disponíveis. */
    private static Personagem gerarInimigoAleatorio() {
        return switch (RNG.nextInt(5)) {
            case 0  -> new Goblin();
            case 1  -> new Orc();
            case 2  -> new Esqueleto();
            case 3  -> new Lobo();
            default -> new Troll();
        };
    }

    /** Exibe o anúncio do encontro formatado. */
    public static void anunciarEncontro(List<Personagem> inimigos) {
        if (inimigos.size() == 1) {
            Log.info("Um " + inimigos.get(0).getNome() + " surge na sua frente!");
        } else {
            StringBuilder sb = new StringBuilder("Encontro múltiplo! ");
            for (int i = 0; i < inimigos.size(); i++) {
                sb.append(inimigos.get(i).getNome());
                if (i < inimigos.size() - 1) sb.append(", ");
            }
            sb.append(" aparecem!");
            Log.info(sb.toString());
        }
    }
}