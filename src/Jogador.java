public class Jogador {
    private String nome;
    private String XouO;
    private double pontuacao;

    public Jogador(String nome, String XouO) {
        this.nome = nome;
        this.XouO = XouO;
        this.pontuacao = 0;
    }

    public String getNome() {
        return nome;
    }
    public String getXouO() {
        return XouO;
    }

    public double getPontuacao() {
        return pontuacao;
    }

    @Override
    public String toString() {
        return "Jogador: "+this.getNome()+"\nOpção: "+this.getXouO() +"\nPontuação: "+getPontuacao();
    }
}
