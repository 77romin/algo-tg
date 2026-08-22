import java.util.*;
class Solution {
	public static void main(String args[]) throws Exception {
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
        StringBuilder sb = new StringBuilder();
		for(int test_case = 1; test_case <= T; test_case++) {
            int tc = sc.nextInt();
            sb.append("#").append(tc).append(" ");
            Map<Integer, Integer> map = new TreeMap();
            for(int i=0; i<1000; i++) {
                int n = sc.nextInt();
                map.put(n, map.getOrDefault(n, 0)+1);
            }
            
            int maxAppear = 0;
            int maxAppScore = 0;
            for(int n : map.keySet()) {
                if(maxAppear<=map.get(n)) {
                    maxAppear = map.get(n);
                    maxAppScore = n; 
                }
            }
            
            sb.append(maxAppScore).append("\n");
		}
        System.out.println(sb);
	}
}

/**
 * <Memo>
 * TreeMap을 이용해서 key값 오름차순으로 확인. (최빈값 중복 수가 존재할시 가장 큰 값 찾기 수월함)
 * 시간복잡도: O(TxNlogK) - T:testcase, N:1000, K:서로 다른 정수의 개수(최대 101개)
 * Bucket Sort를 활용하여서도 풀 예정
 */

