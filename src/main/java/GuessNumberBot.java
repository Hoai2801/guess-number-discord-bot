import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.intent.Intent;

import java.util.concurrent.atomic.AtomicInteger;

public class GuessNumberBot {

    /**
     * This method initializes and runs a Discord bot that generates a random number and allows users to guess it.
     * The bot responds with hints and updates the random number when guessed correctly.
     */
    public static void main(String[] args) {
        // Insert your bot's token here
        String token = "MTE5MTM1MDU0OTM4MDA3OTY0Nw.GnKFaV.dCciXcPBCYGgeIqHkfxgCd-_L8rc8thiFGSFt4";

        // Create a new DiscordApi instance and log in with the bot's token
        DiscordApi api = new DiscordApiBuilder()
                .setToken(token)
                .addIntents(Intent.MESSAGE_CONTENT)
                .login()
                .join();

        // Generate a random number in the range from 0 to 99
        AtomicInteger answer = new AtomicInteger((int) (Math.random() * 100));

        // Listen to messages and respond to the "!letGuess" command
        api.addMessageCreateListener(event -> {
            if (event.getMessageContent().contains("!letGuess")) {
                int guess = Integer.parseInt(event.getMessageContent().substring(10));
                if (guess > answer.get()) {
                    event.getChannel().sendMessage("The correct answer is lower");
                } else if (guess < answer.get()) {
                    event.getChannel().sendMessage("The correct answer is higher");
                } else if (guess == answer.get()) {
                    event.getChannel().sendMessage("Yeah! That's it! -> " + answer);
                    event.getChannel().sendMessage("Now! Let's guess the order number");
                    answer.set((int) (Math.random() * 100));
                }
            }
        });

        // Print the invite URL of your bot
        System.out.println("You can invite the bot by using the following URL: " + api.createBotInvite());
    }
}
