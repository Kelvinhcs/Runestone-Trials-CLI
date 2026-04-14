import java.util.Objects;
import java.util.Random;

/**
 * Classe base para todo personagem adicionado ao jogo.
 */
public abstract class Personagem {

    protected static final Random RNG = new Random();
    protected String nome;
    protected int pontosDeVida;
    protected int ataque;
    protected int defesa;
    protected Armaduras armadura;
    protected Armas arma;

    /**
     * Usado apenas durante batalha: se true, o próximo dano recebido aplica −1d6 após a defesa fixa.
     * Controlado pelo fluxo de combate.
     */
    private boolean bloqueioExtraProximoGolpe;

    public Personagem(String nome, int pontosDeVida, int ataque, int defesa) {
        setNome(nome);
        setPontosDeVida(pontosDeVida);
        setAtaque(ataque);
        setDefesa(defesa);
        this.armadura = null;
        this.arma = null;
        this.bloqueioExtraProximoGolpe = false;
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
        this.ataque = Math.max(0, ataque);
    }

    public int getDefesa() {
        return defesa;
    }

    public void setDefesa(int defesa) {
        this.defesa = Math.max(0, defesa);
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

    /**
     * Marca o próximo golpe recebido para aplicar bloqueio extra (1d6) e exibe o texto de {@link #defender()}.
     * Deve ser usado só no contexto de rodadas de batalha.
     */
    public void prepararBloqueioProximoGolpe() {
        bloqueioExtraProximoGolpe = true;
        defender();
    }


    /** 1d6 com registro no log, estilo mesa (ex.: «1d6 → [4] = 4»). */
    protected int rolarD6(String rotulo) {
        int faces = RNG.nextInt(6) + 1;
        System.out.println("  ⌁ " + rotulo + ": 1d6 → [" + faces + "] = " + faces);
        return faces;
    }

    /**
     * Dano bruto é reduzido pela defesa fixa; se houver bloqueio de combate preparado, subtrai-se mais 1d6 (uma vez).
     */
    protected void receberDano(int danoBruto) {
        int dano = Math.max(0, danoBruto - defesa);
        if (bloqueioExtraProximoGolpe) {
            int bloqueio = rolarD6("Bloqueio (postura defensiva)");
            dano = Math.max(0, dano - bloqueio);
            bloqueioExtraProximoGolpe = false;
        }
        setPontosDeVida(pontosDeVida - dano);
        System.out.println("  → " + nome + " sofre " + dano + " de dano | PV restantes: " + pontosDeVida);
    }

    public abstract void atacar(Personagem alvo);

    /** Comportamento de defesa — sobrescrito nas subclasses. */
    public void defender() {
        System.out.println("[" + nome + "] Postura defensiva!");
    }

    /** Dados comuns da ficha; subclasses acrescentam a linha da classe. */
    public void exibirFicha() {
        System.out.println("--- Ficha ---");
        System.out.println("Nome: " + nome);
        System.out.println("Pontos de vida: " + pontosDeVida);
        System.out.println("Ataque: " + ataque);
        System.out.println("Defesa: " + defesa);
        if (armadura != null) {
            System.out.println("Armadura: " + armadura.getDisplayName());
        }
        if (arma != null) {
            System.out.println("Arma: " + arma.getDisplayName());
        }
    }
}
