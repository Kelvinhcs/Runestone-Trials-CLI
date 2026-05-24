public class Orc extends Personagem implements Inimigo {

    public Orc() {
        super("Orc", 70, 10, 9);
    }

    @Override
    public String getTipoInimigo() { return "Orc"; }

    @Override
    public int getNivelPerigo() { return 2; }

    @Override
    protected int executarAtaqueSemArma() {
        return rolarDado("Pancada do Orc", 10);
    }

    @Override
    public int defender() {
        Log.info("[ " + getNome() + " ] O Orc urra e endurece a pele!");
        return rolarDado("Pele grossa", 6);
    }

    @Override
    public void exibirFicha() {
        super.exibirFicha();
        Log.info("Tipo: Inimigo (Orc)");
        Log.info("-------------");
    }
}