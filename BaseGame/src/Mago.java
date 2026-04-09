/**
 * Conjurador — APS: PV 70, ataque 30, defesa 8; dano = ataque + 10 + 1d6.
 */
public class Mago extends Personagem {

    public Mago(String nome) {
        super(nome, 70, 30, 8);
    }

    @Override
    public void atacar(Personagem alvo) {
        if (!podeAtacar(alvo)) {
            return;
        }
        int d6 = rolarD6("Surto arcano");
        int danoBruto = getAtaque() + 10 + d6;
        System.out.println("[MAGO] Bola de Fogo!");
        alvo.receberDano(danoBruto);
    }

    @Override
    public void defender() {
        System.out.println("[MAGO] Barreira mágica fraca ao redor do corpo!");
    }

    @Override
    public void exibirFicha() {
        super.exibirFicha();
        System.out.println("Classe: Mago");
        System.out.println("-------------");
    }

    private boolean podeAtacar(Personagem alvo) {
        if (alvo == null) {
            System.out.println("[MAGO] Não há alvo.");
            return false;
        }
        if (alvo == this) {
            System.out.println("[MAGO] Não é possível atacar a si mesmo.");
            return false;
        }
        if (estaDerrotado()) {
            System.out.println("[MAGO] Você está derrotado e não pode atacar.");
            return false;
        }
        if (alvo.estaDerrotado()) {
            System.out.println("[MAGO] O alvo já está derrotado.");
            return false;
        }
        return true;
    }
}
