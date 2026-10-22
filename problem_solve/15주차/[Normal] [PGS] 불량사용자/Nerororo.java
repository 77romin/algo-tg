
import java.util.HashSet;

class Solution
{
    static String[] users, bans;
    static boolean[] visited;
    static HashSet<String> set;
    static int N, M;

    public int solution(String[] user_id, String[] banned_id) {

        users = user_id;
        bans = banned_id;

        N = users.length;
        M = bans.length;

        visited = new boolean[N];
        set = new HashSet<>();

        dfs(0);

        return set.size();
    }

    static void dfs(int cnt) {

        if(cnt == M) {
            StringBuilder sb = new StringBuilder();

            for(int i = 0; i < N; i++) {
                if(visited[i]) {
                    sb.append(i).append(",");
                }
            }

            set.add(sb.toString());
            return;
        }

        for(int i = 0; i < N; i++) {
            if(visited[i]) continue;
            if(!check(users[i], bans[cnt])) continue;

            visited[i] = true;
            dfs(cnt + 1);
            visited[i] = false;
        }
    }

    static boolean check(String user, String ban) {

        if(user.length() != ban.length()) return false;

        for(int i = 0; i < user.length(); i++) {
            if(ban.charAt(i) == '*') continue;
            if(user.charAt(i) != ban.charAt(i)) return false;
        }

        return true;
    }
}
