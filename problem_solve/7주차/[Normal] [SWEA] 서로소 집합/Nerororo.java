import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Solution
{
    static int[] parent;

    public static void main(String args[]) throws Exception
    {
        //System.setIn(new FileInputStream("res/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        for(int test_case = 1; test_case <= T; test_case++)
        {
            sb.append('#').append(test_case).append(' ');

            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());

            parent = init(n);

            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(br.readLine());
                int calc = Integer.parseInt(st.nextToken());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());

                unionFind(calc, a, b, sb);
            }
            sb.append('\n');
        }
        System.out.println(sb.toString());
    }

    public static int[] init(int n) {
        int[] arr = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            arr[i] = i;
        }
        return arr;
    }

    public static void unionFind(int calc, int a, int b, StringBuilder sb) {
        if (calc == 0) {
            union(a, b);
        } else {
            sb.append(find(a) == find(b) ? 1 : 0);
        }
    }

    public static void union(int a, int b) {
        a = find(a);
        b = find(b);

        if (a != b) parent[b] = a;
    }

    public static int find(int num) {
        if (parent[num] == num) return num;
        return parent[num] = find(parent[num]);
    }
}

// 최악 O(log n)의 시간복잡도를 가짐