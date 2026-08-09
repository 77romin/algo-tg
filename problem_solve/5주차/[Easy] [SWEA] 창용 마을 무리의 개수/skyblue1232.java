import java.io.*;
import java.util.*;

public class skyblue1232 {

    static int N;
    static boolean[][] map;
    static boolean[] visited;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {

            StringTokenizer st = new StringTokenizer(br.readLine());

            N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());

            map = new boolean[N + 1][N + 1];
            visited = new boolean[N + 1];

            // 서로 아는 관계 입력
            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine());

                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());

                // 서로 아는 관계이므로 양방향
                map[a][b] = true;
                map[b][a] = true;
            }

            int count = 0;

            // 모든 사람 확인
            for (int i = 1; i <= N; i++) {

                // 아직 어느 무리에도 포함되지 않은 사람이라면
                if (!visited[i]) {
                    dfs(i);
                    count++;
                }
            }

            System.out.println("#" + tc + " " + count);
        }
    }

    static void dfs(int person) {

        visited[person] = true;

        for (int next = 1; next <= N; next++) {

            if (map[person][next] && !visited[next]) {
                dfs(next);
            }
        }
    }
}