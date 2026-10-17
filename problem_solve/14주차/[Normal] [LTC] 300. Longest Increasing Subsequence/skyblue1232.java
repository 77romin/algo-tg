
// import java.util.*;

// class Solution {
//     public int lengthOfLIS(int[] nums) {
//         int n = nums.length;
//         int[] dp = new int[n];

//         Arrays.fill(dp, 1);

//         int answer = 1;

//         for (int i = 0; i < n; i++) {
//             for (int j = 0; j < i; j++) {
//                 if (nums[j] < nums[i]) {
//                     dp[i] = Math.max(dp[i], dp[j] + 1);
//                 }
//             }
//             answer = Math.max(answer, dp[i]);
//         }

//         return answer;
//     }
// } O(N^2)


// O(NlogN)
class Solution {
    public int lengthOfLIS(int[] nums) {
        int[] tails = new int[nums.length];
        int size = 0;

        for (int num : nums) {
            int left = 0;
            int right = size;

            while (left < right) {
                int mid = (left + right) / 2;

                if (tails[mid] < num) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }

            tails[left] = num;

            if (left == size) {
                size++;
            }
        }

        return size;
    }
}


