import java.util.*;

class Solution {
    static Set<Integer> set = new HashSet<>(); // 생성한 숫자 중복 없이 저장 가능해서 사용
    static boolean[] visited;

    public int solution(String numbers) {
        visited = new boolean[numbers.length()];
        dfs(numbers, ""); // 상공트 탐색
        int answer = 0;
        
        for (int num : set) { if (isPrime(num)) answer++;

        return answer;
    }

    static void dfs(String numbers, String current) {
        if (current.length() == numbers.length()) return; // 기저 - 모든 숫자 조각을 사용하면 선택불가

        for (int i = 0; i < numbers.length(); i++) {
            if (visited[i]) continue;
            
            visited[i] = true; // 방문 상태 true
            String next = current + numbers.charAt(i); 
            set.add(Integer.parseInt(next)); // Set으로 중복 상태 제거
            dfs(numbers, next); // 상공트 다음 탐색 
            visited[i] = false; // 방문 상태 원복
        }
    }

    static boolean isPrime(int num) {
        if (num < 2) return false; // 기저 - 0,1은 소수 아니라서 제외

        for (int i = 2; i * i <= num; i++) { 
            if (num % i == 0) return false; // 약수 존재하면 소수 X -> num % 특정 수 == 0 일 경우, 특정 수의 배수 즉 약수가 되면 소수라고 못 보니까 
        }

        return true;
    }
}
