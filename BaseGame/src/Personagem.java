import java.util.Objects;
import java.util.Random;

/**
 * Classe base para todos os personagens adicionado ao jogo.
 */
public abstract class Personagem {

    protected static final Random RNG = new Random();
    protected String nome;
    protected int pontosDeVida;
    protected int ataque;
    protected int ca;
    protected Armaduras armadura;
    protected Armas arma;


    public Personagem(String nome, int pontosDeVida, int ataque, int ca) {
        setNome(nome);
        setPontosDeVida(pontosDeVida);
        setAtaque(ataque);
        setCa(ca);
        this.armadura = null;
        this.arma = null;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = Objects.requireNonNullElse(nome, "Sem nome");
    }

    public int getPontosDeVida() {
        return pontosDeVida;
    }

    public void setPontosDeVida(int pontosDeVida) {
        this.pontosDeVida = Math.max(0, pontosDeVida);
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public int getCa() {
        return ca;
    }

    public void setCa(int ca) {
        this.ca = Math.max(0, ca);
    }

    public Armaduras getArmadura() {
        return armadura;
    }

    public void setArmadura(Armaduras armadura) {
        this.armadura = armadura;
    }

    public Armas getArma() {
        return arma;
    }

    public void setArma(Armas arma) {
        this.arma = arma;
    }

    public boolean estaDerrotado() {
        return pontosDeVida <= 0;
    }

    /** Rolagem de dados com retorno do valor e print do resultado
     * Exemplo: « Ataque de fogo: 1d8 → [4] » */
    protected int rolarDado(String rotulo, int faces) {
        int resultado = RNG.nextInt(faces) + 1;
        System.out.println(" « " + rotulo + ": 1d" + faces + " → [" + resultado + "] » ");
        return resultado;
    }

    protected void receberDano(int danoBruto) {
        setPontosDeVida(pontosDeVida - danoBruto);
        System.out.println(" « " + nome + " sofre " + danoBruto + " de dano | PV restantes: " + pontosDeVida);
    }

    protected boolean podeAtacar(Personagem alvo) {
        if (alvo == null) {
            System.out.println("[ " + nome + " ] Não há alvo.");
            return false;
        }
        if (alvo == this) {
            System.out.println("[ " + nome + " ] Não é possível atacar a si mesmo.");
            return false;
        }
        if (estaDerrotado()) {
            System.out.println("[ " + nome + " ] Você está derrotado e não pode atacar.");
            return false;
        }
        if (alvo.estaDerrotado()) {
            System.out.println("[ " + nome + " ] O alvo já está derrotado.");
            return false;
        }
        return true;
    }

    public final void atacar(Personagem alvo) {
        if (!podeAtacar(alvo)) return;
        int rolagem = rolarDado("Teste de acerto", 20);
        boolean acertou = rolagem >= alvo.getCa();

        System.out.println(" « " + nome + " rolou " + rolagem + " contra CA " + alvo.getCa() + " — " + (acertou ? "ACERTO!" : "ERROU!") + " » ");

        if (acertou) {
            executarAtaque(alvo);
        }
    }

    protected abstract void executarAtaque(Personagem alvo);

    /** Comportamento de defesa — sobrescrito nas subclasses. */
    public int defender() {
        System.out.println("[" + nome + "] Postura defensiva!");
        return rolarDado("Defesa ativa",8);
    }

    /** Dados comuns da ficha; subclasses acrescentam a linha da classe. */
    public void exibirFicha() {
        System.out.println("--- Ficha ---");
        System.out.println("Nome: " + nome);
        System.out.println("Pontos de vida: " + pontosDeVida);
        System.out.println("Ataque: " + ataque);
        System.out.println("Defesa (C.A.): " + ca);
        if (armadura != null) {
            System.out.println("Armadura: " + armadura.getDisplayName());
        }
        if (arma != null) {
            System.out.println("Arma: " + arma.getDisplayName());
        }
    }
}
