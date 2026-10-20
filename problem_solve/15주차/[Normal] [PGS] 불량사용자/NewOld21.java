import java.util.HashSet;

class Solution {

    HashSet<HashSet<String>> resultSets = new HashSet<>();

    public int solution(String[] user_id, String[] banned_id) {

        boolean[] visited = new boolean[user_id.length];
        dfs(0, user_id, banned_id, visited, new HashSet<>());
        
        return resultSets.size();
    }

    private void dfs(int banIdx, String[] user_id, String[] banned_id, boolean[] visited, HashSet<String> currentSet) {
        if (banIdx == banned_id.length) {
            resultSets.add(new HashSet<>(currentSet));
            return;
        }

        String pattern = banned_id[banIdx];
        for (int i = 0; i < user_id.length; i++) {
            if (visited[i]) 
                continue;

            if (isMatch(user_id[i], pattern)) {
                visited[i] = true;
                currentSet.add(user_id[i]);

                dfs(banIdx + 1, user_id, banned_id, visited, currentSet);

                currentSet.remove(user_id[i]);
                visited[i] = false;
            }
        }
    }

    private boolean isMatch(String user, String ban) {
        if (user.length() != ban.length()) return false;
        
        for (int i = 0; i < ban.length(); i++) {
            if (ban.charAt(i) != '*' && ban.charAt(i) != user.charAt(i)) {
                return false;
            }
        }
        return true;
    }
}
