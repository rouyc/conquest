package fr.umontpellier.iut.conquest;
import java.util.ArrayDeque;

import java.util.Deque;


public class BoardCaretaker {
    final Deque<BoardMemento> mementos = new ArrayDeque<>();

    public BoardMemento getMemento()
    {
        if (mementos.size() == 1){
            return mementos.getFirst();
        }
        else{
            return mementos.removeFirst();
        }
    }

    public void addMemento(BoardMemento memento)
    {
        mementos.addFirst(memento);
    }

    public int getSize(){
        return mementos.size();
    }

}

