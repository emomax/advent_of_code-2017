package se.maxjonsson.days.december4;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.maxjonsson.Task;
import se.maxjonsson.utils.FileReader;
import se.maxjonsson.utils.IncrementableInt;

public class December4_TaskB implements Task {

    private List<String> input;
    private int result = 0;

    private Map<String, IncrementableInt> passPhrases;

    public December4_TaskB() {
        this.passPhrases = new HashMap<>();
        this.input  = FileReader.readLines("input/december4/input.txt");
    }

    public December4_TaskB(final List<String> input) {
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
            // Sort to allow for grouping of anagrams
            // i.e. 'aja' and 'jaa' both equals as sorted 'aaj'
            final String sortedToken = getSorted(token);

            if (tokens.containsKey(sortedToken)) {
                tokens.get(sortedToken).increment();
            }
            else {
                tokens.put(sortedToken, new IncrementableInt(1));
            }
        }
        return tokens.values().stream().allMatch(token -> token.get() == 1);
    }

    private String getSorted(final String token) {
        final char[] rawArray = token.toCharArray();
        Arrays.sort(rawArray);

        return new String(rawArray);
    }

    public int getResult() {
        return result;
    }

    @Override
    public String getTaskName() {
        return "Day 4: High-Entropy Passphrases - Part Two";
    }

    @Override
    public String getTaskDescription() {
        return "--- Part Two ---\n" +
               "\n" +
               "For added security, yet another system policy has been put in place. Now, a valid passphrase must contain no two words that are anagrams of each other - that is, a passphrase is invalid if any word's letters can be rearranged to form any other word in the passphrase.\n" +
               "\n" +
               "For example:\n" +
               "\n" +
               "abcde fghij is a valid passphrase.\n" +
               "abcde xyz ecdab is not valid - the letters from the third word can be rearranged to form the first word.\n" +
               "a ab abc abd abf abj is a valid passphrase, because all letters need to be used when forming another word.\n" +
               "iiii oiii ooii oooi oooo is valid.\n" +
               "oiii ioii iioi iiio is not valid - any of these words can be rearranged to form any other word.\n" +
               "Under this new system policy, how many passphrases are valid?";
    }
}

