import java.util.*;

class Solution {
    public int solution(int N, int[][] road, int K) {
        int answer = 0;
        PriorityQueue<Integer> deque = new PriorityQueue<>();
        
        deque.offer(1);
        
        int [][] roads = new int[N+1][N+1];

        for (int[] road_info : road) {
            if(roads[road_info[0]][road_info[1]]==0 || roads[road_info[0]][road_info[1]] > road_info[2]){
                roads[road_info[0]][road_info[1]] = road_info[2];
                roads[road_info[1]][road_info[0]] = road_info[2];    
            }
            
        }
        
        int[] distance = new int[N + 1];
        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[1] = 0;
        while(!deque.isEmpty()){
            int cur = deque.poll();

            for(int i=1; i<N+1; i++){
                if(roads[cur][i] > 0){
                    if((distance[cur]+ roads[cur][i]) < distance[i]){
                        deque.offer(i);
                        distance[i] = distance[cur]+ roads[cur][i];
                    }
                } 
            }

        }

        for(int d : distance){
            if(d<=K){
                answer++;
            }
        }

        return answer;
    }
}