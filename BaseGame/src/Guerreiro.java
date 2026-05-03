public class Guerreiro extends Personagem {

    public Guerreiro(String nome) {
        super(nome, 120, 8, 16);
    }

    @Override
    protected void executarAtaque(Personagem alvo) {
        int dado = rolarDado("Golpe de espada", 12);
        int dano = getAtaque() + dado;
        System.out.println("[ " + nome + " ] Golpe brutal!");
        alvo.receberDano(dano);
    }

    @Override
    public void exibirFicha() {
        super.exibirFicha();
        System.out.println("Classe: Guerreiro");
        System.out.println("-------------");
    }

}
