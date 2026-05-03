/**
 * Inimigo fixo da APS — 50 PV; ataca com dano bruto ataque + 1d6 (sabor de mesa).
 */
public class Goblin extends Personagem {

    public Goblin() {
        super("Goblin", 30, 5, 12);
    }

    @Override
    protected void executarAtaque(Personagem alvo) {
        int dado = rolarDado("Garfada do goblin", 6);
        int dano = getAtaque() + dado;
        System.out.println("[GOBLIN] Garfada feroz!");
        alvo.receberDano(dano);
    }


    @Override
    public void exibirFicha() {
        super.exibirFicha();
        System.out.println("Classe: Inimigo (Goblin)");
        System.out.println("-------------");
    }
}
