import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

class Solution {

    static int [] answer;
    static List<List<Node>> maps;


    public int solution(int N, int s, int a, int b, int[][] fares) {
        answer = new int[N+1] ;
        maps = new ArrayList<>();

        for(int i=0; i<N+1; i++){
            maps.add(new ArrayList<>());
        }

        for(int[] far : fares ){
            maps.get(far[0]).add(new Node(far[1], far[2]));
            maps.get(far[1]).add(new Node(far[0], far[2]));
        }

        for(int i=1; i<N+1; i++) {
            int sharing = 0;
            if(i!=s){
                sharing = takingTaxi(s, i, fares, N);
            }
            int alone_A = takingTaxi(i, a, fares, N);
            int alone_B = takingTaxi(i, b, fares, N);
            
            if(sharing < 0 && alone_A < 0 && alone_B < 0){
                continue;
            }
            answer[i] = sharing + alone_A +  alone_B;
        }

        answer[0] = Integer.MAX_VALUE;
        return Arrays.stream(answer).min().getAsInt();
    }


    private static int takingTaxi(int x, int y, int[][] fares, int N){
        boolean [] visited = new boolean[N+1];
        PriorityQueue<Node> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.charge, o2.charge));

        pq.add(new Node(x, 0));

        while(!pq.isEmpty()){
            Node node = pq.poll();

            if(node.cur == y){
                return node.charge;
            }

            if(visited[node.cur]){
                continue;
            }

            visited[node.cur] = true;

            for(Node n : maps.get(node.cur)){
                if(!visited[n.cur]){
                    pq.offer(new Node(n.cur, node.charge + n.charge));
                }
            }
        }
        return -1;
    }


}

class Node{
    int cur;
    int charge;

    Node(int cur, int charge){
        this.cur = cur;
        this.charge = charge;
    }
}