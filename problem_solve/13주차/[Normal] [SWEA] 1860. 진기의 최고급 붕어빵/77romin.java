import java.util.*;
import java.io.*;

class Solution {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static int N, M, K; // N명의 고객, M초당 K개의 붕어빵 생산
    private static int[] customer; // N명의 고객의 도착시각들
    private static boolean isPossible;
    
    private static void init() throws Exception { // 초기화
        isPossible = true;
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        
        customer = new int[N];
        st = new StringTokenizer(br.readLine().trim());
        for(int i=0; i<N; i++)
            customer[i] = Integer.parseInt(st.nextToken());
    }
    
    private static void sellFishBread() { // 붕어빵 제공
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for(int orderTime : customer)
            pq.offer(orderTime);
        
        int curTime = 0;
        int curStocks = 0;
        while(!pq.isEmpty()) {
            int curOrder = pq.poll();
            if(curOrder>curTime) {
                pq.offer(curOrder); // 다시 집어넣어. 아직 때가 아니거든
                curTime++;
                if(curTime%M==0) curStocks+=K; // M초마다 K개의 붕어빵 생산
                continue;
            }
            
            curStocks--; 
            if(curStocks<0) {
                isPossible = false;
                break;
            }
        }
        
    }
    
	public static void main(String args[]) throws Exception {
		int T = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();
        
		for(int test_case = 1; test_case <= T; test_case++) {
            init(); // 초기화
            sellFishBread(); // 붕어빵 제공
            sb.append("#").append(test_case).append(" ")
                .append(isPossible?"Possible":"Impossible").append("\n");
		}
        System.out.print(sb);
	}
}

/**
 * 우선순위 큐를 사용하여 조건탐색
 * 시간복잡도: O(N);
 */
