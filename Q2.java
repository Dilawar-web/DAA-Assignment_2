import java.util.Arrays;

public class Q2{

    private static final int ALPHABET_SIZE = 256;

    static void preprocessBadCharacter(String pattern, int[] badChar) {
        Arrays.fill(badChar, -1);

        for (int i = 0; i < pattern.length(); i++) {
            badChar[pattern.charAt(i)] = i;
        }
    }

    static void preprocessGoodSuffix(String pattern, int[] shift, int[] borderPos) {

        int m = pattern.length();

        int i = m;
        int j = m + 1;

        borderPos[i] = j;

        while (i > 0) {

            while (j <= m && pattern.charAt(i - 1) != pattern.charAt(j - 1)) {

                if (shift[j] == 0)
                    shift[j] = j - i;

                j = borderPos[j];
            }

            i--;
            j--;

            borderPos[i] = j;
        }

        j = borderPos[0];

        for (i = 0; i <= m; i++) {

            if (shift[i] == 0)
                shift[i] = j;

            if (i == j)
                j = borderPos[j];
        }
    }
    static void search(String text, String pattern) {

        int n = text.length();
        int m = pattern.length();

        int[] badChar = new int[ALPHABET_SIZE];
        preprocessBadCharacter(pattern, badChar);

        int[] shift = new int[m + 1];
        int[] borderPos = new int[m + 1];

        preprocessGoodSuffix(pattern, shift, borderPos);

        int s = 0;
        int step = 1;
        java.util.ArrayList<Integer> matches = new java.util.ArrayList<>();

        System.out.println("Text    : " + text);
        System.out.println("Pattern : " + pattern);
        System.out.println();

        while (s <= n - m) {

            int j = m - 1;

            while (j >= 0 && pattern.charAt(j) == text.charAt(s + j))
                j--;

            System.out.println("-------------------------------------------------");
            System.out.println("Step " + step + " | Shift = " + s);
            System.out.println();

            String window = text.substring(s, s + m);

            System.out.println("Window   : " + window);
            System.out.println("Pattern  : " + pattern);

            if (j < 0) {

                System.out.println("Match    : Found at Index " + s);
                matches.add(s);

                int bcShift;

                if (s + m < n)
                    bcShift = m - badChar[text.charAt(s + m)];
                else
                    bcShift = 1;

                int gsShift = shift[0];

                int nextShift = Math.max(bcShift, gsShift);

                System.out.println("BC Shift : " + bcShift);
                System.out.println("GS Shift : " + gsShift);
                System.out.println("Next     : " + nextShift);
                System.out.println();

                s += nextShift;

                System.out.println("=================================================");

                for (int index : matches) {
                    System.out.println("Match Found at Index " + index);
                }

            } else {

                char textChar = text.charAt(s + j);
                char patternChar = pattern.charAt(j);

                int bcShift = Math.max(1, j - badChar[textChar]);
                int gsShift = shift[j + 1];

                int nextShift = Math.max(bcShift, gsShift);

                System.out.println("Mismatch : " + textChar + " != " + patternChar);
                System.out.println("BC Shift : " + bcShift);
                System.out.println("GS Shift : " + gsShift);
                System.out.println("Next     : " + nextShift);
                System.out.println();

                s += nextShift;
            }

            step++;
        }
    }
    public static void main(String[] args) {

        String text = "Insertion sort typically has a smaller constant factor than merge sort";
        String pattern = "sort";

        search(text, pattern);
        }
    }