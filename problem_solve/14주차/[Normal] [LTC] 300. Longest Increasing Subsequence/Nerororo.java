class Solution {
    public int lenLIS(int[] nums) {
        int N = nums.length;
        int[] dp = new int[N]; // LIS의 마지막의 최소값
        int len = 0;

        for (int i = 0; i < N; i++) {
            int left = 0;
            int right = len;

            // nums[i] 이상의 첫 번째 위치 찾기
            while (left < right) {
                int mid = (left + right) / 2;

                if (dp[mid] < nums[i]) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }

            // 끼어 넣을 수 있는 마지막 값을 최소화
            dp[left] = nums[i];

            if (left == len) len++;
        }

        return len;
    }
}