package se.maxjonsson.days.december4;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.maxjonsson.Task;
import se.maxjonsson.utils.FileReader;
import se.maxjonsson.utils.IncrementableInt;

public class December4_TaskA implements Task {

    private List<String> input;
    private int result = 0;

    private Map<String, IncrementableInt> passPhrases;

    public December4_TaskA() {
        this.passPhrases = new HashMap<>();
        this.input  = FileReader.readLines("input/december4/input.txt");
    }

    public December4_TaskA(final List<String> input) {
        this.passPhrases = new HashMap<>();
        this.input = input;
    }

    @Override
    public void run() {
        int validPassphrases = 0;

        for (final String passPhrase : input) {
            if (passphraseIsValid(passPhrase)) {
                validPassphrases++;
            }
        }

        this.result = validPassphrases;
        System.out.println(String.format("Found %s valid passphrases.", validPassphrases));
    }

    private boolean passphraseIsValid(final String passphrase) {

        final List<String> passPhraseTokens = Arrays.asList(passphrase.split("\\s+"));
        final Map<String, IncrementableInt> tokens = new HashMap<>();

        for (final String token : passPhraseTokens) {
            if (tokens.containsKey(token)) {
                tokens.get(token).increment();
            }
            else {
                tokens.put(token, new IncrementableInt(1));
            }
        }

        return tokens.values().stream().allMatch(token -> token.get() == 1);
    }

    public int getResult() {
        return result;
    }

    @Override
    public String getTaskName() {
        return "Day 4: High-Entropy Passphrases";
    }

    @Override
    public String getTaskDescription() {
        return "A new system policy has been put in place that requires all accounts to use a passphrase instead of simply a password. A passphrase consists of a series of words (lowercase letters) separated by spaces.\n" +
               "\n" +
               "To ensure security, a valid passphrase must contain no duplicate words.\n" +
               "\n" +
               "For example:\n" +
               "\n" +
               "aa bb cc dd ee is valid.\n" +
               "aa bb cc dd aa is not valid - the word aa appears more than once.\n" +
               "aa bb cc dd aaa is valid - aa and aaa count as different words.\n" +
               "The system's full passphrase list is available as your puzzle input. How many passphrases are valid?";
    }
}

