public interface Combatente {
    void atacar(Personagem alvo);
    int defender();
    void receberDano(int dano);
    boolean estaDerrotado();
    String getNome();
}
