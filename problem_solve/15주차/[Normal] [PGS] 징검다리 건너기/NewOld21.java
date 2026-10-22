class Solution {
    public int solution(int[] stones, int k) {
        int left = 1;
        int right = 200_000_000;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (canCross(stones, k, mid)) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return right;
    }

    private boolean canCross(int[] stones, int k, int people) {
        int count = 0;

        for (int stone : stones) {
            if (stone < people) {
                count++;
            } else {
                count = 0;
            }

            if (count >= k) {
                return false;
            }
        }

        return true;
    }
}