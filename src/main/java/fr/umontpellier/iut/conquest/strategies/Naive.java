package fr.umontpellier.iut.conquest.strategies;

import fr.umontpellier.iut.conquest.Board;
import fr.umontpellier.iut.conquest.Move;
import fr.umontpellier.iut.conquest.Player;

import java.util.List;
import java.util.Random;

public class Naive implements Strategy {
    @Override
    public Move getMove(Board board, Player player) {
        List<Move> mouvement = board.getValidMoves(player);
        if (mouvement.isEmpty()){
            System.out.println("aucun coup valide possible");
            return null;
        }
        else{
            Random r = new Random();
            int valeur = r.nextInt((mouvement.size() - 1));
            //mouvement.get(0).toString();
            return mouvement.get(valeur);
        }
    }
}
