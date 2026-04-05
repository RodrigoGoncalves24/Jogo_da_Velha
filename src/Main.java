import java.util.Scanner;

public class Main {
    public static Scanner in = new Scanner(System.in);
    private static Jogador j1, j2;
    private static int op = 1;
    private static double coord;
    private static Tabuleiro tabuleiro = new Tabuleiro();

    public static void main(String[] args) {
        System.out.println("Bem vindo ao jogo da velha!");

        while (op != 3) {
            jogadores();

            System.out.println("\nComeçaremos pelo X: " + (j1.getXouO().equalsIgnoreCase("X") ? j1.getNome() : j2.getNome()));
            while (!tabuleiro.eVitoria() && !tabuleiro.ahJogada()) { //Quando houver empate, o jogo precisa parar
                tabuleiro.geraTabuleiro();
                getCoord();

                tabuleiro.jogada(coord);
                System.out.println("\n");
                System.out.println("Jogada da vez: " + tabuleiro.jogadaVez());
            }

            if(tabuleiro.eVitoria()){
                tabuleiro.troca();
                System.out.println("\nVitória: " + tabuleiro.jogadaVez());
                tabuleiro.geraTabuleiro();
            }else{
                System.out.println("\nDeu empate! Velha!");
            }

            menu();
        }
        System.out.println("Jogo finalizado!");


    }


    /// Menu
    private static void menu() {
        System.out.println("Selecione a opção desejada: ");
        System.out.println("1 - Começar novon jogo");
        System.out.println("2 - Placar");
        System.out.println("3 - Finalizar");
        op = in.nextInt();
        if(op < 0 || op > 3){
            System.out.println("Opção inválida! Tente novamente!");
            menu();
        } else if (op == 2) {
            System.out.println("Pontuação: ");
            System.out.println(j1.toString());
            System.out.println(j2.toString());
        }
    }

    /// Get coordinate and prevent error
    public static void getCoord(){

        while (true){
            System.out.println("\nInsira a coordenada a ser jogada: (Ex: 0,0)");
            try {
                coord = in.nextDouble();
            } catch (Exception e) {
                System.out.println("Valor inválido, tente novamente!");
                in.nextLine();
                continue;
            }

            if(!tabuleiro.contains(coord)){
                System.out.println("Coordenada inválida! ");
                continue;
            }

            break; // Sai quando jogada válida
        }

    }


    /// Players
    private static void jogadores() {
        String nome, op;
        System.out.println("Insira os jogadores: ");

        System.out.print("Nome jogador1: ");

        nome = in.nextLine();
        System.out.print("Escolha entre X ou O: ");
        op = in.nextLine();
        j1 = new Jogador(nome, op);

        System.out.println();

        System.out.print("Nome jogador2: ");
        nome = in.nextLine();
        if (j1.getXouO().equalsIgnoreCase("X")) op = "O";
        else op = "X";
        j2 = new Jogador(nome, op);
    }

}