import java.util.*;
import java.io.*;

class Solution {
    
	public static void main(String args[]) throws Exception {
        StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T=Integer.parseInt(br.readLine());

		for(int test_case = 1; test_case <= T; test_case++) {
            StringTokenizer st = new StringTokenizer(br.readLine().trim());
            int n = Integer.parseInt(st.nextToken()); // n개의 노드
            
            List<Integer>[] nodes = new ArrayList[n]; // 각 노드에 연결된 노드들 저장
            
            for(int i=0; i<n; i++)
                nodes[i] = new ArrayList<>();
            
            for(int i=0; i<n; i++)
                for(int j=0; j<n; j++)
                    if(Integer.parseInt(st.nextToken())==1)
                        nodes[i].add(j);
            
            int minCnt = Integer.MAX_VALUE;
            for(int i=0; i<n; i++) {
                boolean[] visited = new boolean[n];
                Queue<Integer> q = new LinkedList<>();
                
                // 시작노드 삽입
                visited[i] = true;
                q.add(i);
                
                int dist = 0; // 시작노드로부터의 거리
                int sumCnt = 0; // 시작노드와 각 노드의 거리 합계
                
                while(!q.isEmpty()) { // BFS활용: 큐에 각 노드에서의 한단계 다음 노드들 확인
                    int size = q.size();
                    
                    dist++; // 다음단계로 넘어가면 거리 증가
                    
                    for(int j=0; j<size; j++) {
                        int curNode = q.poll();
                        
                        if(curNode!=i) 
                            sumCnt += dist;
                        
                        for(int nextNode : nodes[curNode]) {
                            if(visited[nextNode]) continue;
                            visited[nextNode] = true;
                            q.add(nextNode);
                        }
                        
                    }
                }
                minCnt = Math.min(minCnt, sumCnt);
                
            }
            
            sb.append("#").append(test_case).append(" ").append(minCnt).append("\n");
		}
        System.out.println(sb);
        br.close();
	}
}
