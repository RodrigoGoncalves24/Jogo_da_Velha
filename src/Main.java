import java.util.Scanner;

/**
 * Ajustar check de vitória
 * Ajustar contagem da vitória em Tabuleiro
 * Checagem de erros de entrada pelo usuário - ÚLTIMO
 * */


public class Main {
    public static Scanner in = new Scanner(System.in);
    private static String nome, op, jogada;
    private static Jogador j1, j2;
    public static double coord;

    public static void main(String[] args) {
        Tabuleiro tabuleiro = new Tabuleiro();
        tabuleiro.geraTabuleiro();

        jogadores();

        System.out.println("\n\n");
        System.out.println("Começaremos pelo X: "+(j1.getXouO().equalsIgnoreCase("X")?j1.getNome():j2.getNome()));

        while(!tabuleiro.eVitoria()){
            tabuleiro.geraTabuleiro();
            System.out.println("Insira a coordenada a ser jogada: (Ex: 0,0)");
            try {
                coord = in.nextDouble();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            tabuleiro.jogada(coord);
            System.out.println("\n");
            System.out.println("Jogada da vez: "+ tabuleiro.jogadaVez());
        }

        System.out.println("\nVitória: "+tabuleiro.jogadaVez());
    }

    private static void jogadores(){
        System.out.println("Bem vindo ao jogo da velha!");
        System.out.println("Insira os jogadores: ");

        System.out.print("Nome jogador1: ");
        nome = in.nextLine();
        System.out.print("Escolha entre X ou O: ");
        op = in.nextLine();
        j1 = new Jogador(nome, op, 0);

        System.out.println();

        System.out.print("Nome jogador2: ");
        nome = in.nextLine();
        if(j1.getXouO().equalsIgnoreCase("X")) op = "O";
        else op = "X";
        j2 = new Jogador(nome, op, 0);
    }

}