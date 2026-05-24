public class Arqueiro extends Personagem {

    public Arqueiro(String nome) {
        super(nome, 85, 10, 14);
    }

    @Override
    protected int executarAtaqueSemArma() {
        return rolarDado("Coronhada", 4);
    }

    @Override
    public int defender() {
        Log.info("[ " + getNome() + " ] Esquiva ágil!");
        return rolarDado("Esquiva do arqueiro", 8);
    }

    @Override
    public void exibirFicha() {
        super.exibirFicha();
        Log.info("Classe: Arqueiro");
        Log.info("-------------");
    }
}