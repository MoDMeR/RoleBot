package de.foryasee.rolebot.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.security.auth.login.LoginException;

import de.foryasee.rolebot.core.CommandManager;
import de.foryasee.rolebot.core.DataSaver;
import de.foryasee.rolebot.core.ListenerManager;
import de.foryasee.rolebot.util.Logger;
import de.foryasee.rolebot.util.ShutdownHook;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

public class Main {

	private static String token = "";
	private static String prefix = "";

	private static JDA jda;
	
	private static boolean running = false;

	public static void main(String[] args) {
		new ShutdownHook();
		new DataSaver();
		Logger.startFileLogging();
		while(!running) {
			callCommand();
		}
	}
	
	private static void callCommand() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String[] cmd = {};
		try {
			cmd = reader.readLine().split(" ");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		switch (cmd[0]) {
		case "start":
			Logger.info("Starting Bot...");
			startBot();
			break;
		case "set":
			switch (cmd[1]) {
			case "token":
				DataSaver.saveToken(cmd[2]);
				token = cmd[2];
				Logger.info("Token saved");
				break;
			case "prefix":
				prefix = cmd[2];
				DataSaver.savePrefix(cmd[2]);
				Logger.info("Prefix saved");
				break;
			default:
				Logger.error("Unknown Command");
				break;
			}
			break;
		default:
			Logger.error("Unknown Command");
			break;
		}
	}

	private static void startBot() {
		prefix = DataSaver.loadPrefix();
		token = DataSaver.loadToken();
		JDABuilder builder = new JDABuilder(AccountType.BOT);
		builder.setToken(token);
		builder.setAutoReconnect(true);
		builder.setStatus(OnlineStatus.ONLINE);
		builder.setGame(new Game() {

			@Override
			public String getUrl() {
				return null;
			}

			@Override
			public GameType getType() {
				return GameType.DEFAULT;
			}

			@Override
			public String getName() {
				return prefix + "help";
			}
		});

		init(builder);

		try {
			jda = builder.buildBlocking();
			running = true;
			Logger.info("Bot started");
		} catch (LoginException | IllegalArgumentException | InterruptedException | RateLimitedException e) {
			e.printStackTrace();
		}
	}

	private static void init(JDABuilder builder) {
		new ListenerManager(builder);
		new CommandManager();
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public static String getPrefix() {
		return prefix;
	}

	public static void setPrefix(String prefix) {
		Main.prefix = prefix;
	}

}
