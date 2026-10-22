
class Solution {
    public int solution(String[] user_id, String[] banned_id) {
        int n = user_id.length;
        int m = banned_id.length;
        int size = 1 << n;

        // 1. 불량 사용자 패턴과 사용자 ID의 매칭 여부
        boolean[][] match = new boolean[m][n];

        for (int i = 0; i < m; i++) {
            String banned = banned_id[i];

            for (int j = 0; j < n; j++) {
                String user = user_id[j];

                if (banned.length() != user.length()) continue;

                boolean valid = true;

                for (int k = 0; k < banned.length(); k++) {
                    char c = banned.charAt(k);

                    if (c != '*' && c != user.charAt(k)) {
                        valid = false;
                        break;
                    }
                }

                match[i][j] = valid;
            }
        }

        // 2. 비트마스크 DP
        boolean[] dp = new boolean[size];
        dp[0] = true;

        for (int i = 0; i < m; i++) {
            boolean[] next = new boolean[size];

            for (int mask = 0; mask < size; mask++) {
                if (!dp[mask]) continue;

                for (int j = 0; j < n; j++) {
                    // 이미 선택했거나 패턴이 일치하지 않으면 제외
                    if ((mask & (1 << j)) != 0 || !match[i][j]) {
                        continue;
                    }

                    next[mask | (1 << j)] = true;
                }
            }

            dp = next;
        }

        // 3. 가능한 제재 사용자 집합의 개수
        int answer = 0;

        for (boolean possible : dp) {
            if (possible) answer++;
        }

        return answer;
    }
}
