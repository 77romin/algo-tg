import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

class Solution {
    static boolean[] arr;
    static int[] prev, next;
    static Deque<Integer> stack;
    static int N, K;

    public String solution(int n, int k, String[] cmd) {
        N = n;
        K = k;

        arr = new boolean[N];
        stack = new ArrayDeque<>();

        init();

        StringTokenizer st;

        for (String str : cmd) {
            st = new StringTokenizer(str);
            String c = st.nextToken();

            if (c.equals("U")) {
                int X = Integer.parseInt(st.nextToken());

                for (int x = X; x > 0; x--) {
                    K = prev[K];
                }
            } else if (c.equals("D")) {
                int X = Integer.parseInt(st.nextToken());

                for (int x = X; x > 0; x--) {
                    K = next[K];
                }
            } else if (c.equals("C")) {
                arr[K] = true;
                stack.push(K);

                if (prev[K] != -1) next[prev[K]] = next[K];
                if (next[K] != -1) prev[next[K]] = prev[K];

                if (next[K] == -1) K = prev[K];
                else K = next[K];

            } else if (c.equals("Z")) {
                int num = stack.pop();
                arr[num] = false;

                if (prev[num] != -1) next[prev[num]] = num;
                if (next[num] != -1) prev[next[num]] = num;
            }
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < N; i++) {
            sb.append(arr[i] ? 'X' : 'O');
        }

        return sb.toString();
    }

    private void init() {
        prev = new int[N];
        next = new int[N];

        for (int i = 0; i < N; i++) {
            prev[i] = i - 1;
            next[i] = i + 1;
        }

        next[N - 1] = -1;
    }
}