import java.util.ArrayDeque;

class Solution {
    public int solution(int[] stones, int k) {
        int answer = Integer.MAX_VALUE;
        int N = stones.length;

        ArrayDeque<Integer> q = new ArrayDeque<>();

        for (int i = 0; i < N; i++) {

            if (!q.isEmpty() && q.peekFirst() <= i - k) {
                q.pollFirst();
            }

            while (!q.isEmpty() && stones[q.peekLast()] <= stones[i]) {
                q.pollLast();
            }

            q.offerLast(i);

            if (i >= k - 1)
                answer = Math.min(answer, stones[q.peekFirst()]);
        }

        return answer;
    }
}