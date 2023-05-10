package rockpaperscissors;

import java.io.File;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private Player player;
    private Random random;
    private File file;
    private Scanner scanner;

    public Game() {
        random = new Random();
        file = new File("rating.txt");
        scanner = new Scanner(System.in);
        player = createPlayer();
    }

    private Player createPlayer() {
        System.out.print("Enter your name: ");
        java.lang.String username = scanner.nextLine();
        System.out.println("Hello, " + username);
        int userscore = getScoreFromFile(username);

        return new Player(username, userscore);
    }

    private int getScoreFromFile(String name) {

        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNext()) {
                String[] parts = reader.nextLine().split(" ");
                String username = parts[0];
                int userscore = Integer.parseInt(parts[1]);

                if (name.equals(username)) {
                    return userscore;
                }
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        return 0;
    }

    public void play() {
        String[] options = getOptions();

        System.out.println("Okay, let's start");
        loop: while (true) {
            String input = scanner.next();
            switch (input) {
                case "!exit" -> {
                    System.out.println("Bye!");
                    break loop;
                }

                case "!rating" ->
                        System.out.println("Your rating: " + getScore());

                default -> {
                    if (!Arrays.asList(options).contains(input)) {
                        System.out.println("Invalid input");
                        continue;
                    }

                    String userChoice = input;
                    String compChoice = options[random.nextInt(options.length)];
                    String result = getResult(options, userChoice, compChoice);

                    switch (result) {
                        case "loss" -> System.out.println("Sorry, but the computer chose " + compChoice);
                        case "win" -> {
                            System.out.printf("Well done. The computer chose %s and failed%n", compChoice);
                            player.increaseScore(100);
                        }
                        case "draw" -> {
                            System.out.printf("There is a draw (%s)%n", compChoice);
                            player.increaseScore(50);
                        }
                    }
                }
            }
        }
    }

    public int getScore() {
        return player.getScore();
    }

    private String[] getOptions() {
        String options = scanner.nextLine();
        if (options.isEmpty()) {
            return new String[] {"rock", "paper", "scissors"};
        }

        return options.split(",");
    }

    private String getResult(String[] options, String userChoice, String compChoice) {
        if (userChoice.equals(compChoice)) {
            return "draw";
        }

        String[] optionsSorted = getSortedOptions(options, userChoice);
        int compChoiceIndex = Arrays.asList(optionsSorted).indexOf(compChoice);
        int middleIndex = optionsSorted.length / 2;

        return compChoiceIndex < middleIndex ? "loss" : "win";
    }

    private String[] getSortedOptions(String[] options, String userChoice) {
        int userChoiceIndex = Arrays.asList(options).indexOf(userChoice);
        String[] optionsSorted = new String[options.length - 1];

        System.arraycopy(options, userChoiceIndex + 1, optionsSorted, 0, options.length - 1 - userChoiceIndex);
        System.arraycopy(options, 0, optionsSorted, options.length - 1 - userChoiceIndex, userChoiceIndex);

        return optionsSorted;
    }
}
