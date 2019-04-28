package src.game.pojo;

import java.util.List;

/**
 * Pojo representing a card
 *
 * |---|
 * |K  |
 * |♢ ♢|
 * |  K|
 * |---|
 *
 */
public class Card {
	private Symbol symbol;
	private Suit naipe;
	private boolean isTrunfo;

	public Symbol getSymbol() {
		return symbol;
	}

	public Suit getNaipe()
	{
		return naipe;
	}

	public boolean isTrunfo() {
		return isTrunfo;
	}

	public void setIsTrunfo(boolean isTrunfo) {
		this.isTrunfo = isTrunfo;
	}

	public Card(Suit suit, Symbol symbol){
		this.naipe = suit;
		this.symbol = symbol;
	}

	public void print() {
		System.out.println("|---|");
		System.out.println("|" + this.getSymbol().getString() + "  |");
		System.out.println("|" + this.getNaipe().getString() + " " + this.getNaipe().getString() + "|");
		System.out.println("|  " + this.getSymbol().getString() + "|");
		System.out.println("|---|");
		System.out.println(" ");
	}

	public int compareTo(Card card, List<Suit> sortedNaipes) {
		if (this.isTrunfo() && card.isTrunfo()) {
			int pointsComparison = Integer.compare(this.getSymbol().getPoints(), card.getSymbol().getPoints());

			if (pointsComparison == 0) {
				return Integer.compare(this.getSymbol().getNumber(), card.getSymbol().getNumber());
			}

			return pointsComparison;
		} else if (!this.isTrunfo() && !card.isTrunfo()) {
			int naipeComparison = 1;
			for (Suit highestSuit : sortedNaipes) {
				naipeComparison = Boolean.compare(this.getNaipe() == highestSuit, card.getNaipe() == highestSuit);

				if (naipeComparison == 0) {
					int numberComparison = Integer.compare(this.getSymbol().getNumber(), card.getSymbol().getNumber());

					if (numberComparison == 0) {
						continue;
					}

					return numberComparison;
				}
				return naipeComparison;
			}

			return naipeComparison;

		} else {
			return Boolean.compare(this.isTrunfo, card.isTrunfo());
		}
	}

	public enum Suit {
		DIAMONDS("♢"),
		SPADES("♠"),
		HEARTS("♡"),
		CLUBS("♣");

		private String string;

		Suit(String string) {
			this.string = string;
		}

		public String getString() {
			return string;
		}
	}

	public enum Symbol {
		ACE("A", 11, 11),
		SEVEN("7", 10, 10),
		KING("K", 4, 9),
		JACK("J", 3, 8),
		QUEEN("Q", 2, 7),
		SIX("6", 0, 6),
		FIVE("5", 0, 5),
		FOUR("4", 0, 4),
		THREE("3", 0, 3),
		TWO("2", 0, 2);

		private String string;
		private int points;
		private int number;

		Symbol(String string, int points, int number) {
			this.string = string;
			this.points = points;
			this.number = number;
		}

		public String getString() {
			return string;
		}

		public int getPoints() {
			return points;
		}

		public int getNumber() {
			return number;
		}
	}
	
}
