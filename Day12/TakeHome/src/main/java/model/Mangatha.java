package model;

import java.util.ArrayList;
import java.util.List;

import cards.Card;
import cards.Deck;

public class Mangatha {
	private Deck deck;
	private List<Card> inPile;
	private List<Card> outPile;
	private static Mangatha inst;
	private List<Player> players;
	private boolean isGameStart;
	private Player winner;
	private boolean isWinnerAvailable;

	public static Mangatha get() {
		if (inst == null)
			inst = new Mangatha();
		return inst;
	}

	public void init() {
		reset();
	}

	public void performAction() {
		inPile.add(deck.drawTop());
		outPile.add(deck.drawTop());
	}

	public List<Card> getInPile() {
		return inPile;
	}

	public List<Card> getOutPile() {
		return outPile;
	}

	public void addPlayer(String name, int bet, String pos, String abbr) {
		Player player = new Player(name, bet, pos, abbr);
		players.add(player);
	}

	public void setIsGameStart() {
		this.isGameStart = true;
	}

	public void reset() {
		deck = new Deck();
		inPile = new ArrayList<>();
		outPile = new ArrayList<>();
		players = new ArrayList<>();
		this.isGameStart = false;
		this.winner = null;
		this.isWinnerAvailable = false;
		deck.shuffle();
	}

	public boolean getIsGameStart() {
		return isGameStart;
	}

	public List<Player> getPlayers() {
		return this.players;
	}

	public boolean isInsidePile(Player player, String type) {
		for (Card card : inPile) {
			if (player.checkWin(type, card)) {
				return true;
			}
		}
		return false;
	}

	public int accumulateBet() {
		return players.stream().mapToInt(p -> p.getBet()).sum();
	}

	public Player getWinner() {
		for (Player player : players) {
			if (isInsidePile(player, "IN") || isInsidePile(player, "OUT")) {
				winner = new Player(player.getName(), accumulateBet(), player.getChosenPosition(),
						player.getChosenCard());
				isGameStart = false;
				isWinnerAvailable = true;
				break;
			}
		}
		return winner;
	}

	public boolean getIsWinnerAvailable() {
		return isWinnerAvailable;
	}
}
