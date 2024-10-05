package m;

import dev.failsafe.internal.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TestTest {

    private static final Logger log = LoggerFactory.getLogger(TestTest.class);

    @Test
    public void testSortArray() {
        int[] array = {3, 0, 7, 2, 0, 2, 2, 4, 1, 4, 3, 1, 1, 4, 5, 8, 4, 9};

        for (int i = 1; i < array.length - 1; i++) {
            for (int index = 0; index < array.length - i; index++) {
                if (array[index + 1] < array[index]) {
                    int temp = array[index];
                    array[index] = array[index + 1];
                    array[index + 1] = temp;
                }
            }
        }

        for (int a : array) {
            System.out.print(a);
        }
    }

    @Test
    public void testPalindrome() {
        String text = "ABtestitsetBA";
        assertThat("Error", isPalindrome(text), is(true));
    }

    private boolean isPalindrome(String text) {
        int length = text.length();
        char[] textArray = text.toCharArray();

//        for (int index = 0; index < length / 2; index++) {
//            if (textArray[index] != textArray[length - index - 1]) {
//                return false;
//            }
//        }
//
//        return true;

        StringBuffer reverse = new StringBuffer(text).reverse();
        return IntStream.range(0, length / 2).allMatch(index -> textArray[index] != textArray[length - index - 1]);
    }

    @Test
    public void maxSubArraySum() {
        int[] array = {3, 0, 7, 2, 0, 2, 2, 4, 1, 4, 3, 1, 1, 4, 5, 8, 4, 9};

        System.out.println(Arrays.stream(IntStream.range(0, array.length - 2).map(i -> array[i] + array[i+1]).toArray()).max().getAsInt());
    }

    @Test
    public void testParentheses() {
        String text = "ABte()(stit   )setBA";
        assertThat("Error", isBalanced(text), is(true));
    }

    private boolean isBalanced(String text) {
        int openCount = 0;
        int closeCount = 0;
        char[] charArray = text.toCharArray();
        for (char c : charArray) {
            if (c == '(') openCount++;
            if (c == ')') {
                closeCount++;
                if (closeCount > openCount) return false;
            }
        }
        return openCount == closeCount;
    }

    @Test
    public void testInversionCount() {
        int[] array = {3, 0, 7, 2};
        int count = 0;
        int count2 = 0;

        for (int i = 0; i < array.length - 1; i++) {
            if (array[i+1] < array[i]) count++;
        }

        for (int i = 0; i < array.length - 1; i++) {
            for (int j = i; j < array.length - 1; j++) {
                if (array[j+1] < array[i]) count2++;
            }
        }

        System.out.println(count);
        System.out.println(count2);
        System.out.println(IntStream.range(0, array.length  - 1).filter(i -> array[i+1] < array[i]).count());
        System.out.println(IntStream.range(0, array.length - 1).flatMap(i -> IntStream.range(i, array.length - 1).filter(j -> array[j+1] < array[i])).count());
    }

    @Test
    public void oddFiltering() {
        int[] array = {3, 0, 7, 2, 13, 10, 17, 12, 20, 22, 32, 4, 1, 14, 23, 11, 21, 24, 5, 8, 34, 9};
        Arrays.stream(array).filter(e -> e % 2 == 1).forEach(e -> System.out.println(e));
    }

    @Test
    public void max() {
        int[] array = {3, 0, 7, 2, 13, 10, 17, 12, 20, 22, 32, 4, 1, 14, 23, 11, 21, 24, 5, 8, 34, 9};
        System.out.println(Arrays.stream(array).max().getAsInt());
        System.out.println(Arrays.stream(array).reduce((a, b) -> a > b? a:b).getAsInt());

        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) max = array[i];
        }

        System.out.println(max);
    }

    @Test
    public void symbolsCount() {
        List<String> list = List.of(
                "There's a lady who's sure all that glitters is gold",
                "And she's buying a stairway to Heaven",
                "When she gets there she knows, if the stores are all closed",
                "With a word she can get what she came for",
                "Ooh, ooh, and she's buying a stairway to Heaven"
        );

       System.out.println(list.stream().map(e -> e.replaceAll("\\s", "")).map(String::length).reduce((a, b) -> a + b).get());
       System.out.println(list.stream().map(e -> e.replaceAll("\\s", "")).mapToInt(String::length).sum());

       int count = 0;
       for (String str : list) {
           str = str.replaceAll("\\s", "");
           count += str.length();
       }
       System.out.println(count);
    }

    @Test
    public void avg() {
        int[] array = {3, 0, 7, 2, 13, 10, 17, 12, 20, 22, 32, 4, 1, 14, 23, 11, 21, 24, 5, 8, 34, 9};
        System.out.println(Arrays.stream(array).average().getAsDouble());

        double avg = (double) Arrays.stream(array).sum() / array.length;

        System.out.println(avg);
    }

    @Test
    public void linesSorting() {
        List<String> list = List.of(
                "There's a lady who's sure all that glitters is gold",
                "And she's buying a stairway to Heaven",
                "When she gets there she knows, if the stores are all closed",
                "With a word she can get what she came for",
                "Ooh, ooh, and she's buying a stairway to Heaven"
        );

        List<String> sortedList = list.stream().sorted(Comparator.comparing(String::length)).toList();
        System.out.println(sortedList);

        sortedList = new ArrayList<>();
        List<String> initialList = list.stream().collect(Collectors.toList());
        for (int i = 0; i < list.size(); i++) {
            int index = getMinLengthIndex(initialList);
            sortedList.add(initialList.get(index));
            initialList.remove(index);
        }

        System.out.println(sortedList);
    }

    private int getMinLengthIndex(List<String> list) {
        int minLengthIndex = 0;
        for (int index = 1; index < list.size(); index++) {
            if (list.get(index).length() < list.get(minLengthIndex).length()) minLengthIndex = index;
        }
        return minLengthIndex;
    }

    @Test
    public void isAllPositive() {
        int[] array = {3, 0, 7, 2, 13, 10, 17, 12, 20, 22, 32, 4, 1, 14, 23, 11, 21, 24, 5, 8, 34, 9};
        boolean isAllPositive = Arrays.stream(array).noneMatch(e -> (e < 0));
        assertThat("All positive", isAllPositive, is(true));

        for (int i : array) {
            if (i < 0) {
                isAllPositive = false;
                break;
            }
        }
        assertThat("All positive", isAllPositive, is(true));
    }

    private String prefixJoin(List<String> input, String prefix) {
        return String.join(",", input.stream().filter(s -> s.startsWith(prefix)).toList());
    }

    @Test
    public void powSum() {
        int[] array = {3, 0, 7, 2, 13, 10, 17, 12, 20, 22, 32, 4, 1, 14, 23, 11, 21, 24, 5, 8, 34, 9};
        System.out.println(Arrays.stream(array).map(e -> e*e).sum());

        int sum = 0;
        for (int element : array) {
            sum += Math.pow(element, 2);
        }

        System.out.println(sum);
    }

    @Test
    public void firstLongString() {
        List<String> list = List.of(
                "There's a lady who's sure all that glitters is gold",
                "And she's buying a stairway to Heaven",
                "When she gets there she knows, if the stores are all closed",
                "With a word she can get what she came for",
                "Ooh, ooh, and she's buying a stairway to Heaven"
        );

        String firstLongString = list.stream().filter(s -> s.length() > 158).findFirst().orElse("no such a line");
        System.out.println(firstLongString);
    }

    @Test
    public void printDuplicates() {
        int[] array = {3, 0, 7, 2, 0, 2, 2, 4, 1, 4, 3, 1, 1, 4, 5, 8, 4, 9};
        Map<Integer, Integer> elements = new HashMap<>();
        List<Integer> duplicates = new ArrayList<>();

        for (int e : array) {
            if (elements.keySet().contains(e)) elements.put(e, elements.get(e) + 1);
            else elements.put(e, 1);
        }
        for (int e : elements.keySet()) {
            if (elements.get(e) > 1) duplicates.add(e);
        }

        System.out.println(duplicates);

        System.out.println(
                Arrays.stream(array).boxed().collect(Collectors.groupingBy(e -> e, Collectors.counting()))
                .entrySet().stream().filter(e -> e.getValue() > 1).map(e -> e.getKey()).toList()
        );
    }

    @Test
    public void reverseNumber() {
        int initialNumber = 1844;
        int reversedNumber = 0;

        while (initialNumber != 0) {
            reversedNumber = 10 * reversedNumber + initialNumber % 10;
            initialNumber = initialNumber / 10;
        }

        System.out.println(reversedNumber);
    }

    @Test
    public void primeNumber() {
        int number = 21;
        System.out.println(!IntStream.range(2, number / 2).anyMatch(n -> number % n == 0));
    }

    @Test
    public void twoMax() {
        int[] array = {3, 0, 7, 2, 13, 10, 17, 12, 20, 22, 32, 4, 1, 14, 23, 11, 21, 24, 5, 8, 34, 9};
        int[] sortedArray = Arrays.stream(array).sorted().toArray();
        System.out.println(sortedArray[sortedArray.length-1]);
        System.out.println(sortedArray[sortedArray.length-2]);

        System.out.println(IntStream.of(array).boxed().sorted((a, b) -> b.compareTo(a)).limit(2).toList());
    }

    @Test
    public void longestSubstring() {
        String str = "sdkjgsuydhclskvnhjvftyfjaknvhvyusgchaklcnsdfhuewfhcoslnbvdyugwiuchlxjbvduyogvsljdvbdjfyveubcjvbyd";
        char[] chars = str.toCharArray();
        int maxLength = 0;
        int start = 0;
        int end = 0;

        for (int i = 0; i < str.length() - 1; i++) {
            for (int j = i + 1; j < str.length(); j++) {
                if ((chars[j] == chars[i])) {
                    if (j - i > maxLength) {
                        maxLength = j - i;
                        end = j;
                        start = i;
                    }
                    break;
                }
            }
        }

        System.out.println(str.substring(start, end));
        System.out.println(maxLength);
    }

    @Test
    public void isNumberPalindrome() {
        int initialNumber = 389889763;
        int reversedNumber = 0;
        int n = initialNumber;

        while (n != 0) {
            reversedNumber = 10 * reversedNumber + n % 10;
            n = n / 10;
        }
        System.out.println(initialNumber==reversedNumber);
    }

    @Test
    public void swap() {
        int a = 670;
        int b = 0;

        System.out.println(a);
        System.out.println(b);

        a += b;
        b = a - b;
        a -= b;

        System.out.println(a);
        System.out.println(b);
    }

    @Test
    public void reverseString() {
        String a = "Sraka";

        System.out.println(new StringBuffer(a).reverse());

        char[] b = a.toCharArray();
        int left = 0, right = b.length -1;
        while (left < right) {
            char temp = b[right];
            b[right] = b[left];
            b[left] = temp;
            left++;
            right--;
        }

        System.out.println(new String(b));
    }

    @Test
    public void numbers() {
        int[] array = {3, 0, 7, 2, 13, 10, 17, 12, 20, 22, 32, 4, 1, 14, 23, 11, 21, 24, 5, 8, 34, 9};

        // even
        System.out.println(IntStream.of(array).boxed().filter(e -> e % 2 == 0).toList());

        // count of divisible by 3
        System.out.println(IntStream.of(array).boxed().filter(e -> e % 3 == 0).count());

        // rows
        System.out.println(Arrays.stream(array).mapToObj(String::valueOf).collect(Collectors.joining(",")));
        List<String> newList = IntStream.of(array).mapToObj(StringBuffer::new).map(e -> e.toString()).collect(Collectors.toList());

        // sorting desc
        System.out.println(Arrays.stream(array).boxed().sorted((a, b) -> b - a).toList());
    }

    @Test
    public void strings() {
        List<String> list = List.of(
                "There's a lady who's sure all that glitters is gold",
                "And she's buying a stairway to Heaven",
                "When she gets there she knows, if the stores are all closed",
                "With a word she can get what she came for",
                "Ooh, ooh, and she's buying a stairway to Heaven");

        // contains substring
        System.out.println(list.stream().allMatch(e -> e.contains("o")));

        // sum of rows length's
        System.out.println(list.stream().mapToInt(r -> r.length()).sum());

        // new list of duplicates only
        System.out.println(
                list.stream().collect(Collectors.groupingBy(r -> r, Collectors.counting()))
                        .entrySet().stream().filter(r -> r.getValue() > 1).collect(Collectors.toList())
                );

        // avg
        System.out.println(list.stream().mapToInt(r -> r.length()).average().getAsDouble());

        // prefix
        System.out.println(list.stream().anyMatch(e -> e.startsWith("And")));
    }

    @Test
    public void sumInRange() {
        System.out.println(IntStream.range(1, 5).sum());
    }
}
