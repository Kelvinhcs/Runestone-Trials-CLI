/**
 * Inimigo rápido — ataca duas vezes por turno com dados menores.
 */
public class Lobo extends Personagem implements Inimigo {

    public Lobo() {
        super("Lobo", 40, 5, 13);
    }

    @Override
    public String getTipoInimigo() { return "Lobo"; }

    @Override
    public int getNivelPerigo() { return 1; }

    @Override
    protected int executarAtaqueSemArma() {
        int mordida1 = rolarDado("Mordida 1", 4);
        int mordida2 = rolarDado("Mordida 2", 4);
        Log.info("[ " + getNome() + " ] O Lobo ataca duas vezes!");
        return mordida1 + mordida2;
    }

    @Override
    public int defender() {
        Log.info("[ " + getNome() + " ] O Lobo esquiva com agilidade!");
        return rolarDado("Agilidade do lobo", 6);
    }

    @Override
    public void exibirFicha() {
        super.exibirFicha();
        Log.info("Tipo: Inimigo (Lobo)");
        Log.info("-------------");
    }
}