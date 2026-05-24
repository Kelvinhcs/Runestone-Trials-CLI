package inimigos;

import personagens.Personagem;
import util.Log;

public class Goblin extends Personagem implements Inimigo {

    public Goblin() {
        super("Goblin", 50, 6, 10);
    }

    @Override
    public String getTipoInimigo() { return "Goblin"; }

    @Override
    public int getNivelPerigo() { return 1; }

    @Override
    protected int executarAtaqueSemArma() {
        return rolarDado("Garfada do goblin", 6);
    }

    @Override
    public int defender() {
        Log.info("[ " + getNome() + " ] O Goblin recua!");
        return rolarDado("Agilidade do goblin", 4);
    }

    @Override
    public void exibirFicha() {
        super.exibirFicha();
        Log.info("Tipo: Inimigo (Goblin)");
        Log.info("-------------");
    }
}