/**
 * Inimigo ágil — CA alta por ser pequeno, dano médio, pouca vida.
 */
public class Esqueleto extends Personagem implements Inimigo {

    public Esqueleto() {
        super("Esqueleto", 35, 7, 14);
    }

    @Override
    public String getTipoInimigo() { return "Esqueleto"; }

    @Override
    public int getNivelPerigo() { return 1; }

    @Override
    protected int executarAtaqueSemArma() {
        return rolarDado("Golpe de osso", 6);
    }

    @Override
    public int defender() {
        Log.info("[ " + getNome() + " ] O Esqueleto se reorganiza!");
        return rolarDado("Estrutura óssea", 4);
    }

    @Override
    public void exibirFicha() {
        super.exibirFicha();
        Log.info("Tipo: Inimigo (Esqueleto)");
        Log.info("-------------");
    }
}