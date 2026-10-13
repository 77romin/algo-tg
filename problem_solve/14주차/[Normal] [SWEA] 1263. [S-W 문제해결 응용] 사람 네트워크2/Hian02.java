/*
문제 정의

N명의 사람이 하나의 네트워크로 연결되어 있습니다.
두 사람이 직접 연결되어 있으면 거리는 1이고, 다른 사람들을 거쳐서 연결되어 있다면 거쳐가는 간선의 최소 개수가 두 사람 사이의 최단 거리가 됩니다.

사용자 i의 Closeness Centrality는 다음과 같습니다.
CC(i) = 모든 j에 대한 dist(i, j)의 합
즉 한 사람이 다른 모든 사람에게 가기 위한 최단 거리의 합입니다.

모든 사람의 CC 값을 계산한 뒤 그중 가장 작은 값을 구하는 문제입니다.
입력으로 주어지는 그래프는 하나의 연결 그래프이므로 모든 사람 사이의 경로가 존재합니다.
*/


/*
접근 방법

모든 간선의 가중치는 1이므로 BFS를 사용하면 하나의 시작점에서 다른 모든 정점까지의 최단 거리를 구할 수 있습니다.
각 사람을 한 번씩 시작점으로 설정해서 BFS를 수행합니다.

예를 들어 0번 사람에서 BFS를 수행하면
dist[0] = 0
dist[1] = 0번에서 1번까지의 최단 거리
dist[2] = 0번에서 2번까지의 최단 거리
...

이 거리들을 모두 더하면 0번 사람의 CC 값이 됩니다.

같은 방식으로 0번 사람, 1번 사람, 2번 사람... N-1번 사람
모두 BFS를 수행하고 가장 작은 거리 합을 정답으로 저장합니다.

입력은 인접 행렬 형태로 주어집니다.
BFS에서 인접 행렬을 그대로 사용하면 정점을 하나 꺼낼 때마다 N개의 정점을 전부 확인해야 하므로 비효율적입니다.
따라서 입력받을 때 값이 1인 경우만 인접 리스트에 저장합니다.

추가로 현재 BFS에서 계산한 거리 합이 이미 지금까지의 최소 CC 이상이라면 해당 정점이 정답이 될 수 없습니다.
남은 정점들의 거리는 모두 0 이상이므로 이후 합이 더 작아질 수 없기 때문입니다.
따라서 이 경우 BFS를 더 진행하지 않고 종료하는 가지치기도 사용할 수 있습니다.
*/


/*
문제 풀이
*/

import java.io.*;
import java.util.*;

public class Solution {
    /*
    빠른 입력을 위한 클래스입니다.
    N = 1000이면 하나의 테스트 케이스에서 인접 행렬만 1,000,000개의 숫자가 입력되므로 BufferedInputStream을 이용해 빠르게 입력받습니다.
    */
    static class FastScanner {
        private final InputStream in = System.in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0;
        private int len = 0;

        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;

