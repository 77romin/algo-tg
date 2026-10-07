import java.io.*;
import java.util.*;

public class Solution {
    static int N;
    static long[] x;
    static long[] y;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            N = Integer.parseInt(br.readLine());
            x = new long[N];
            y = new long[N];
            StringTokenizer st = new StringTokenizer(br.readLine());

            for (int i = 0; i < N; i++) {
                x[i] = Long.parseLong(st.nextToken());
            }

            st = new StringTokenizer(br.readLine());

            for (int i = 0; i < N; i++) {
                y[i] = Long.parseLong(st.nextToken());
            }

            double E = Double.parseDouble(br.readLine());
            long[] minEdge = new long[N];
            boolean[] visited = new boolean[N];

            Arrays.fill(minEdge, Long.MAX_VALUE);

            minEdge[0] = 0;
            long total = 0;

            for (int count = 0; count < N; count++) {
                int current = -1;
                long min = Long.MAX_VALUE;
                
                for (int i = 0; i < N; i++) { // 아직 선택하지 않은 섬 중 가장 작은 비용으로 연결 가능한 섬 찾기
                    if (!visited[i] && minEdge[i] < min) {
                        min = minEdge[i];
                        current = i;
                    }
                }

                visited[current] = true;
                total += minEdge[current];

                // 새로 선택한 섬을 기준으로 거리 갱신
                for (int next = 0; next < N; next++) {

                    if (visited[next]) continue;

                    long dx = x[current] - x[next];
                    long dy = y[current] - y[next];
                    long cost = dx * dx + dy * dy;

                    if (cost < minEdge[next]) {
                        minEdge[next] = cost;
                    }
                }
            }
            long answer = Math.round(total * E);

            sb.append("#").append(tc).append(" ").append(answer).append("\n");
        }

        System.out.print(sb);
    }
}
