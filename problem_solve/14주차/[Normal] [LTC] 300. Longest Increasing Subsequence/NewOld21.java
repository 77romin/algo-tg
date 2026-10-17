class Solution {

    static int[] lis;

    public int lengthOfLIS(int[] nums) {
        lis = new int[nums.length];
        int idx = 0;
        int ans = -1;

        for (int num : nums) {
            if (idx > 0 && lis[idx - 1] >= num) {
                int i = binarySearch(0, idx, num);
                lis[i] = num;
            } else {
                lis[idx] = num;
                idx++;
            }

            ans = Math.max(ans, idx);
        }

        return ans;
    }

    static int binarySearch(int start, int end, int num) {
        while (start < end) {
            int mid = (start + end) / 2;
            if (lis[mid] < num) {
                start = mid + 1;
            } else {
                end = mid;
            }
        }

        return end;
    }
}