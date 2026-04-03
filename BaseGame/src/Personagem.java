public abstract class Personagem {
    protected float nome;
    protected float pontosDeVida;
    protected float ataque;
    protected float defesa;
    protected Armaduras armadura;
    protected Armas arma;

    public String getNomeString(){
        return "O seu nome é: " + this.nome;}

    public float getNomeAtributo() {
        return nome;}

    public void setNome(float nome) {
        this.nome = nome;}

    public String getPontosDeVidaString(){
        return "Seus pontos de vida atuais são: " + this.pontosDeVida;}

    public float getPontosDeVidaAtributo() {
        return pontosDeVida;}


    public void setPontosDeVida(float pontosDeVida) {
        if (pontosDeVida < 0){
            this.pontosDeVida = 0;
        }
        else {this.pontosDeVida = pontosDeVida;}
    }

    public String getAtaqueString() {
        return "Seu ataque atual é de" + this.ataque;}

    public float getAtaqueAtributo() {
        return ataque;}

    public void setAtaque(float ataque) {
        this.ataque = ataque;}

    public String getDefesaString() {
        return "Sua defesa atual é de: " + this.defesa;}

    public float getDefesaAtributo() {
        return defesa;}

    public void setDefesa(float defesa) {
        this.defesa = defesa;}

    public String getArmaduraString() {
        return "Sua armadura atual é: "+this.armadura;}

    public Armaduras getArmaduraAtributo() {
        return armadura;}

    public void setArmadura(Armaduras armadura) {
        this.armadura = armadura;}

    public String getArmaString() {
        return "Sua arma atual é: "+this.arma;}

    public Armas getArmaAtributo() {
        return arma;}

    public void setArma(Armas arma) {
        this.arma = arma;}
}
