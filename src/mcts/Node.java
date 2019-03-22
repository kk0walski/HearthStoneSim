package mcts;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import game.Game;
import game.Move;

public class Node {

    private Deque<Move> untriedMoves;
    private Game game;
    private Move moveInNode;
    private Node parent;
    private List<Node> children;
    private int firstHeroWins;
    private int secondHeroWins;
    private int totalGames;

    public Node() {
        children = new ArrayList<>();
    }

    public Node(Game game) {
        this();
        this.game = game;
        untriedMoves = new ArrayDeque<Move>();
    }

    public Node(Node parent, Move moveInNode) {
        this();
        this.game = parent.game;
        this.parent = parent;
        this.moveInNode = moveInNode;
        untriedMoves = new ArrayDeque<Move>(parent.untriedMoves);
    }

    public void addChild(Node child) {
        this.children.add(child);
    }

    public void addFirstHeroWin() {
        firstHeroWins++;
        totalGames++;
    }

    public void addSecondHeroWin() {
        secondHeroWins++;
        totalGames++;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public List<Node> getChilds() {
        return children;
    }

    public void setChilds(List<Node> children) {
        this.children = children;
    }

    public Move getMoveInNode() {
        return moveInNode;
    }

    public void setMoveInNode(Move moveInNode) {
        this.moveInNode = moveInNode;
    }

    public int getFirstHeroWins() {
        return firstHeroWins;
    }

    public void setFirstHeroWins(int firstHeroWins) {
        this.firstHeroWins = firstHeroWins;
    }

    public int getSecondHeroWins() {
        return secondHeroWins;
    }

    public void setSecondHeroWins(int secondHeroWins) {
        this.secondHeroWins = secondHeroWins;
    }

    public int getTotalGames() {
        return totalGames;
    }

    public void setTotalGames(int totalGames) {
        this.totalGames = totalGames;
    }

    public Deque<Move> getUntriedMoves() {
        return untriedMoves;
    }

    public void setUntriedMoves(Deque<Move> untriedMoves) {
        this.untriedMoves = untriedMoves;
    }
}
