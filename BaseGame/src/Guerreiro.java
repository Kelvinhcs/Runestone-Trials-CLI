/**
 * Lutador corpo a corpo — APS: PV 120, ataque 20, defesa 15; dano = ataque + 5 + 1d6.
 */
public class Guerreiro extends Personagem {

    public Guerreiro(String nome) {
        super(nome, 120, 20, 15);
    }

    @Override
    public void atacar(Personagem alvo) {
        if (!podeAtacar(alvo)) {
            return;
        }
        int d6 = rolarD6("Ímpeto da lâmina");
        int danoBruto = getAtaque() + 5 + d6;
        System.out.println("[GUERREIRO] Golpe de espada!");
        alvo.receberDano(danoBruto);
    }

    @Override
    public void defender() {
        System.out.println("[GUERREIRO] Escudo erguido, firme no chão!");
    }

    @Override
    public void exibirFicha() {
        super.exibirFicha();
        System.out.println("Classe: Guerreiro");
        System.out.println("-------------");
    }

    private boolean podeAtacar(Personagem alvo) {
        if (alvo == null) {
            System.out.println("[GUERREIRO] Não há alvo.");
            return false;
        }
        if (alvo == this) {
            System.out.println("[GUERREIRO] Não é possível atacar a si mesmo.");
            return false;
        }
        if (estaDerrotado()) {
            System.out.println("[GUERREIRO] Você está derrotado e não pode atacar.");
            return false;
        }
        if (alvo.estaDerrotado()) {
            System.out.println("[GUERREIRO] O alvo já está derrotado.");
            return false;
        }
        return true;
    }
}
