import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Solution
{
    static int N;
    static List<List<Integer>> graph;  // 인접리스트
    static int[][] cnt;
	public static void main(String args[]) throws Exception
	{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
        


		for(int test_case = 1; test_case <= T; test_case++)
		{
            StringTokenizer st = new StringTokenizer(br.readLine());

            graph = new ArrayList<>();
            int N = Integer.parseInt(st.nextToken());
            cnt = new int[N][N];

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
                PriorityQueue<Node> pq = new PriorityQueue<>((n1,n2) -> Integer.compare(n1.move, n2.move));
                cnt[i][i] = -1;
                pq.add(new Node(i, 0));

                while (!pq.isEmpty()) {
                    Node node = pq.poll();
                    if(i!=node.cur && cnt[i][node.cur]==0)
                        cnt[i][node.cur] = node.move;
                        cnt[node.cur][i] = node.move;

                    for(int n : graph.get(node.cur)){
                        if(cnt[i][n]==0){
                            pq.add(new Node(n, node.move+1));
                        }
                    }
                }
                int sum = 1;
                for(int j=0; j<N; j++){
                    sum += cnt[i][j];
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