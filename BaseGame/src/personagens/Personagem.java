package personagens;

import java.util.Objects;
import java.util.Random;
import equipamento.Armas;
import equipamento.Armaduras;
import util.Log;

/**
 * Classe base para todos os personagens do jogo.
 * Implementa Combatente para garantir o contrato de combate via interface.
 */
public abstract class Personagem implements Combatente {

    private static final Random RNG = new Random();

    private String nome;
    private int pontosDeVida;
    private int ataque;
    private int caBase;
    private int ca;
    private Armaduras armadura;
    private Armas arma;
    private Armaduras escudo;

    // ─── Construtor ───────────────────────────────────────────────────────────

    public Personagem(String nome, int pontosDeVida, int ataque, int ca) {
        setNome(nome);
        setPontosDeVida(pontosDeVida);
        setAtaque(ataque);
        this.caBase   = ca;
        this.ca       = ca;
        this.armadura = null;
        this.arma     = null;
        this.escudo   = null;
    }

    // ─── Getters e Setters ────────────────────────────────────────────────────

    public String getNome() { return nome; }

    public void setNome(String nome) {
        this.nome = Objects.requireNonNullElse(nome, "Sem nome");
    }

    public int getPontosDeVida() { return pontosDeVida; }

    public void setPontosDeVida(int pontosDeVida) {
        this.pontosDeVida = Math.max(0, pontosDeVida);
    }

    public int getAtaque() { return ataque; }

    public void setAtaque(int ataque) { this.ataque = ataque; }

    public int getCaBase() { return caBase; }

    public int getCa() { return ca; }

    public void setCa(int ca) { this.ca = Math.max(0, ca); }

    public Armaduras getArmadura() { return armadura; }

    public void setArmadura(Armaduras armadura) {
        this.armadura = armadura;
        recalcularCa();
    }

    public Armas getArma() { return arma; }

    public void setArma(Armas arma) { this.arma = arma; }

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

    // ─── Combate ──────────────────────────────────────────────────────────────

    @Override
    public boolean estaDerrotado() {
        return pontosDeVida <= 0;
    }

    protected boolean podeAtacar(Personagem alvo) {
        if (alvo == null) {
            Log.info("[ " + getNome() + " ] Não há alvo.");
            return false;
        }
        if (alvo == this) {
            Log.info("[ " + getNome() + " ] Não é possível atacar a si mesmo.");
            return false;
        }
        if (estaDerrotado()) {
            Log.info("[ " + getNome() + " ] Você está derrotado e não pode atacar.");
            return false;
        }
        if (alvo.estaDerrotado()) {
            Log.info("[ " + getNome() + " ] O alvo já está derrotado.");
            return false;
        }
        return true;
    }

    @Override
    public final void atacar(Personagem alvo) {
        if (!podeAtacar(alvo)) return;

        int rolagem       = rolarDado("Teste de acerto", 20);
        int limiarCritico = (arma != null) ? arma.getCriticalThreshold() : 20;

        if (rolagem == 1) {
            Log.criticoErro(getNome());
            aplicarDano(this, 1);
            return;
        }

        if (rolagem >= limiarCritico) {
            Log.criticoAcerto();
            aplicarDano(alvo, 2);
            return;
        }

        boolean acertou = rolagem >= alvo.getCa();
        Log.acerto(getNome(), rolagem, alvo.getCa(), acertou);

        if (acertou) {
            aplicarDano(alvo, 1);
        }
    }

    private void aplicarDano(Personagem alvo, int multiplicador) {
        int dado      = calcularDado(alvo);
        int bonus     = getBonusDano();
        int danoBase  = dado + bonus;
        int danoTotal = danoBase * multiplicador;

        String breakdown = multiplicador > 1
                ? dado + " (dado) + " + bonus + " (bônus) = " + danoBase
                + " - " + danoBase + " x" + multiplicador + " (crítico) = " + danoTotal
                : dado + " (dado) + " + bonus + " (bônus) = " + danoTotal;

        Log.dano(breakdown);
        alvo.receberDano(danoTotal);
    }

    @Override
    public void receberDano(int danoBruto) {
        setPontosDeVida(getPontosDeVida() - danoBruto);
        Log.danoCausado(getNome(), danoBruto, getPontosDeVida());
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

    /** Dado usado quando sem arma equipada - definido por cada subclasse. */
    protected abstract int executarAtaqueSemArma();

    /** Comportamento de defesa - sobrescrito em cada subclasse com estilo próprio. */
    @Override
    public int defender() {
        Log.info("[" + getNome() + "] Postura defensiva!");
        return rolarDado("Defesa ativa", 8);
    }

    /** Rolagem de dados com print estilo mesa: « Rótulo: 1dX - [Y] » */
    protected int rolarDado(String rotulo, int faces) {
        int resultado = RNG.nextInt(faces) + 1;
        Log.rolagem(rotulo, faces, resultado);
        return resultado;
    }

    /** Dados comuns da ficha - subclasses chamam super e acrescentam a linha da classe. */
    public void exibirFicha() {
        Log.info("--- Ficha ---");
        Log.info("Nome:           " + getNome());
        Log.info("Pontos de vida: " + getPontosDeVida());
        Log.info("Ataque:         " + getAtaque());

        if (armadura != null) {
            Log.info("Defesa (C.A.):  " + getCa()
                    + " (base " + getCaBase() + " + armadura " + armadura.getDefenseBonus()
                    + (escudo != null ? " + escudo " + escudo.getDefenseBonus() : "") + ")");
            Log.info("Armadura:       " + armadura.getDisplayName()
                    + (armadura.temPenalidade()
                    ? " [Penalidade: " + armadura.getArmorPenalty() + "]" : ""));
        } else {
            Log.info("Defesa (C.A.):  " + getCa());
        }

        if (escudo != null) {
            Log.info("Escudo:         " + escudo.getDisplayName()
                    + (escudo.temPenalidade()
                    ? " [Penalidade: " + escudo.getArmorPenalty() + "]" : ""));
        }

        if (arma != null) {
            Log.info("Arma:           " + arma.getDisplayName()
                    + " [" + arma.getDamageDice() + " | " + arma.getDamageType() + "]");
        }
    }
}
