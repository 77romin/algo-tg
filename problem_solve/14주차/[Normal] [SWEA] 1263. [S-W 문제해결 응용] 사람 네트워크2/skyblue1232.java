import java.io.*;
import java.util.*;

public class Solution {
    static int N;
    static int[][] graph;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            graph = new int[N][N];

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    graph[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            int answer = Integer.MAX_VALUE;

            for (int i = 0; i < N; i++) {
                answer = Math.min(answer, bfs(i));
            }

            sb.append("#").append(tc).append(" ").append(answer).append("\n");
        }

        System.out.print(sb);
    }

    static int bfs(int start) {
        Queue<Integer> q = new ArrayDeque<>();
        int[] dist = new int[N];
        Arrays.fill(dist, -1);
        q.offer(start);
        dist[start] = 0;
        int sum = 0;

        while (!q.isEmpty()) {
            int cur = q.poll();

            for (int next = 0; next < N; next++) {
                if (graph[cur][next] == 1 && dist[next] == -1) {
                    dist[next] = dist[cur] + 1;
                    sum += dist[next];
                    q.offer(next);
                }
            }
        }

        return sum;
    }
}
