public class Mago extends Personagem {

    public Mago(String nome) {
        super(nome, 60, 14, 11);
    }

    @Override
    protected void executarAtaque(Personagem alvo) {
        int dado1 = rolarDado("Bola de Fogo 1", 8);
        int dado2 = rolarDado("Bola de Fogo 2", 8);
        int dano = getAtaque() + dado1 + dado2;
        System.out.println("[ " + nome + " ] Bola de Fogo!");
        alvo.receberDano(dano);
    }

    @Override
    public void exibirFicha() {
        super.exibirFicha();
        System.out.println("Classe: Mago");
        System.out.println("-------------");
    }

}
