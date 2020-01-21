package fr.umontpellier.iut.conquest;

public class BoardMemento {
    private Pawn[][] field;

    public BoardMemento(Pawn[][] field) {
        this.field = field;
    }

    public Pawn[][] getField() {
        return field;
    }
}
