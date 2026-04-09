/**
 * Inimigo fixo da APS — 50 PV; ataca com dano bruto ataque + 1d6 (sabor de mesa).
 */
public class Goblin extends Personagem {

    public Goblin() {
        super("Goblin", 50, 12, 5);
    }

    @Override
    public void atacar(Personagem alvo) {
        if (alvo == null || alvo == this || estaDerrotado()) {
            return;
        }
        if (alvo.estaDerrotado()) {
            return;
        }
        int d6 = rolarD6("Fúria do goblin");
        int danoBruto = getAtaque() + d6;
        System.out.println("[GOBLIN] Garfada feroz!");
        alvo.receberDano(danoBruto);
    }

    @Override
    public void defender() {
        System.out.println("[GOBLIN] Se encolhe atrás de um saco podre!");
    }

    @Override
    public void exibirFicha() {
        super.exibirFicha();
        System.out.println("Classe: Inimigo (Goblin)");
        System.out.println("-------------");
    }
}
