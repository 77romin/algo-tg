import java.util.*;

class Solution {

    List<int []> move;
    public int[][] solution(int n) {
        int[][] answer;
        move = new ArrayList<>();
        hanoi(n, 1, 3, 2);
        answer = new int[move.size()][2];
        for(int i=0; i<move.size(); i++){
            answer[i][0] = move.get(i)[0];
            answer[i][1] = move.get(i)[1];
        }
        return answer;
    }
    
    
    void hanoi(int n, int start, int dest, int via){
        if(n<=1){
            move.add(new int[]{start, dest});
            return;
        }

        hanoi(n -1, start, via, dest);
        move.add(new int[]{start, dest});
        hanoi(n -1, via, dest, start);
    }
}

