public class Mago extends Personagem {

    public Mago(String nome) {
        super(nome, 60, 12, 12);
    }

    @Override
    protected int calcularDado(Personagem alvo) {
        int d1 = rolarDado("Bola de Fogo 1", 8);
        int d2 = rolarDado("Bola de Fogo 2", 8);
        return d1 + d2;
    }

    @Override
    public void exibirFicha() {
        super.exibirFicha();
        System.out.println("Classe: Mago");
        System.out.println("-------------");
    }

}
