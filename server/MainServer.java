package chineseCheckers.server;

import chineseCheckers.gameStaff.Bot;
import chineseCheckers.gameStaff.Game;

import java.net.ServerSocket;
import java.util.Random;

public class MainServer {

	private boolean isHuman(byte i, byte numOfPlayers, byte numOfBots) {
		if (i == 0 || i > numOfBots)
			return true;
		else
			return false;

	}

	public static void main(String[] args) throws Exception {
		AbstractPlayer[] players = new AbstractPlayer[6];
		Random random = new Random();
		byte start;
		ServerSocket listener = new ServerSocket(8901);
		byte numOfPlayers;
		byte numOfBots;
		boolean[] correct;
		Game game;

		System.out.println("Server is Running");
		players[0] = new NormalPlayer(listener.accept(), (byte) 0, (byte) 0);
		numOfPlayers = players[0].getNumOfPlayers();
		numOfBots = players[0].getNumOfBots();

		start = (byte) random.nextInt(numOfPlayers);

		System.out.println("Players " + numOfPlayers + "Bots " + numOfBots);

		game = new Game(numOfPlayers);

		/**
		 * Ustawia graczy i boty
		 */
		byte i = 1;

		while (i <= numOfBots) {
			players[i] = new Bot(numOfPlayers, i, game.getPositionOfPlayer(i));// Jak
																				// wyliczyc
																				// pozycje
			/*
			 * for(int j =i; j>0;j--) players[i].countPlayers(numOfPlayers-i-1);
			 */
			i++;
		}

		while (i < numOfPlayers) {

			players[i] = new NormalPlayer(listener.accept(), i, numOfPlayers); // Musisz
																				// poinformowac
																				// klienta
																				// o
																				// jego
																				// pozycji
																				// na
																				// planszy
			/*
			 * for(int j =i; j>0;j--) players[i].countPlayers(numOfPlayers-i-1);
			 */
			i++;

			System.out.println("Dodalem gracza ");
		}

		byte stillPlay = (byte) (numOfPlayers - numOfBots);
		byte[] command = new byte[4];

		byte j;

		for (i = 0; i < numOfPlayers; i++) {
			if (i != start) {
				players[i].giveResponse(4);
			}
		}

		while (stillPlay > 1) {// Poku sa jeszcze gracze
			for (i = start; i < numOfPlayers; i++)
				if (players[i] != null) {

					players[i].startRound();
					while (true) {
						command = players[i].getMove();
						if (command == null) {
							players[i].giveResponse(1);
							game.resetSelect(i);
							break;
						} else {
							correct = game.isCorrect(i, command[0], command[1], command[2], command[3]);
							if (correct[0]) {
								game.perform(i, command[0], command[1], command[2], command[3]);

								for (j = 0; j < numOfPlayers; j++) {
									if (i != j) {
										System.out.println("daj ruch innym: " + j);
										players[j].inform(i, command[0], command[1], command[2], command[3]);
									}
								}

								if (game.hasFinished(i)) {
									players[i].giveResponse(2);
									players[i].close();
									players[i] = null;
									break;
								} else {
									if (correct[1]) {// Ruch poprawny i moze sie
														// jeszcze ruszyc
										players[i].giveResponse(3);
									} else {
										System.out.println("ruch poprawny");
										players[i].giveResponse(1);
										game.resetSelect(i);
										break;
									}

								}
							} else
								players[i].giveResponse(0);
						}
					}
					start = 0;

				}
		}
		listener.close();
	}
}
