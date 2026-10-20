import java.util.*;

class Solution {
    public int solution(int[] stones, int k) {
        Deque<Integer> deque = new ArrayDeque<>();
        int answer = Integer.MAX_VALUE;

        for (int i = 0; i < stones.length; i++) {
            while (!deque.isEmpty() && deque.peekFirst() <= i - k) {
                deque.pollFirst();
            }

            while (!deque.isEmpty() &&
                   stones[deque.peekLast()] <= stones[i]) {
                deque.pollLast();
            }

            deque.offerLast(i);

            if (i >= k - 1) {
                answer = Math.min(answer, stones[deque.peekFirst()]);
            }
        }

        return answer;
    }
}
