/**
 * Inimigo elite — regenera PV a cada turno, dano pesado, vida alta.
 */
public class Troll extends Personagem implements Inimigo {

    private static final int REGENERACAO = 5;

    public Troll() {
        super("Troll", 90, 12, 11);
    }

    @Override
    public String getTipoInimigo() { return "Troll"; }

    @Override
    public int getNivelPerigo() { return 3; }

    /** Sobrescreve receberDano para regenerar após cada golpe. */
    @Override
    public void receberDano(int dano) {
        super.receberDano(dano);
        if (!estaDerrotado()) {
            setPontosDeVida(getPontosDeVida() + REGENERACAO);
            Log.info(" « Troll regenera " + REGENERACAO + " PV! PV atuais: " + getPontosDeVida() + " » ");
        }
    }

    @Override
    protected int executarAtaqueSemArma() {
        return rolarDado("Garras do Troll", 12);
    }

    @Override
    public int defender() {
        Log.info("[ " + getNome() + " ] O Troll absorve o golpe!");
        return rolarDado("Regeneração passiva", 8);
    }

    @Override
    public void exibirFicha() {
        super.exibirFicha();
        Log.info("Tipo: Inimigo (Troll) | Regenera " + REGENERACAO + " PV por turno");
        Log.info("-------------");
    }
}