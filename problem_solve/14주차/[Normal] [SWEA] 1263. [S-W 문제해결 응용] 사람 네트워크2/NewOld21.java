import java.io.*;
import java.util.*;


class Solution
{
    static int N;
    static List<List<Integer>> graph;  // 인접리스트

	public static void main(String args[]) throws Exception
	{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());

		for(int test_case = 1; test_case <= T; test_case++)
		{
            StringTokenizer st = new StringTokenizer(br.readLine());

            graph = new ArrayList<>();
            N = Integer.parseInt(st.nextToken());

            for(int i=0; i<N; i++){
                graph.add(new ArrayList<>());
                for(int j=0; j<N; j++){
                    int n = Integer.parseInt(st.nextToken());
                    if(n==1){
                        graph.get(i).add(j);
                    }
                }
            }

            int ans = Integer.MAX_VALUE;

            for(int i=0; i<N; i++){
                Queue<Node> q = new ArrayDeque<>();
                boolean[] visited = new boolean[N];

                q.add(new Node(i, 0));
                visited[i] = true;

                int sum = 0;

                while (!q.isEmpty()) {
                    Node node = q.poll();

                    sum += node.move;

                    for (int next : graph.get(node.cur)) {
                        if (!visited[next]) {
                            visited[next] = true;
                            q.add(new Node(next, node.move + 1));
                        }
                    }
                }
                ans = Math.min(ans, sum);
            }

            System.out.println("#" + test_case + " " + ans);

		}
	}
}

class Node{
    int cur; // 현재 위치
    int move; // 이동 횟수

    Node(int a, int b){
        this.cur = a;
        this.move = b;
    }
}