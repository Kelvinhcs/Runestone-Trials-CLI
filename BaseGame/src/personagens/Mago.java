package personagens;

import util.Log;

public class Mago extends Personagem {

    public Mago(String nome) {
        super(nome, 60, 12, 12);
    }

    @Override
    protected int executarAtaqueSemArma() {
        int d1 = rolarDado("Magia bruta 1", 4);
        int d2 = rolarDado("Magia bruta 2", 4);
        return d1 + d2;
    }

    @Override
    public int defender() {
        Log.info("[ " + getNome() + " ] Escudo arcano!");
        return rolarDado("Barreira mágica", 6);
    }

    @Override
    public void exibirFicha() {
        super.exibirFicha();
        Log.info("Classe: Mago");
        Log.info("-------------");
    }
}