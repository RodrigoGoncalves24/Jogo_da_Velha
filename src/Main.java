import java.util.Scanner;

/**
 * Ajustar check de vitória - funcioando
 * Ajustar contagem da vitória em Tabuleiro - ajustado e funcionando
 * Ajustar "percorreDiagonal" - funcionando
 * Problemmas com coordenddas 0,2 - corrigido
 * Testar possíveis jogadas de vitória e empate
 * Validar e fazer os pequenos ajustes de cada jogada
 *
 *
 * Checagem de erros de entrada pelo usuário - ÚLTIMO
 * */


public class Main {
    public static Scanner in = new Scanner(System.in);
    private static Jogador j1, j2;

    public static void main(String[] args) {
        Tabuleiro tabuleiro = new Tabuleiro();
        tabuleiro.geraTabuleiro();
        double coord;

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
        tabuleiro.geraTabuleiro();

    }

    private static void jogadores(){
        String nome, op;
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