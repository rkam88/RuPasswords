package net.rusnet.sb.rupasswords;

import java.util.ArrayList;

public class PasswordGenerator {
    private Character[] passwordChars;

    private ArrayList<Character> lowerCaseLetters;
    private ArrayList<Character> upperCaseLetters;
    private ArrayList<Character> digits;
    private ArrayList<Character> symbols;

    public PasswordGenerator() {
        lowerCaseLetters = new ArrayList<>();
        upperCaseLetters = new ArrayList<>();
        digits = new ArrayList<>();
        symbols = new ArrayList<>();

        for (int i = 97; i <= 122; i++) {
            lowerCaseLetters.add((char) i);
        }
        for (int i = 65; i <= 90; i++) {
            upperCaseLetters.add((char) i);
        }
        for (int i = 48; i <= 57; i++) {
            digits.add((char) i);
        }

        for (int i = 33; i <= 47; i++) {
            symbols.add((char) i);
        }
        for (int i = 58; i <= 64; i++) {
            symbols.add((char) i);
        }
        for (int i = 91; i <= 96; i++) {
            symbols.add((char) i);
        }
        for (int i = 123; i <= 126; i++) {
            symbols.add((char) i);
        }
    }

    public String generatePassword(int length, boolean useCaps, boolean useDigits, boolean useSymbols) {
        //min length check
        int counter = 0;
        if (useCaps) counter++;
        if (useDigits) counter++;
        if (useSymbols) counter++;
        if (counter > length) throw new IllegalArgumentException();

        passwordChars = new Character[length];

        ArrayList<Character> possibleChars = new ArrayList<>();
        possibleChars.addAll(lowerCaseLetters);

        if (useCaps) {
            insertCharacter(pickCharacter(upperCaseLetters));
            possibleChars.addAll(upperCaseLetters);
        }
        if (useDigits) {
            insertCharacter(pickCharacter(digits));
            possibleChars.addAll(digits);
        }
        if (useSymbols) {
            insertCharacter(pickCharacter(symbols));
            possibleChars.addAll(symbols);
        }

        for (int i = 0; i < length; i++) {
            if (passwordChars[i] == null) {
                passwordChars[i] = pickCharacter(possibleChars);
            }
        }

        StringBuilder result = new StringBuilder();
        for (Character c: passwordChars) {
            result.append(c);
        }

        return result.toString();
    }

    private int generateNumber(int min, int max) {
        return (int) ((Math.random() * ((max - min) + 1)) + min);
    }

    private char pickCharacter(ArrayList<Character> array) {
        int position = generateNumber(0, array.size()-1);
        return array.get(position);
    }

    private void insertCharacter(char charToInsert) {
        int length = passwordChars.length;
        int position = generateNumber(0,length-1);

        boolean characterIsSet = false;
        do {
            if (passwordChars[position] == null) {
                passwordChars[position] = charToInsert;
                characterIsSet = true;
            } else {
                position++;
                if (position == length) position = 0;
            }
        } while (!characterIsSet);
    }



}
