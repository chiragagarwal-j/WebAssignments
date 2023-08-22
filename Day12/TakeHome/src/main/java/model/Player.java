package model;

import cards.Card;

public class Player {
	private String name;
	private int bet;
	private Card chosenCard;
	private String chosenPosition;

	public Player(String name, int bet, String pos, String abbr) {
		this.name = name;
		this.bet = bet;

		this.chosenCard = new Card(abbr);
		this.chosenPosition = pos;
	}

	public boolean checkWin(String position, Card card) {
		return chosenPosition.equalsIgnoreCase(position) && chosenCard.equals(card);
	}

	public int getBet() {
		return this.bet;
	}

	public String getName() {
		return this.name;
	}

	public String getChosenCard() {
		return chosenCard.toString();
	}

	public String getChosenPosition() {
		return chosenPosition;
	}

	public String toString() {
		return String.format("%s %s %s %s", name, bet, chosenCard.toString(), chosenPosition);
	}
}
