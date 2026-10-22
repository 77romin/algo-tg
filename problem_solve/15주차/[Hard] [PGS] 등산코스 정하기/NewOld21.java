
import java.util.*;
import java.io.*;

class Solution {

    static List<Node>[] graph;
    static int[] intensity;
    static boolean[] isGate;
    static boolean[] isSummit;

    public int[] solution(int n, int[][] paths, int[] gates, int[] summits) {

        graph = new ArrayList[n + 1];
        intensity = new int[n + 1];
        isGate = new boolean[n + 1];
        isSummit = new boolean[n + 1];

        for (int i = 0; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] path : paths) {
            int a = path[0];
            int b = path[1];
            int w = path[2];

            graph[a].add(new Node(b, w));
            graph[b].add(new Node(a, w));
        }

        for (int gate : gates) {
            isGate[gate] = true;
        }

        for (int summit : summits) {
            isSummit[summit] = true;
        }

        Arrays.fill(intensity, Integer.MAX_VALUE);

        PriorityQueue<Node> pq = new PriorityQueue<>((n1, n2) -> Integer.compare(n1.sum, n2.sum));

        for (int gate : gates) {
            intensity[gate] = 0;
            pq.offer(new Node(gate, 0));
        }

        while (!pq.isEmpty()) {
            Node node = pq.poll();
            int idx = node.idx;
            int sum = node.sum;

            if (sum > intensity[idx]) {
                continue;
            }

            if (isSummit[idx]) {
                continue;
            }

            for (Node next : graph[idx]) {

                if (isGate[next.idx]) {
                    continue;
                }

                int max = Math.max(sum, next.sum);

                if (max < intensity[next.idx]) {
                    intensity[next.idx] = max;
                    pq.offer(new Node(next.idx, max));
                }
            }
        }

        Arrays.sort(summits);

        int minSummit = -1;
        int minIntensity = Integer.MAX_VALUE;

        for (int summit : summits) {
            if (intensity[summit] < minIntensity) {
                minIntensity = intensity[summit];
                minSummit = summit;
            }
        }

        return new int[]{minSummit, minIntensity};
    }
}

class Node {
    int idx;
    int sum;

    Node(int idx, int sum) {
        this.idx = idx;
        this.sum = sum;
    }
}
