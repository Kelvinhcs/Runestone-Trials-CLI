public class Arqueiro extends Personagem {

    public Arqueiro(String nome) {
        super(nome, 85, 10, 14);
    }

    @Override
    protected int calcularDado(Personagem alvo) {
        return rolarDado("Flecha certeira", 8);
    }

    @Override
    public void exibirFicha() {
        super.exibirFicha();
        System.out.println("Classe: Arqueiro");
        System.out.println("-------------");
    }

}
