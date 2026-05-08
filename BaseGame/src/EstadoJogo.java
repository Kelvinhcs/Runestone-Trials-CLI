/**
 * Estado compartilhado entre o menu principal e o fluxo de batalha.
 */
public class EstadoJogo {

    private Personagem jogador;
    private Goblin goblinDeCombate;

    public void setJogador(Personagem jogador) {
        this.jogador = jogador;
    }

    public boolean temJogador() {
        return jogador != null;
    }

    public boolean temGoblin(){
        return goblinDeCombate != null && !goblinDeCombate.estaDerrotado();
    }

    public Personagem getJogador() {
        return jogador;
    }

    public Goblin getAndCreateGoblin() {
        if (goblinDeCombate == null || goblinDeCombate.estaDerrotado()) {
            goblinDeCombate = new Goblin();
            System.out.println("Um Goblin surge (30 PV)!");
        }
        return goblinDeCombate;
    }

    public void limparGoblin() {
        this.goblinDeCombate = null;
    }
}
