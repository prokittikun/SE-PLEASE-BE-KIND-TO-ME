/**
 * 6510405334
 * Kittikun Buntoyut
 */

import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Board board = new Board(40);

        Die[] dice = new Die[2];
        for (int i = 0; i < 2; i++) {
            dice[i] = new Die();
        }
        ArrayList<Player> players = new ArrayList<Player>();
        players.add(new Player("Pro", dice, board));
        players.add(new Player("Mare", dice, board));
        players.add(new Player("JJ Natdanai", dice, board));
        players.add(new Player("James", dice, board));

        MGame game = new MGame(players, board, dice, 20);
        game.playGame();
    }
}

class MGame {

    private Die[] dice;
    private Board board;
    private int roundCount;
    private ArrayList<Player> players;
    private int n;
    public MGame(ArrayList<Player> players, Board board, Die[] dice, int n) {

        this.board = board;
        this.dice = dice;
        this.n = n;
        if (players.size() < 2 || players.size() > 8) {
            throw new IllegalArgumentException("ผู้เล่นขั้นต่ำต้องมี 2 คน และ ไม่เกิน 8 คน");
        }

        this.players = players;
        this.roundCount = 0;
    }

    public void playGame() {
        while (roundCount < this.n) {
            System.out.println("รอบที่ " + (roundCount+1));
            playRound();
            this.roundCount++;
        }
    }

    private void playRound() {
        for (Player player : players) {
            player.takeTurn();
        }
    }
}

class Die {
    private int faceValue;
    private Random rand;

    public Die() {
        this.faceValue = 1;
    }

    public void roll() {
        this.rand = new Random();
        this.faceValue = rand.ints(1, 6).findFirst().getAsInt();
    }

    public int getFaceValue() {
        return faceValue;
    }

}

class Board {
    private ArrayList<Square> squares;

    public Board(int size) {
        this.squares = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            Square square = new Square("Square " + i);
            this.squares.add(square);
        }
    }

    public Square getSquare(Square oldLoc, int fvTot) {
        int currentIndex = squares.indexOf(oldLoc);
        int newIndex = (currentIndex + fvTot) % squares.size();

        return squares.get(newIndex);
    }

    public Square getStartSquare() {
        return squares.get(0);
    }
}

class Square {
    private String name;

    public Square(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}

class Player {
    private Die[] dice;
    private Board board;
    private Piece piece;
    private String name;

    public Player(String name, Die[] dice, Board board) {
        this.name = name;
        this.dice = dice;
        this.piece = new Piece(board.getStartSquare());
        this.board = board;
    }

    public void takeTurn() {
        int fv = 0;
        for (int i = 0; i < dice.length; i++) {
            dice[i].roll();
            fv += dice[i].getFaceValue();
        }
        System.out.println("ผู้เล่น " + this.name + " ทอยลูกเต๋าได้ " + fv + " คะแนน");
        Square oldLoc = piece.getLocation();
        Square newLoc = board.getSquare(oldLoc, fv);
        System.out.println("เดินจาก " + oldLoc.getName() + " ไปยัง " + newLoc.getName());
        piece.setLocation(newLoc);
    }

    public String getName() {
        return this.name;
    }
}

class Piece {
    private Square location;

    public Piece(Square location) {
        this.location = location;
    }

    public Square getLocation() {
        return location;
    }

    public void setLocation(Square newLoc) {
        location = newLoc;
    }

}