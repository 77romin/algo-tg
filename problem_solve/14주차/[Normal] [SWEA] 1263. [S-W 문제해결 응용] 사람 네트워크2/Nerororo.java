import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Solution {

    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());
        int INF = 1_000_000;

        for (int test_case = 1; test_case <= T; test_case++) {
            sb.append('#').append(test_case).append(' ');

            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());

            int[][] cc = new int[n][n];

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    int num = Integer.parseInt(st.nextToken());

                    if (i == j) {
                        cc[i][j] = 0;
                    } else if (num == 0) {
                        cc[i][j] = INF;
                    } else {
                        cc[i][j] = 1;
                    }
                }
            }

            // 플로이드 워셜
            for (int k = 0; k < n; k++) {
                for (int i = 0; i < n; i++) {
                    if (cc[i][k] == INF) continue;

                    for (int j = 0; j < n; j++) {
                        if (cc[k][j] == INF) continue;

                        cc[i][j] = Math.min(cc[i][j], cc[i][k] + cc[k][j]);
                    }
                }
            }

            int min = INF;

            for (int i = 0; i < n; i++) {
                int sum = 0;

                for (int j = 0; j < n; j++) {
                    sum += cc[i][j];
                }

                min = Math.min(min, sum);
            }

            sb.append(min).append('\n');
        }

        System.out.println(sb);
    }
}

/*
플로이드 위셜 n^3승의 시간 복잡도를 가지는데
뭔가 최적화 가능한 요소가 더 있어보인다.
 */