public class Jogador {
    private String nome;
    private String XouO;

    public Jogador(String nome, String XouO) {
        this.nome = nome;
        this.XouO = XouO;
    }

    public String getNome() {
        return nome;
    }
    public String getXouO() {
        return XouO;
    }

    @Override
    public String toString() {
        return "Jogador: "+this.getNome()+"\nOpção: "+this.getXouO();
    }
}
