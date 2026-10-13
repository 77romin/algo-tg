import java.util.*;

class Solution {
    public String solution(int n, int k, String[] cmd) {
        TreeSet<Integer> set = new TreeSet<>();
        Stack<Integer> stack = new Stack<>();
        char[] ans = new char[n];
        Arrays.fill(ans, 'O');

        for (int i = 0; i < n; i++) set.add(i);

        for (String c : cmd) {
            char op = c.charAt(0);

            if (op == 'U' || op == 'D') {
                int x = Integer.parseInt(c.substring(2));
                while (x-- > 0)
                    k = op == 'U' ? set.lower(k) : set.higher(k);

            } else if (op == 'C') {
                Integer next = set.higher(k);
                set.remove(k);
                stack.push(k);
                ans[k] = 'X';
                k = next != null ? next : set.lower(k);

            } else {
                int row = stack.pop();
                set.add(row);
                ans[row] = 'O';
            }
        }

        return new String(ans);
    }
}
