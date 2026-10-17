import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

class Solution {
	
	static class Node implements Comparable<Node> {
		int to, weight;

		public Node(int to, int weight) {
			this.to = to;
			this.weight = weight;
		}

		@Override
		public int compareTo(Node o) {
			if(this.weight != o.weight) return Integer.compare(this.weight, o.weight);
			return Integer.compare(this.to, o.to);
		}
	}
	
	static int N;
	static List<List<Node>> graph = new ArrayList<>();
	int[] gates, ans;
	Set<Integer> summits = new HashSet<>();

    public int[] solution(int n, int[][] paths, int[] gates, int[] summits) {
    	N = n;
    	for (int i = 0; i <= N; i++) {
			graph.add(new ArrayList<>());
		}
    	
    	for (int i = 0; i < paths.length; i++) {
			int[] path = paths[i];
			int from = path[0];
			int to = path[1];
			int weight = path[2];
			
			graph.get(from).add(new Node(to, weight));
			graph.get(to).add(new Node(from, weight));
		}
    	
    	this.gates = gates;
    	for (int i = 0; i < summits.length; i++) {
			this.summits.add(summits[i]);
		}
    	
        ans = new int[2];
        solve();
        return ans;
    }
    
    private void solve() {
    	PriorityQueue<Node> pq = new PriorityQueue<>();
    	
    	int[] minWeight = new int[N+1];
    	Arrays.fill(minWeight, Integer.MAX_VALUE);
    	
    	for (int g : gates) {
			pq.offer(new Node(g, 0));
			minWeight[g] = 0;
		}
    	ans[0] = N;
    	ans[1] = Integer.MAX_VALUE;
    	
    	while(!pq.isEmpty()) {
    		Node cur = pq.poll();
    		
    		if(cur.weight > ans[1]) break;
    		
    		if(summits.contains(cur.to)) {
    	    	ans[0] = Math.min(ans[0], cur.to);
    			ans[1] = cur.weight;
    			continue;
    		}
    		
    		for (Node next : graph.get(cur.to)) {
    			int nextWeight = Math.max(next.weight, cur.weight);
				if(minWeight[next.to] > nextWeight) {
					minWeight[next.to] = nextWeight;
					pq.offer(new Node(next.to, nextWeight));
				}
			}
    	}
    }
}