                if (len <= 0) {
                    return -1;
                }
            }
            return buffer[ptr++];
        }

        int nextInt() throws IOException {
            int c;

            do {
                c = read();
            } while (c <= ' ');

            int sign = 1;

            if (c == '-') {
                sign = -1;
                c = read();
            }

            int num = 0;

            while (c > ' ') {
                num = num * 10 + (c - '0');
                c = read();
            }

            return num * sign;
        }
    }
    static int N;
    /*
    graph[i] = i번 사람과 직접 연결된 사람들의 목록입니다.
    */
    static ArrayList<Integer>[] graph;
    /*
    start번 사람에서 다른 모든 사람까지 BFS를 수행하고 최단 거리의 합을 반환합니다.

    best는 지금까지 구한 가장 작은 CC 값이며, 현재 거리 합이 best 이상이 되면 더 계산할 필요가 없으므로 바로 종료합니다.
    */
    static int bfs(int start, int best) {
        /*
        dist[i] = start에서 i까지의 최단 거리입니다.

        -1이면 아직 방문하지 않은 정점입니다.
        */
        int[] dist = new int[N];

        Arrays.fill(dist, -1);

        /*
        BFS Queue입니다.

        정점의 개수는 최대 N개이므로 int 배열을 Queue처럼 사용합니다.
        */
        int[] queue = new int[N];

        int front = 0;
        int rear = 0;

        /*
        시작 정점의 거리는 0입니다.
        */
        dist[start] = 0;
        queue[rear++] = start;
        /*
        최단 거리의 합입니다.
        */
        int sum = 0;
        /*
        BFS 수행
        */
        while (front < rear) {
            int current = queue[front++];
            /*
            현재 정점과 연결된 모든 정점을 확인합니다.
            */
            for (int next : graph[current]) {
                /*
                이미 방문했다면 다시 확인하지 않습니다.
                */
                if (dist[next] != -1) {
                    continue;
                }
                /*
                가중치가 모두 1이므로 현재 정점의 거리 + 1이 next까지의 최단 거리입니다.
                */
                dist[next] = dist[current] + 1;
                /*
                최단 거리 합에 추가합니다.
                */
                sum += dist[next];
                /*
                현재 거리 합이 이미 지금까지의 최소 CC 이상이라면 더 이상 정답이 될 수 없으므로 종료합니다.
                */
                if (sum >= best) {
                    return sum;
                }
                queue[rear++] = next;
            }
        }
        return sum;
    }


    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner();
        StringBuilder sb = new StringBuilder();

        int T = fs.nextInt();
        for (int tc = 1; tc <= T; tc++) {
            N = fs.nextInt();
            /*
            인접 리스트 초기화
            */
            graph = new ArrayList[N];

            for (int i = 0; i < N; i++) {
                graph[i] = new ArrayList<>();
            }


            /*
            N x N 인접 행렬을 입력받습니다.

            network[i][j] == 1이라면 i번 사람과 j번 사람이 연결되어 있으므로 인접 리스트에 j를 추가합니다.
            */
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    int connected = fs.nextInt();
                    if (connected == 1) {
                        graph[i].add(j);
                    }
                }
            }
            /*
            사람들의 CC 값 중 최솟값을 저장합니다.
            */
            int answer = Integer.MAX_VALUE;
            /*
            모든 사람을 한 번씩 시작점으로 BFS를 수행합니다.
            */
            for (int start = 0; start < N; start++) {
                int cc = bfs(start, answer);
                answer = Math.min(answer, cc);
            }
            sb.append("#").append(tc).append(" ").append(answer).append('\n');
        }
        System.out.print(sb);
    }
}


/*
시간복잡도

정점의 개수를 N, 간선의 개수를 E라고 하겠습니다.
인접 행렬을 입력받는 데 N * N개의 값을 확인해야 하므로 O(N^2)이 필요합니다.
하나의 정점에서 BFS를 수행하면 모든 정점과 간선을 한 번씩 확인하므로 O(N + E)입니다.

모든 N개의 정점에서 BFS를 수행하므로 O(N * (N + E))입니다.

따라서 전체 시간복잡도는 O(N^2 + N * (N + E))이며 일반적으로 O(N * (N + E))로 볼 수 있습니다.
그래프가 매우 촘촘한 최악의 경우 E가 O(N^2)이므로 O(N^3)까지 갈 수 있지만, 인접 행렬을 매 BFS마다 전부 훑는 방식보다 인접 리스트를 이용하면 실제 존재하는 간선만 확인할 수 있습니다.

핵심 정리

1. CC(i)는 i번 정점에서 다른 모든 정점까지의 최단 거리 합입니다.
2. 간선의 가중치가 모두 1이므로 BFS를 사용하면 최단 거리를 구할 수 있습니다.
3. 모든 정점을 시작점으로 BFS를 한 번씩 수행합니다.
4. 각 BFS에서 구한 거리 합 중 가장 작은 값을 정답으로 사용합니다.
5. 입력은 인접 행렬이지만 BFS를 효율적으로 수행하기 위해 인접 리스트로 변환합니다.
6. 현재 거리 합이 이미 기존 최소 CC 이상이면 더 이상 최소값이 될 수 없으므로 해당 BFS를 조기에 종료할 수 있습니다.
*/
