package fr.umontpellier.iut.conquest;

import java.util.ArrayList;
import java.util.List;

/**
 * Modélise un plateau.
 */
public class Board {
    /**
     * Tableau des pions.
     */
    private Pawn[][] field;

    /**
     * Constructeur.
     *
     * @param size : la taille du plateau.
     */
    public Board(int size) {
        field = new Pawn[size][size];
    }

    /**
     * Constructeur pour Test.
     *
     * @param field : plateau prédéfini.
     */
    public Board(Pawn[][] field) {
        this.field = field;
    }

    public Board(Board b){
        Pawn[][] tab = b.getField();
        this.field = new Pawn[tab.length][tab.length];
        for(int i=0;i<tab.length;i++){
            for (int j=0;j<tab.length;j++){
                this.field[i][j]=tab[i][j];
            }
        }
    }

    /**
     * Les méthodes suivantes sont utilisées pour les tests automatiques. Il ne faut pas les utiliser.
     */
    public Pawn[][] getField() {
        return field;
    }

    /**
     * Retourne la taille du plateau.
     */
    public int getSize() {
        return field.length;
    }


    /**
     * Affiche le plateau.
     */
    public String toString() {
        int size = field.length;
        StringBuilder b = new StringBuilder();
        for (int r = -1; r < size; r++) {
            for (int c = -1; c < size; c++) {
                if (r == -1 && c == -1) {
                    b.append("_");
                } else if (r == -1) {
                    b.append("_").append(c);
                } else if (c == -1) {
                    b.append(r).append("|");
                } else if (field[r][c] == null) {
                    b.append("_ ");
                } else if (field[r][c].getPlayer().getColor() == 1) {
                    b.append("X ");
                } else {
                    b.append("O ");
                }
            }
            b.append("\n");
        }
        b.append("---");
        return b.toString();
    }

    /**
     * Initialise le plateau avec les pions de départ.
     * Rappel :
     * - player1 commence le jeu avec un pion en haut à gauche (0,0) et un pion en bas à droite.
     * - player2 commence le jeu avec un pion en haut à droite et un pion en bas à gauche.
     */
    public void initField(Player player1, Player player2) {
        int l=field.length;

        for (int i=0;i<l;i++) {
            for (int j=0;j<l;j++) {
                field[i][j]=null;
            }
        }

        field[0][0] = new Pawn(player1);
        field[l-1][l-1] = new Pawn(player1);
        field[0][l-1] = new Pawn(player2);
        field[l-1][0] = new Pawn(player2);

    }

    /**
     * Vérifie si un coup est valide.
     * Rappel :
     * - Les coordonnées du coup doivent être dans le plateau.
     * - Le pion bougé doit appartenir au joueur.
     * - La case d'arrivée doit être libre.
     * - La distance entre la case d'arrivée est au plus 2.
     */
    public boolean isValid(Move move, Player player) {
        int lengthB = field.length;

        if (((0 <= move.getRow1()) && (move.getRow1() < lengthB) &&
                (0 <= move.getColumn1()) && (move.getColumn1() < lengthB))) {
            if (((0 <= move.getRow2()) && (move.getRow2() < lengthB) &&
                    (0 <= move.getColumn2()) && (move.getColumn2() < lengthB))) {
                if (field[move.getRow1()][move.getColumn1()] != null) {
                    if (field[move.getRow1()][move.getColumn1()].getPlayer().equals(player)) {
                        if (field[move.getRow2()][move.getColumn2()]==null) {
                            return !(Math.abs(move.getRow1() - move.getRow2()) > 2) && !(Math.abs(move.getColumn1() - move.getColumn2()) > 2);
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }


    }

    /**
     * Déplace un pion.
     *
     * @param move : un coup valide.
     *             Rappel :
     *             - Si le pion se déplace à distance 1 alors il se duplique pour remplir la case d'arrivée et la case de départ.
     *             - Si le pion se déplace à distance 2 alors il ne se duplique pas : la case de départ est maintenant vide et la case d'arrivée remplie.
     *             - Dans tous les cas, une fois que le pion est déplacé, tous les pions se trouvant dans les cases adjacentes à sa case d'arrivée prennent sa couleur.
     */
    public void movePawn(Move move) {
        Pawn depart = field[move.getRow1()][move.getColumn1()];
        if (Math.abs(move.getRow1() - move.getRow2()) >= 2 || Math.abs(move.getColumn1() - move.getColumn2()) >= 2) {
            field[move.getRow1()][move.getColumn1()] = null;
            field[move.getRow2()][move.getColumn2()] = new Pawn(depart.getPlayer());
        } else {
            field[move.getRow2()][move.getColumn2()] = new Pawn(depart.getPlayer());
        }
        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j <= 2; j++) {
                if (0 <= move.getRow2() - 1 + i && move.getRow2() - 1 + i < field.length && 0 <= move.getColumn2() - 1 + j && move.getColumn2() - 1 + j < field.length) {
                    if (field[move.getRow2() - 1 + i][move.getColumn2() - 1 + j] != null) {
                        field[move.getRow2() - 1 + i][move.getColumn2() - 1 + j] = new Pawn(depart.getPlayer());
                    }

                }
            }

        }
    }

    /**
     * Retourne la liste de tous les coups valides de player.
     * S'il n'y a de coup valide, retourne une liste vide.
     */
    public List<Move> getValidMoves(Player player) {

        int length = field.length;
        ArrayList<Move> validMov = new ArrayList<Move>();
        Move moveA;

        for (int i=0;i<length;i++) {
            for (int j=0;j<length;j++) {
                if (field[i][j]!=null) {
                    if (field[i][j].getPlayer().equals(player)) {
                        for (int k=0;k<length;k++) {
                            for (int l=0;l<length;l++) {
                                moveA = new Move(i,j,k,l);
                                if (this.isValid(moveA,player)) {
                                    validMov.add(moveA);
                                }
                            }
                        }
                    }
                }
            }
        }


    return validMov;
    }

    /**
     * Retourne le nombre de pions d'un joueur.
     */
    public int getNbPawns(Player player) {
        int length = field.length;
        int nbPawn=0;
        for (int i=0;i<length;i++) {
            for (int j=0;j<length;j++) {
                if (field[i][j]!=null) {
                    if (field[i][j].getPlayer().equals(player)) {
                        nbPawn++;
                    }
                }
            }
        }
        return nbPawn;
    }

    /**
     *
     * @param player
     * @return l'évaluation de l'étape comme demandé avec Minmax (nbPaws player - nbPaws Other player)
     */
    public int evaluer (Player player){
        return this.getNbPawns(player) - this.getNbPawns(player.getGame().getOtherPlayer(player));
    }

    /**
     * Fonctions associées au memento (pour récupérer les board précedents)
     */

    /**
     * Fonction qui ajoute un board au memento
     * @return le memento créé
     */
    public BoardMemento saveToMemento() {
        Board boardBis = new Board(this);
        return new BoardMemento(boardBis.field);
    }

    /**
     * donne à board le tableau du memento passé en paramètre
     * @param memento
     */
    public void undoFromMemento(BoardMemento memento)
    {
        this.field = memento.getField();
    }
}
