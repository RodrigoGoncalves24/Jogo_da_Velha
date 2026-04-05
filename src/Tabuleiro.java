import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Tabuleiro {
    private final String[][] tabuleiro = new String[5][3];
    private final ArrayList<Double> coordenadas = new ArrayList<>(Arrays.asList(0.0, 2.0, 4.0, 0.1, 2.1, 4.1, 0.2, 2.2, 4.2));
    private static ArrayList<double[]> possivelJogada;
    private static int quantidadeJogadasLivres = 9;
    private static double[][] jogadas = {
            {0.0, 0.0, 0.0, 0.0, 0.0},
            {0.0, 0.0, 0.0, 0.0, 0.0}
    };

    private final double[][] coordVitorias = {
            {0.0, 0.1, 0.2},
            {2.0, 2.1, 2.2},
            {4.0, 4.1, 4.2},
            {0.0, 2.0, 4.0},
            {0.1, 2.1, 4.1},
            {0.2, 2.2, 4.2},
            {0.0, 2.1, 4.2},
            {0.2, 2.1, 4.0}
    };


    private static int coordX;
    private static int coordY;
    private static double coordOriginal;
    private static String XouO = "X";
    private static int vitoria = 0;
    private static int pos = 0;

    private static boolean tabuleiroGerado = false;

    private void tabuleiro() {
        tabuleiro[0][0] = "  0.0 |";
        tabuleiro[1][0] = "──────┼";
        tabuleiro[2][0] = "  2.0 |";
        tabuleiro[3][0] = "──────┼";
        tabuleiro[4][0] = "  4.0 |";

        tabuleiro[0][1] = "  0.1 |";
        tabuleiro[1][1] = "──────┼";
        tabuleiro[2][1] = " 2.1  |";
        tabuleiro[3][1] = "──────┼";
        tabuleiro[4][1] = "  4.1 |";

        tabuleiro[0][2] = " 0.2  ";
        tabuleiro[1][2] = "──────";
        tabuleiro[2][2] = " 2.2 ";
        tabuleiro[3][2] = "──────";
        tabuleiro[4][2] = " 4.2 ";

    }

    public void geraTabuleiro() {
        if (!tabuleiroGerado) {
            tabuleiroGerado = true;
            tabuleiro();
        }
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(tabuleiro[i][j]);
            }
            System.out.println();
        }
    }

    /// Mark a play
    public void jogada(double coord) {
        coordOriginal = coord;
        coordX = (int) coord;
        coordY = (int) Math.round((coord - coordX) * 10);
        try {
            if (coordenadas.contains(coord)) {
                if (!(tabuleiro[coordX][coordY].contains("X") || tabuleiro[coordX][coordY].contains("O"))) {
                    tabuleiro[coordX][coordY] = "   " + XouO + "  " + (coordY < 2 ? "|" : "");
                } else {
                    System.out.println("Jogada inválida: " + (Objects.equals(tabuleiro[coordX][coordY], "X") ? "X" : "O"));
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao jogar! Tente novamente!");
        }

        /// Fill the player array
        if (jogadaVez().equals("X")) {
            jogadas[0][pos] = (coordOriginal);
        } else {
            jogadas[1][pos] = (coordOriginal);
            pos++;
        }

        verificaJogadas();
        quantidadeJogadasLivres--;
        troca();
    }

    /// With the arrays of moves, check the currency of possibilities victory
    public void verificaJogadas() {
        possivelJogada = new ArrayList<>();
        possivelJogada();

        int jogador;

        if (jogadaVez().equals("X")) jogador = 0;
        else jogador = 1;

        /// Verify, among the possibilities of that play, where there could be marking
        for (double[] doubles : possivelJogada) {
            for (int j = 0; j < 3; j++) {
                if (doubles[j] == jogadas[jogador][j]) {
                    vitoria++;
                    continue;
                }
                break;
            }
            if (eVitoria()) return;
            else vitoria = 0;
        }
    }

    /// Populates the array of moves — based on the selected coordinate, determines the possible moves that could lead to a win.
    private void possivelJogada() {
        for (double[] coordVitoria : coordVitorias) {
            for (int j = 0; j < 3; j++) {
                if (coordVitoria[j] == coordOriginal) {
                    possivelJogada.add(coordVitoria);
                    break;
                }
            }
        }
    }

    /// Check the currency of vitoria
    public boolean eVitoria() {
        return vitoria == 3;
    }

    /// Change player
    public void troca() {
        if (XouO.equals("X")) XouO = "O";
        else XouO = "X";
    }

    /// Return the player
    public String jogadaVez() {
        return XouO;
    }

    /// Return true if the array of coordinates contains the player coordinate
    public boolean contains(double coord){
        return coordenadas.contains(coord);

    }

    public boolean ahJogada(){
        return quantidadeJogadasLivres == 0;
    }

}

