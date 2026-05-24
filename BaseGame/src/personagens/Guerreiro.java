package personagens;

import util.Log;

public class Guerreiro extends Personagem {

    public Guerreiro(String nome) {
        super(nome, 120, 8, 10);
    }

    @Override
    protected int executarAtaqueSemArma() {
        return rolarDado("Soco brutal", 6);
    }

    @Override
    public int defender() {
        Log.info("[ " + getNome() + " ] Postura de escudo!");
        return rolarDado("Resistência do guerreiro", 10);
    }

    @Override
    public void exibirFicha() {
        super.exibirFicha();
        Log.info("Classe: Guerreiro");
        Log.info("-------------");
    }
}