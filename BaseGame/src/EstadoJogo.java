/**
 * Estado compartilhado entre o menu principal e o fluxo de batalha.
 */
public class EstadoJogo {

    private Personagem jogador;
    private Goblin goblinDeCombate;

    public Personagem getJogador() {
        return jogador;
    }

    public void setJogador(Personagem jogador) {
        this.jogador = jogador;
    }

    public Goblin getGoblinDeCombate() {
        return goblinDeCombate;
    }

    public void setGoblinDeCombate(Goblin goblinDeCombate) {
        this.goblinDeCombate = goblinDeCombate;
    }
}
