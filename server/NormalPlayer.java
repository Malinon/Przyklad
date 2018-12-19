package chineseCheckers.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class NormalPlayer extends AbstractPlayer {
	byte number;
	Socket socket;
	BufferedReader input;
	PrintWriter output;
	String command;
	static private byte options[];

	private boolean correctOption(byte[] op) {
		if (op[0] == 2 || op[0] == 3 || op[0] == 6)
			if (op[1] >= 0 && op[1] <= op[0] - 1)
				return true;

		return false;
	}

	public NormalPlayer(Socket socket, byte number, byte players) {
		this.socket = socket;
		this.number = number;
		try {
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new PrintWriter(socket.getOutputStream(), true);
			output.println("WELCOME " + number);

			if (number == 0) {// Ustawienia gry
				do {
					options = new byte[2];
					output.println("Podaj ilość graczy, oraz ilość botów");
					command = input.readLine();
					options = Translator.translateOption(command);
				} while (!correctOption(options));
			} else
				output.println(options[0] + " " + (players - number - 1)); // ilosc
																			// graczy,
																			// ilosco
																			// graczy
																			// na
																			// ktorych
																			// czekamy

			// output.println("Waiting for opponent to connect");
		} catch (IOException e) {
			System.out.println("Player died: " + e);
		}
	}

	@Override
	public byte[] getMove() {
		try {
			command = input.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Translator.translateMove(command);
	}

	public byte getNumOfPlayers() {
		return options[0];
	}

	public byte getNumOfBots() {
		return options[1];
	}

	public void giveResponse(int n) {
		switch (n) {
		case 0:
			output.println("Nie poprawny ruch, podaj ruch ponownie");
			break;
		case 1:
			output.println("Ruch poprawny");
			break;
		case 2:
			output.println("Wygrales, gratulje, tak z szczerego serca");
			break;
		case 3:
			output.println("Podaj kolejny ruch");
			break;
		case 4:
			output.println("Poczekaj na swoja ture");
			break;

		}
	}

	public void startRound() {
		output.println("Twoja tura");

	}

	@Override
	public void inform(byte player, byte part1, byte field1, byte part2, byte field2) {
		System.out.println(Translator.translateInfo(player, part1, field1, part2, field2));
		output.println(Translator.translateInfo(player, part1, field1, part2, field2));
	}

	@Override
	public void close() {
		try {
			socket.close();
		} catch (IOException e) {

		}
	}

	/*
	 * public void countPlayers(int n){ output.println(n); }
	 */
}
