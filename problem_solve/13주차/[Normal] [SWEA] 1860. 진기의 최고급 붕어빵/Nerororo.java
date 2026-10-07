import java.io.BufferedReader;
//import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

class Solution
{
    static int[] visitor;
    static final String O = "Possible";
    static final String X = "Impossible";

    public static void main(String args[]) throws Exception
    {
//        System.setIn(new FileInputStream("res/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++)
        {
            sb.append('#').append(test_case).append(' ');

            st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());

            visitor = new int[N];

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                visitor[i] = Integer.parseInt(st.nextToken());
            }
            Arrays.sort(visitor);

            sb.append(isAble(M, K)).append('\n');
        }
        System.out.println(sb.toString());
    }

    static String isAble(int M, int K) {
        for (int i = 0; i < visitor.length; i++) {
            int time = visitor[i];
            int bread = time / M * K;
            if (bread < i + 1) {
                return X;
            }
        }
        return O;
    }
}

/*
정렬에 N lon N, isAble 순회에 N 의 복잡도를 가지기 때문에
총 시간복잡도는 O(N log N)이다.
 */