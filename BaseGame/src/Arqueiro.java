/**
 * Classe equilibrada — APS: PV 90, ataque 22, defesa 12;
 * dano = ataque + (0 a 10) + 1d6, com registro de rolagens.
 */
public class Arqueiro extends Personagem {

    public Arqueiro(String nome) {
        super(nome, 90, 22, 12);
    }

    @Override
    public void atacar(Personagem alvo) {
        if (!podeAtacar(alvo)) {
            return;
        }
        int bonusMira = RNG.nextInt(11);
        System.out.println("  ⌁ Ajuste de mira (1d11−1, faces 0–10): [" + bonusMira + "]");
        int d6 = rolarD6("Tensão do arco");
        int danoBruto = getAtaque() + bonusMira + d6;
        System.out.println("[ARQUEIRO] Flecha disparada!");
        alvo.receberDano(danoBruto);
    }

    @Override
    public void defender() {
        System.out.println("[ARQUEIRO] Recua e cobre-se com o terreno!");
    }

    @Override
    public void exibirFicha() {
        super.exibirFicha();
        System.out.println("Classe: Arqueiro");
        System.out.println("-------------");
    }

    private boolean podeAtacar(Personagem alvo) {
        if (alvo == null) {
            System.out.println("[ARQUEIRO] Não há alvo.");
            return false;
        }
        if (alvo == this) {
            System.out.println("[ARQUEIRO] Não é possível atacar a si mesmo.");
            return false;
        }
        if (estaDerrotado()) {
            System.out.println("[ARQUEIRO] Você está derrotado e não pode atacar.");
            return false;
        }
        if (alvo.estaDerrotado()) {
            System.out.println("[ARQUEIRO] O alvo já está derrotado.");
            return false;
        }
        return true;
    }
}
