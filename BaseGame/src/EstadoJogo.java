/**
 * Estado compartilhado entre o menu principal e o fluxo de batalha.
 */
public class EstadoJogo {

    private Personagem jogador;

    public void setJogador(Personagem jogador) {
        this.jogador = jogador;
    }

    public Personagem getJogador() {
        return jogador;
    }

    public boolean temJogador() {
        return jogador != null;
    }

    public boolean jogadorDerrotado() {
        return temJogador() && jogador.estaDerrotado();
    }
}