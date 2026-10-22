class Solution {

	static int ans, N, M;
	static String[] user, ban;
	static boolean[] check;

	public int solution(String[] user_id, String[] banned_id) {
		user = user_id;
		ban = banned_id;
		N = user.length;
		M = ban.length;

		check = new boolean[(1<<N)];
		dfs(0, 0);
		
		for (int i = 0; i < (1<<N); i++) {
			if(check[i]) ans++;
		}
		return ans;
	}

	private static void dfs(int i, int mask) {
		if(i == M) {
			check[mask] = true;
			return;
		}
		
		String b = ban[i];
			
		for (int j = 0; j < N; j++) {
			if((mask & (1<<j)) != 0) continue;
			
			String u = user[j];
			boolean flag = true;
			if (u.length() != b.length()) continue;
			
			for (int k = 0; k < u.length(); k++) {
				char c1 = u.charAt(k);
				char c2 = b.charAt(k);
				if (c2 != '*' && c1 != c2) {
					flag = false;
					break;
				}
			}
			if (flag) {
				 dfs(i + 1, mask | (1<<j));
			}
		}	
	}
}
