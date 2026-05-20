public class Guerreiro extends Personagem {

    public Guerreiro(String nome) {
        super(nome, 120, 8, 10);
    }

    @Override
    protected int executarAtaqueSemArma() {
        return rolarDado("Soco brutal", 6);
    }

    @Override
    public void exibirFicha() {
        super.exibirFicha();
        System.out.println("Classe: Guerreiro");
        System.out.println("-------------");
    }

}
