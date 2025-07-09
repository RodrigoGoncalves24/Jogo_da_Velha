import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Tabuleiro {
    private final String[][] tabuleiro = new String[5][3];
    private final ArrayList<Double> coordenadas = new ArrayList<>(Arrays.asList(0.0, 2.0, 4.0, 0.1, 2.1, 4.1, 0.2, 2.2, 4.2));
    private final ArrayList<Double> naoDiagonal = new ArrayList<>(Arrays.asList(0.1, 2.2, 4.1, 2.0));
    private static int coordX;
    private static int coordY;
    private static double coordOriginal;
    private static String XouO = "X";
    private static int vitoria = 0;
    private static boolean tabuleiroGerado = false;

    //Evita loop nas verificações
    private static boolean [] coordLado = new boolean[3];
    private static boolean [] coordCimaBaixo = new boolean[3];

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

    public void jogada(double coord) {
        coordOriginal = coord;
        coordX = (int) coord;
        coordY = (int) Math.round((coord - coordX) * 10);
        try {
            // Ajustar a troca dos caracteres
            if (coordenadas.contains(coord)) {
                if (!(tabuleiro[coordX][coordY].equals("X") || tabuleiro[coordX][coordY].equals("O"))) {
                    tabuleiro[coordX][coordY] = "   " + XouO + "  " + (coordY < 2 ? "|" : "");
                } else {
                    System.out.println("Jogada inválida: " + (Objects.equals(tabuleiro[coordX][coordY], "X") ? "X" : "O"));
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao jogar! Tente novamente!");
        }

        percorreLado();
        if (!eVitoria()) vitoria = 0;
        troca();
        checkDir();
        vitoria = 0;

    }

    public boolean eVitoria() {
        vitoria++;
        return vitoria >= 3;

    }

    // Loop quando verifica as coord do meio
    public boolean percorreLado() {
        if (tabuleiro[coordX][coordY].contains(XouO)) {
            lados();
            if (!eVitoria()){
                if (checkLados() && coordExiste(coordX, coordY + 1)) coordY += 1; // verificando se existe posição na direita
                else coordY -= 1;// verificando se existe posição na esquerda
                percorreLado();
            }
            return true;
        }
        restauraCoord();
        percorreCimaBaixo();
        return false;
    }

    private void lados(){
        switch (coordY){
            case 0:{
                coordLado[0] = true;
                break;
            }
            case 1:{
                coordLado[1] = true;
                break;
            }
            case 2:{
                coordLado[2] = true;
                break;
            }
        }
    }


    // Não percorrer diagonal quando coord for baixo
    public boolean percorreCimaBaixo() {
        if (tabuleiro[coordX][coordY].contains(XouO)) {
            coordCimaBaixo();
            if (!eVitoria()){
                if (checkCimaBaixo() && coordExiste(coordX + 2, coordY)) coordX += 2;
                else coordX -= 2;
                percorreCimaBaixo();
                if (!(naoDiagonal.contains(coordOriginal))) percorreDiagonal();
            }
            return true;
        }
        restauraCoord();
        return false;
    }

    private void coordCimaBaixo(){
        switch (coordX){
            case 0:{
                coordCimaBaixo[0] = true;
                break;
            }
            case 2:{
                coordCimaBaixo[1] = true;
                break;
            }
            case 4:{
                coordCimaBaixo[2] = true;
                break;
            }
        }
    }

    private boolean checkLados(){
        if(coordY >=3) {
            int i = 2;
            return coordLado[i];
        }else return coordLado[coordY];
    }

    private boolean checkCimaBaixo(){
        if(coordX >= 3){
            int i = 2;
            return coordCimaBaixo[i];
        }else return coordCimaBaixo[coordX];
    }

    // Arrumar metodo
    public void percorreDiagonal() {
        if (tabuleiro[coordX + 2][coordY + 1].contains(XouO)) {
            vitoria++;
            if (coordExiste(coordX + 2, coordY + 1)) {
                coordX += 2;
                coordY += 1;
            } else {
                coordX -= 2;
                coordY -= 1;
            }
            vitoria = 0;
            percorreDiagonal();
        }
        restauraCoord();
    }


    // Verifica se a coordenada  existe
    public boolean coordExiste(int x, int y) {
        String coordNova = x + "." + y;
        return coordenadas.contains(Double.parseDouble(coordNova));
    }

    public void troca() {
        if (XouO.equals("X")) XouO = "O";
        else XouO = "X";
    }

    public String jogadaVez() {
        return XouO;
    }

    // Evitar loop e retaurar as coordenas originais do programa para seguir
    private static void restauraCoord() {
        coordX = (int) coordOriginal;
        coordY = (int) Math.round((Double.parseDouble(String.valueOf(coordOriginal)) - coordX) * 10);
    }

    private static void checkDir() {
        for (int i = 0; i < 3; i++) {
            coordLado[i] = false;
            coordCimaBaixo[i] = false;
        }

    }
}

