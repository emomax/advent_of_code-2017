package se.maxjonsson.days.december8;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import se.maxjonsson.Task;
import se.maxjonsson.utils.FileReader;

public class December8_TaskA implements Task {

    private List<String> input;
    private int result = 0;

    private Map<String, Integer> registers;

    public December8_TaskA() {
        input = FileReader.readLines("input/december8/input.txt");
    }

    public December8_TaskA(final List<String> input) {
        this.input = new ArrayList<>(input);
    }

    @Override
    public void run() {
        registers = mapInstructions(input);
        result = registers.values().stream()
                .mapToInt(Integer::intValue)
                .max()
                .getAsInt();

        System.out.println(String.format("Highest value in registers: '%s'.", result));
    }

    private Map<String, Integer> mapInstructions(final List<String> input) {
        final String instructionPattern = "^(?<register>\\w+) (?<action>\\w{3}) (?<value>-?\\d+) if (?<comparator>\\w+) (?<operand>\\>|\\<|\\>\\=|\\<\\=|\\!\\=|\\=\\=) (?<comparatorValue>-?\\d+)$";
        final Map<String, Integer> registers = new HashMap<>();

        for (final String instruction : input) {
            final Matcher registerMatcher = Pattern.compile(instructionPattern).matcher(instruction);

            if (!registerMatcher.matches()) {
                throw new RuntimeException("Failed to parse instruction '" + instruction + "'!");
            }

            final String register = registerMatcher.group("register");
            final String action = registerMatcher.group("action");
            final int value = Integer.parseInt(registerMatcher.group("value"));
            final String comparator = registerMatcher.group("comparator");
            final String operand = registerMatcher.group("operand");
            final int comparatorValue = Integer.parseInt(registerMatcher.group("comparatorValue"));

            if (!registers.containsKey(comparator)) {
                registers.put(comparator, 0);
            }

            if (!registers.containsKey(register)) {
                registers.put(register, 0);
            }

            int change = compute(action, value, registers.get(comparator), operand, comparatorValue);
            registers.put(register, registers.get(register) + change);
        }

        return registers;
    }

    private int compute(final String action, final int value, final int toCompareWith, final String operand, final int comparatorValue) {

        if (compare(toCompareWith, comparatorValue, operand)) {
            return "inc".equals(action) ? value : -value;
        }

        return 0;
    }

    private boolean compare(int a, int b, String operand) {
        switch (operand) {
            case ">": return a > b;
            case ">=": return a >= b;
            case "<": return a < b;
            case "<=": return a <= b;
            case "!=": return a != b;
            case "==": return a == b;
            default: throw new RuntimeException(String.format("invalid operand '%s'", operand));
        }
    }

    public int getResult() {
        return result;
    }

    @Override
    public String getTaskName() {
        return "Day 8: I Heard You Like Registers";
    }

    @Override
    public String getTaskDescription() {
        return "You receive a signal directly from the CPU. Because of your recent assistance with jump instructions, it would like you to compute the result of a series of unusual register instructions.\n" +
               "\n" +
               "Each instruction consists of several parts: the register to modify, whether to increase or decrease that register's value, the amount by which to increase or decrease it, and a condition. If the condition fails, skip the instruction without modifying the register. The registers all start at 0. The instructions look like this:\n" +
               "\n" +
               "b inc 5 if a > 1\n" +
               "a inc 1 if b < 5\n" +
               "c dec -10 if a >= 1\n" +
               "c inc -20 if c == 10\n" +
               "These instructions would be processed as follows:\n" +
               "\n" +
               "Because a starts at 0, it is not greater than 1, and so b is not modified.\n" +
               "a is increased by 1 (to 1) because b is less than 5 (it is 0).\n" +
               "c is decreased by -10 (to 10) because a is now greater than or equal to 1 (it is 1).\n" +
               "c is increased by -20 (to -10) because c is equal to 10.\n" +
               "After this process, the largest value in any register is 1.\n" +
               "\n" +
               "You might also encounter <= (less than or equal to) or != (not equal to). However, the CPU doesn't have the bandwidth to tell you what all the registers are named, and leaves that to you to determine.\n" +
               "\n" +
               "What is the largest value in any register after completing the instructions in your puzzle input?";
    }
}

