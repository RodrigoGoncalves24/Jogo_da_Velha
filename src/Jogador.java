public class Jogador {
    private String nome;
    private String XouO;
    private int pontuacao;

    public Jogador(String nome, String XouO, int pontuacao) {
        this.nome = nome;
        this.XouO = XouO;
        this.pontuacao = pontuacao;
    }

    public String getNome() {
        return nome;
    }
    public String getXouO() {
        return XouO;
    }
    public int getPontuacao() {
        return pontuacao;
    }

    @Override
    public String toString() {
        return "Jogador: "+this.getNome()+"\nOpção: "+this.getXouO()+"\nPontuacao: "+this.getPontuacao();
    }
}
