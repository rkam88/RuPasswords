package net.rusnet.sb.rupasswords;

public class PasswordStrengthChecker {

    public PasswordStrength checkPasswordStrength(String password) {
        if (password.length() == 0) return PasswordStrength.EMPTY;

        boolean hasCaps = false;
        boolean hasDigit = false;
        boolean hasSymbol = false;
        int counter = 0;

        for (int i = 0; i < password.length(); i++) {
            if (Character.isUpperCase(password.charAt(i))) {
                hasCaps = true;
            } else if (Character.isDigit(password.charAt(i))) {
                hasDigit = true;
            } else if (!Character.isAlphabetic(password.charAt(i))) {
                hasSymbol = true;
            }
        }
        if (hasCaps) counter++;
        if (hasDigit) counter++;
        if (hasSymbol) counter++;



        if (password.length() >= 8) {
            switch (counter) {
                case 0:
                    return PasswordStrength.MEDIUM;
                case 1:
                    return PasswordStrength.NORMAL;
                case 2:
                    return PasswordStrength.GOOD;
                case 3:
                    return PasswordStrength.GREAT;
            }
        }
        if (password.length() >= 6 && counter > 0) return PasswordStrength.MEDIUM;
        return PasswordStrength.LOW;
    }
}
