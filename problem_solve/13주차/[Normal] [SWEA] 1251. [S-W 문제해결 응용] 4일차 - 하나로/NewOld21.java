import java.util.*;
import java.io.*;

class Solution {

    static int N;
    static Node[] island;
    static boolean[] visited;

    public static void main(String args[]) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringTokenizer st;

        for (int test_case = 1; test_case <= T; test_case++) {

            N = Integer.parseInt(br.readLine());
            island = new Node[N];
            visited = new boolean[N];
            long ans = 0;

            int[] x = new int[N];
            int[] y = new int[N];

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                x[i] = Integer.parseInt(st.nextToken());
            }

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                y[i] = Integer.parseInt(st.nextToken());
            }

            for (int i = 0; i < N; i++) {
                island[i] = new Node(x[i], y[i]);
            }

            double E = Double.parseDouble(br.readLine());

            PriorityQueue<Point> pq = new PriorityQueue<>((n1, n2) -> Long.compare(n1.sum, n2.sum));
            pq.offer(new Point(0, 0));

            while (!pq.isEmpty()) {
                Point point = pq.poll();
                int idx = point.idx;
                long sum = point.sum;

                if (!visited[idx]) {
                    visited[idx] = true;
                    ans += sum;
                    for (int i = 0; i < N; i++) {
                        if (!visited[i]) {
                            long dx = (long) island[idx].x - island[i].x;
                            long dy = (long) island[idx].y - island[i].y;
                            long distance = dx * dx + dy * dy;

                            pq.offer(new Point(i, distance));
                        }
                    }
                }
            }

            System.out.println("#" + test_case + " " + (Math.round(ans * E)));
        }
    }
}

class Point {
    int idx;
    long sum;

    Point(int idx, long sum) {
        this.idx = idx;
        this.sum = sum;
    }
}

class Node {
    int x;
    int y;

    Node(int x, int y) {
        this.x = x;
        this.y = y;
    }
}