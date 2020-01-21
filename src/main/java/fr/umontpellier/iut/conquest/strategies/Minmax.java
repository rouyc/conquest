package fr.umontpellier.iut.conquest.strategies;

import fr.umontpellier.iut.conquest.Board;
import fr.umontpellier.iut.conquest.Move;
import fr.umontpellier.iut.conquest.Player;

import java.util.ArrayList;
import java.util.List;

public class Minmax implements Strategy {
    private int IALevel;

    public Minmax(int IALevel) {
        this.IALevel = IALevel;
    }

    @Override
    public Move getMove(Board board, Player player) {
        List<Move> moveValid = board.getValidMoves(player);
        Move mouvement = moveValid.get(0);
        int valeur = Integer.MIN_VALUE;
        if (this.IALevel%2 == 0){
            valeur = Integer.MAX_VALUE;
        }
        int res = 0;
        for(Move move : moveValid) {
            //System.out.println(move.toString());
            Board boardCourant = new Board(board);
            boardCourant.movePawn(move);
            res = Minmax(this.IALevel-1,player,boardCourant,true);
            //System.out.println(res);
            if (this.IALevel%2 == 0){
                if ( res < valeur){
                    valeur = res;
                    mouvement = move;
                }
            }
            else{
                if ( res > valeur){
                    valeur = res;
                    mouvement = move;
                }
            }
        }
        //System.out.println(mouvement.toString());
        return mouvement;
    }

    public int Minmax (int profondeur, Player joueurCourant, Board boardCourant, boolean joueurActuel) {
        List<Move> mouvement = boardCourant.getValidMoves(joueurCourant);
        int value = 0;
        if (profondeur == 0 || mouvement.isEmpty()){
            return boardCourant.evaluer(joueurCourant);
        }
        if (joueurActuel) {
            value = Integer.MIN_VALUE;
            for (Move move : mouvement) {
                Board board = new Board(boardCourant);
                board.movePawn(move);
                int valeur = Minmax(profondeur-1,joueurCourant.getGame().getOtherPlayer(joueurCourant),board,false);
                value = max(value,valeur);
            }
        }
        else{
            value = Integer.MAX_VALUE;
            for (Move move : mouvement) {
                Board board = new Board(boardCourant);
                board.movePawn(move);
                int valeur = Minmax(profondeur-1,joueurCourant.getGame().getOtherPlayer(joueurCourant),board,true);
                value = min(value,valeur);
            }
        }
        return value;
    }

    public int max (int value,int minmax){
        if (value > minmax){
            return value;
        }
        else {
            return minmax;
        }
    }

    public int min (int value,int minmax){
        if (value < minmax){
            return value;
        }
        else {
            return minmax;
        }
    }
}
