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
        Arrays.sort(customer); // 도착시간 오름차순 정렬
    }
    
    private static void sellFishBread() { // 붕어빵 제공
        for(int i=0; i<N; i++) {
            int curStocks = (customer[i]/M)*K; // i+1번째 손님 왔을때의 붕어빵 보유 개수
            if(curStocks < i+1) { // i+1개 이상 붕어빵이 없을 경우, 불가능한 것으로 판단
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
 * 알고리즘: Greedy
 * 시간복잡도: O(NlogN + N) -- 정렬:O(NlogN), 순회: O(N)
 */
