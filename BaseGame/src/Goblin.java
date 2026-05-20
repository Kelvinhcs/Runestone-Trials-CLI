/**
 * Inimigo fixo da APS — 50 PV; ataca com dano bruto ataque + 1d6 (sabor de mesa).
 */
public class Goblin extends Personagem {

    public Goblin() {
        super("Goblin", 45, 6, 10);
    }

    @Override
    protected int executarAtaqueSemArma() {
        return rolarDado("Garfada do goblin", 6);
    }

    @Override
    public void exibirFicha() {
        super.exibirFicha();
        System.out.println("Classe: Inimigo (Goblin)");
        System.out.println("-------------");
    }
}
