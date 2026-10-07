import java.io.*;
import java.util.*;

public class Solution {

    static class Edge implements Comparable<Edge> {
        int from;
        int to;
        long weight;

        Edge(int from, int to, long weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        // compare는 무조건 1, 0, -1 셋 중 하나를 반환하기 때문에 int형 반환 사용
        @Override
        public int compareTo(Edge o) {
            return Long.compare(this.weight, o.weight);
        }
    }

    static int[] parent;

    static int find(int x) {
        if (parent[x] == x) {
            return x;
        }

        return parent[x] = find(parent[x]);
    }

    static boolean union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);

        // 이미 같은 집합이면 연결시 사이클 발생
        if (rootA == rootB) {
            return false;
        }

        parent[rootB] = rootA;
        return true;
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {
            sb.append('#').append(test_case).append(' ');

            int N = Integer.parseInt(br.readLine());
            List<Edge> edges = new ArrayList<>();
            int[][] map = new int[N][2];

            // X 좌표
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                map[i][0] = Integer.parseInt(st.nextToken());
            }

            // Y 좌표
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                map[i][1] = Integer.parseInt(st.nextToken());
            }

            for (int i = 0; i < N - 1; i++) {
                for (int j = i + 1; j < N; j++) {
                    long dx = (long) map[j][0] - map[i][0];
                    long dy = (long) map[j][1] - map[i][1];

                    long weight = dx * dx + dy * dy;

                    edges.add(new Edge(i, j, weight));
                }
            }

            // 정렬
            Collections.sort(edges);

            // Union-Find 초기화
            parent = new int[N + 1];

            for (int i = 1; i <= N; i++) {
                parent[i] = i;
            }

            long sum = 0;
            int count = 0;

            // 가장 작은 간선부터 검사
            for (Edge edge : edges) {

                // 사이클이 발생하지 않는 경우
                if (union(edge.from, edge.to)) {
                    sum += edge.weight;
                    count++;

                    // MST 간선 수 = V - 1
                    if (count == N - 1) {
                        break;
                    }
                }
            }

            double E = Double.parseDouble(br.readLine());
            long result = Math.round(E * sum);

            sb.append(result).append('\n');
        }
        System.out.println(sb);
    }
}

/*
크루스칼 알고리즘 사용
이 문제에선 간선의 개수가 N(N-1)/2개로 모든 간선을 만들고 정렬하면 O(N^2 log N)의
시간복잡도를 가지게 된다.
때문에 프림을 사용하면 간선 객체를 직접 만들 필요 없이
배열을 기반으로 각 정점까지의 현재 최소 거리만 관리하면 된다.
 */