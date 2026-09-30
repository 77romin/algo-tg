import java.util.HashSet;
import java.util.Set;

class Solution {

    static int answer, size;
    static int[] num;
    static boolean[] visited;
    static Set<Integer> set;

    public int solution(String numbers) {

        answer = 0;
        size = numbers.length();

        set = new HashSet<>();
        num = new int[size];
        visited = new boolean[size];

        for (int i = 0; i < size; i++) {
            num[i] = numbers.charAt(i) - '0';
        }

        makeNum(0);

        for (int n : set) {
            checkPrime(n);
        }

        return answer;
    }

    private static void makeNum(int number) {

        set.add(number);

        for (int i = 0; i < size; i++) {

            if (visited[i]) continue;

            visited[i] = true;

            makeNum(number * 10 + num[i]);

            visited[i] = false;
        }
    }

    private static void checkPrime(int n) {

        if (n < 2) return;

        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) return;
        }

        answer++;
    }
}