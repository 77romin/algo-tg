import java.util.*;
import java.io.*;

class Solution {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static long answer;
    
    private static int n;
    private static double E;
    private static Node[] node;
    private static long[][] edge;
    private static long[] dp;
    
    private static PriorityQueue<Node> pq;
    
    private static class Node implements Comparable<Node> {
        int idx;
        long x, y, dist;
        
        Node(int idx, long x, long y, long dist) {
            this.idx = idx;
            this.x = x;
            this.y = y;
            this.dist = dist;
        }

        @Override
        public int compareTo(Node o) {
            return Long.compare(this.dist, o.dist);
        }
    }
    
    private static void init() throws IOException {
        n = Integer.parseInt(br.readLine().trim());
        
        // input x list
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        long[] x = new long[n];
        for(int i = 0; i < n; i++)
            x[i] = Long.parseLong(st.nextToken());
        
        // input y list
        st = new StringTokenizer(br.readLine().trim());
        long[] y = new long[n];
        for(int i = 0; i < n; i++)
            y[i] = Long.parseLong(st.nextToken());
        
        // input E
        E = Double.parseDouble(br.readLine().trim());
        
        // make node list
        node = new Node[n];
        for(int i = 0; i < n; i++)
            node[i] = new Node(i, x[i], y[i], 0);
            
        // calculate distance of each nodes
        edge = new long[n][n];
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(i == j) continue;
                if(edge[i][j] != 0) continue;
                long dx = node[i].x - node[j].x;
                long dy = node[i].y - node[j].y;
                long distSqr = dx * dx + dy * dy;
                edge[i][j] = edge[j][i] = distSqr;
            }
        }
        
        // initialize dp to INF
        dp = new long[n];
        for(int i = 0; i < n; i++) {
            dp[i] = Long.MAX_VALUE;
        }

        pq = new PriorityQueue<>();
        answer = 0;
    }
    
    private static void build() {
        boolean[] visited = new boolean[n];
        dp[0] = 0;
        pq.offer(new Node(0, node[0].x, node[0].y, 0));
        
        long totalDistSqr = 0;
        int count = 0;

        while(!pq.isEmpty()) {
            if(count == n) break; // whole node search completion
            Node cur = pq.poll();
            
            if(visited[cur.idx]) continue; // already visited
            visited[cur.idx] = true;
            totalDistSqr += cur.dist;
       
            for(int i = 0; i < n; i++) {
                if(visited[i] || cur.idx == i) continue;
                
                long nextDist = edge[cur.idx][i];
                if(nextDist < dp[i]) {
                    dp[i] = nextDist;
                    pq.offer(new Node(i, node[i].x, node[i].y, nextDist));
                }
            }
            count++;
        }

        answer = Math.round(totalDistSqr * E);
    }
    
    public static void main(String args[]) throws Exception {
        int T = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        for(int test_case = 1; test_case <= T; test_case++) {
            init();
            build();
            sb.append("#").append(test_case).append(" ").append(answer).append("\n");
        }
        System.out.print(sb);
    }
}

// 프림 알고리즘 --> MST
// 시간복잡도: O(N^2 log N)
