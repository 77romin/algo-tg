import java.io.*;
import java.util.*;

public class skyblue1232 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder answer = new StringBuilder();

        int[][] deltasCross = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            int N = Integer.parseInt(br.readLine());

            int[][] map = new int[N][N];
            int[][] minCost = new int[N][N];

            for (int r = 0; r < N; r++) {
                String line = br.readLine();

                for (int c = 0; c < N; c++) {
                    map[r][c] = line.charAt(c) - '0';
                    minCost[r][c] = Integer.MAX_VALUE;
                }
            }

            PriorityQueue<int[]> pq =
                    new PriorityQueue<>(Comparator.comparingInt(node -> node[2]));

            minCost[0][0] = 0;
            pq.offer(new int[]{0, 0, 0});

            while (!pq.isEmpty()) {
                int[] current = pq.poll();

                int r = current[0];
                int c = current[1];
                int cost = current[2];

                if (cost > minCost[r][c]) continue;
                if (r == N - 1 && c == N - 1) break;

                for (int d = 0; d < deltasCross.length; d++) {
                    int nr = r + deltasCross[d][0];
                    int nc = c + deltasCross[d][1];

                    if (!isIn(nr, nc, N)) continue;

                    int nextCost = cost + map[nr][nc];

                    if (nextCost < minCost[nr][nc]) {
                        minCost[nr][nc] = nextCost;
                        pq.offer(new int[]{nr, nc, nextCost});
                    }
                }
            }

            answer.append('#').append(tc).append(' ')
                  .append(minCost[N - 1][N - 1]).append('\n');
        }

        System.out.print(answer);
    }

    static boolean isIn(int r, int c, int N) {
        return r >= 0 && r < N && c >= 0 && c < N;
    }
}