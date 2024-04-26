package ru.gb.core.lesson2.Hw2.players;


import ru.gb.core.lesson2.Hw2.mechanics.Turn;

public abstract class Player {
    private String name;
    private char dot;
    private Turn turn;

    public Player(String name,char dot) {
        this.name = name;
        this.dot = dot;
        this.turn = new Turn();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Turn getTurn() {
        return turn;
    }

    public void setTurnCoordinate(int x, int y) {
        this.turn.setX(x);
        this.turn.setY(y);
    }

    public char getDot() {
        return dot;
    }


}