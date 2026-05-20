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
    private int caBase;
    protected int ca;
    protected Armaduras armadura;
    protected Armas arma;
    protected Armaduras escudo;


    public Personagem(String nome, int pontosDeVida, int ataque, int ca) {
        setNome(nome);
        setPontosDeVida(pontosDeVida);
        setAtaque(ataque);
        this.caBase = ca;
        this.ca = ca;
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

    public int getCaBase() { return caBase; }

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
        recalcularCa();
    }

    public Armas getArma() {
        return arma;
    }

    public void setArma(Armas arma) {
        this.arma = arma;
    }

    public Armaduras getEscudo() { return escudo; }

    public void setEscudo(Armaduras escudo) {
        if (escudo != null && escudo.getType() != Armaduras.ArmorType.SHIELD) {
            throw new IllegalArgumentException("Item não é um escudo.");
        }
        this.escudo = escudo;
        recalcularCa();
    }

    private void recalcularCa() {
        int bonusArmadura = (armadura != null) ? armadura.getDefenseBonus() : 0;
        int bonusEscudo   = (escudo   != null) ? escudo.getDefenseBonus()   : 0;
        this.ca = caBase + bonusArmadura + bonusEscudo;
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
        int limiarCritico = (arma != null) ? arma.getCriticalThreshold() : 20;

        if (rolagem == 1) {
            System.out.println(" « ERRO CRÍTICO! " + nome + " se feriu com o próprio golpe! » ");
            aplicarDano(this, 1);
            return;
        }

        if (rolagem >= limiarCritico) {
            System.out.println(" « ACERTO CRÍTICO! » ");
            aplicarDano(alvo, 2);
            return;
        }

        boolean acertou = rolagem >= alvo.getCa();
        System.out.println(" « " + nome + " rolou " + rolagem + " contra CA " + alvo.getCa()
                + " - " + (acertou ? "ACERTO!" : "ERROU!") + " » ");

        if (acertou) {
            aplicarDano(alvo, 1);
        }
    }

    private void aplicarDano(Personagem alvo, int multiplicador) {
        int dado = calcularDado(alvo);
        int bonus = getBonusDano();
        int danoBase = dado + bonus;
        int danoTotal = danoBase * multiplicador;

        String breakdown = multiplicador > 1
                ? dado + " (dado) + " + bonus + " (bônus) = " + danoBase + " → " + danoBase + " x" + multiplicador + " (crítico) = " + danoTotal
                : dado + " (dado) + " + bonus + " (bônus) = " + danoTotal;

        System.out.println(" « Dano: " + breakdown + " » ");
        alvo.receberDano(danoTotal);
    }

    protected int getBonusDano() {
        return getAtaque();
    }

    protected int calcularDado(Personagem alvo) {
        if (arma != null) {
            return rolarDadosDaArma(arma);
        }
        return executarAtaqueSemArma();
    }

    private int rolarDadosDaArma(Armas arma) {
        String dice = arma.getDamageDice().contains("/")
                ? arma.getDamageDice().split("/")[0]
                : arma.getDamageDice();

        String[] parts = dice.split("d");
        int quantidade = Integer.parseInt(parts[0]);
        int faces      = Integer.parseInt(parts[1]);

        int total = 0;
        for (int i = 0; i < quantidade; i++) {
            total += rolarDado(arma.getDisplayName(), faces);
        }
        return total;
    }

    protected abstract int executarAtaqueSemArma();

    /** Comportamento de defesa - sobrescrito nas subclasses. */
    public int defender() {
        System.out.println("[" + nome + "] Postura defensiva!");
        return rolarDado("Defesa ativa",8);
    }

    /** Dados comuns da ficha; subclasses acrescentam a linha da classe. */
    public void exibirFicha() {
        System.out.println("--- Ficha ---");
        System.out.println("Nome:           " + nome);
        System.out.println("Pontos de vida: " + pontosDeVida);
        System.out.println("Ataque:         " + ataque);
        if (armadura != null) {
            System.out.println("Defesa (C.A.):  " + ca
                    + " (base " + caBase + " + armadura " + armadura.getDefenseBonus() + ")");
            System.out.println("Armadura:       " + armadura.getDisplayName()
                    + (armadura.temPenalidade() ? " [Penalidade: " + armadura.getArmorPenalty() + "]" : ""));
        } else {
            System.out.println("Defesa (C.A.):  " + ca);
        }
        if (arma != null) {
            System.out.println("Arma:           " + arma.getDisplayName()
                    + " [" + arma.getDamageDice() + " | " + arma.getDamageType() + "]");
        }
    }
}
