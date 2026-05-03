public class Arqueiro extends Personagem {

    public Arqueiro(String nome) {
        super(nome, 85, 10, 13);
    }

    @Override
    protected void executarAtaque(Personagem alvo) {
        int dado = rolarDado("Flecha certeira", 8);
        int dano = getAtaque() + dado;
        System.out.println("[ " + nome + " ] Flecha disparada!");
        alvo.receberDano(dano);
    }

    @Override
    public void exibirFicha() {
        super.exibirFicha();
        System.out.println("Classe: Arqueiro");
        System.out.println("-------------");
    }

}
