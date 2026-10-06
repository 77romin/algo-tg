import java.io.*;
import java.util.*;

public class Solution {

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {

            int N = Integer.parseInt(br.readLine());

            long[] x = new long[N];
            long[] y = new long[N];

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                x[i] = Long.parseLong(st.nextToken());
            }

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                y[i] = Long.parseLong(st.nextToken());
            }

            double E = Double.parseDouble(br.readLine());

            boolean[] visited = new boolean[N];
            long[] minDist = new long[N];

            Arrays.fill(minDist, Long.MAX_VALUE);

            minDist[0] = 0;

            long sum = 0;

            for (int i = 0; i < N; i++) {

                int cur = -1;
                long min = Long.MAX_VALUE;

                // MST와 가장 싸게 연결 가능한 섬 찾기
                for (int j = 0; j < N; j++) {
                    if (!visited[j] && minDist[j] < min) {
                        min = minDist[j];
                        cur = j;
                    }
                }

                visited[cur] = true;
                sum += minDist[cur];

                // 새로 들어온 cur 기준으로 최소 거리 갱신
                for (int next = 0; next < N; next++) {

                    if (visited[next]) {
                        continue;
                    }

                    long dx = x[cur] - x[next];
                    long dy = y[cur] - y[next];

                    long dist = dx * dx + dy * dy;

                    if (dist < minDist[next]) {
                        minDist[next] = dist;
                    }
                }
            }

            long result = Math.round(sum * E);

            sb.append('#').append(tc).append(' ').append(result).append('\n');
        }

        System.out.print(sb);
    }
}