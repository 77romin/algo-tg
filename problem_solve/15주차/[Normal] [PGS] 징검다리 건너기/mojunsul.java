    import java.util.ArrayDeque;
    import java.util.Deque;

    class Solution {
        public int solution(int[] stones, int k) {
            int ans = Integer.MAX_VALUE;
            Deque<Integer> deque = new ArrayDeque<>();
            for (int i = 0; i < stones.length; i++) {
                while(!deque.isEmpty() && stones[deque.peekLast()] <= stones[i]) {
                    deque.pollLast();
                }

                deque.offerLast(i);

                if(i - deque.peekFirst() + 1 > k) {
                    deque.pollFirst();
                }

                if(i + 1 >= k) {
                    ans = Math.min(ans, stones[deque.peekFirst()]);
                }
            }

            return ans;
        }
    }
