public class Arqueiro extends Personagem {

    public Arqueiro(String nome) {
        super(nome, 85, 10, 14);
    }

    @Override
    protected int executarAtaqueSemArma() {
        return rolarDado("Coronhada", 4);
    }

    @Override
    public void exibirFicha() {
        super.exibirFicha();
        System.out.println("Classe: Arqueiro");
        System.out.println("-------------");
    }

}
