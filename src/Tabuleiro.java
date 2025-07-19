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
    //private static int index = 0;
    private static boolean tabuleiroGerado = false;

    //Evita loop nas verificações
    private static final boolean[] coordLado = new boolean[3];
    private static final boolean[] coordCimaBaixo = new boolean[3];
    private static final boolean[] coordDiagonal = new boolean[5];

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
        if (!eVitoria()) {
            vitoria = 0;
            troca();
            restauraDir();
        }
    }

    public boolean eVitoria() {
        return vitoria == 3;
    }

    // Loop quando verifica as coord do meio
    public void percorreLado() {
        if (tabuleiro[coordX][coordY].contains(XouO)) {
            vitoria++;
            verificaVisitados(coordY, 'y');
            if (!eVitoria()) {
                if (!check(coordY, 'y') && coordExiste(coordX, coordY + 1)) coordY += 1; // verifica se a posição não foi visitada e se ela existe
                else coordY -= 1;// verificando se existe posição na esquerda
                percorreLado();
            }
            return;
        }
        restauraCoord();
        vitoria = 0;
        percorreCimaBaixo();
    }

    // Indica que a posição já foi visitada, tirando a chance de verificar ela novamente
    private void verificaVisitados(int coord, char c) {
        if (c == 'x') {
            switch (coord) {
                case 0: {
                    coordCimaBaixo[0] = true;
                    break;
                }
                case 1: {
                    coordCimaBaixo[1] = true;
                    break;
                }
                case 2: {
                    coordCimaBaixo[2] = true;
                    break;
                }
            }
        } else {
            switch (coord) {
                case 0: {
                    coordLado[0] = true;
                    break;
                }
                case 2: {
                    coordLado[1] = true;
                    break;
                }
                case 4: {
                    coordLado[2] = true;
                    break;
                }
            }
        }


    }

    // Identifica se lado já foi visitado para poder ir para a próxima -- retorna true or false
    private boolean check(int coordenada, char c) {
        int i = coordenada + 1; // verifica se a coordenada da frente já foi visitada
        if (i >= 3) i = 2;
        if (c == 'y') return coordLado[i];
        else return coordCimaBaixo[i];
    }

    // Não percorrer diagonal quando coord for baixo
    public void percorreCimaBaixo() {
        if (tabuleiro[coordX][coordY].contains(XouO)) {
            vitoria++;
            verificaVisitados(coordX, 'x');
            if (!eVitoria()) {
                if (!check(coordX, 'x') && coordExiste(coordX + 2, coordY)) coordX += 2;
                else coordX -= 2;
                percorreCimaBaixo();
            }
            return;
        }
        restauraCoord();
        vitoria = 0;
        if (!naoDiagonal.contains(coordOriginal)) percorreDiagonal();
    }


    // Arrumar metodo -- problema com a contagem da vitória
    public void percorreDiagonal() {
        if (tabuleiro[coordX][coordY].contains(XouO)) {
            vitoria++;
            verificaVisitadosDiagonal(coordX, coordY); // marcar coordenada como já visitada
            if (!eVitoria() && !checkDiagonal()) {
                // identificar de onde ele veio para decidir direção que vai
                int x = coordX;
                int y = coordY;

                if (x == 0 && y == 0) { // leva para o meio do canto superior direito
                    coordX += 2;
                    coordY += 1;
                } else if (x == 0 && y == 2) { // leva para o meio do canto superior esquerdo
                    coordX += 2;
                    coordY -= 1;
                } else if (x == 2 && y == 1) { // restaurar a coordenada de onde ele veio para saber para onde vai
                    restauraCoord();
                    if(coordX == 0 && coordY == 0 || coordX ==2 && coordY == 1){ // caso ele tenha começado no centro, manda pra uma direção
                        x += 2;
                        y += 1;
                    }else if(coordX == 0 && coordY == 2){
                        x +=2;
                        y -= 1;
                    }else if (coordX == 4 && coordY == 0){
                        x -= 2;
                        y += 1;
                    }else if(coordX == 4 && coordY == 2){
                        x -= 2;
                        y -= 1;
                    }
                    coordX = x;
                    coordY = y;
                } else if (x == 4 && y == 0) { // leva para o meio do canto inferior esquerdo
                    coordX -= 2;
                    coordY += 1;
                } else if (x == 4 && y == 2) { // leva para o meio do canto inferior direito
                    coordX -= 2;
                    coordY -= 1;
                }
                percorreDiagonal();
            }
            return;
        }
        restauraCoord();
        vitoria = 0;
    }

    // Ajustar método
    public boolean checkDiagonal() {
        if (coordX == 0 && coordY == 0 ) return coordDiagonal[4];
        if (coordX == 0 && coordY == 2) return coordDiagonal[3];
        if (coordX == 4 && coordY == 0) return coordDiagonal[1];
        if (coordX == 4 && coordY == 2) return coordDiagonal[0];
        return  coordDiagonal[2];

    }

    public void verificaVisitadosDiagonal(int c1, int c2) {
        if (c1 == 0 && c2 == 0) {
            coordDiagonal[0] = true;
        } else if (c1 == 0 && c2 == 2) {
            coordDiagonal[1] = true;
        } else if (c1 == 2 && c2 == 1) {
            coordDiagonal[2] = true;
        } else if (c1 == 4 && c2 == 0) {
            coordDiagonal[3] = true;
        } else if (c1 == 4 && c2 == 2) {
            coordDiagonal[4] = true;
        }

       // if (c1 == 0 || c1 == 4) index = 2;
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

    // Retaurar as coordenas originais do programa para seguir
    private static void restauraCoord() {
        coordX = (int) coordOriginal;
        coordY = (int) Math.round((Double.parseDouble(String.valueOf(coordOriginal)) - coordX) * 10);
    }

    private static void restauraDir() {
        for (int i = 0; i < 3; i++) {
            coordLado[i] = false;
            coordCimaBaixo[i] = false;
        }

        for (int i = 0; i < 5; i++) {
            coordDiagonal[i] = false;
        }

    }
}

