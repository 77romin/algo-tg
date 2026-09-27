
import java.util.*;
class Solution {
    static int answer;
    static String[] num;  
    static boolean[] visited;
    static HashSet<Integer> set; 
           
    public int solution(String numbers) {
        answer = 0;
        num = new String[numbers.length()];
        visited = new boolean[numbers.length()];
        set = new HashSet<>();


        for(int i=0; i<numbers.length(); i++){
            num[i] = numbers.charAt(i) + ""; 
        }
            
        dfs("");
        for(int n : set){
            prim(n);
        }
        return answer;
    }


    void dfs(String number){
        if(number.isEmpty())
            set.add(Integer.parseInt(number));

        for(int i=0; i<num.length; i++){
            if(visited[i])
                continue;
            visited[i] = true;
            dfs(number + num[i]);
            visited[i] = false;
        }
    }

    void prim(int n){
        if(n==0 || n==1){
            return;
        }
        int r = (int)Math.sqrt(n);
        answer += 1;
        for(;r>1; r--){
            if(n%r==0){
                answer -=1;
                break;
            }
        }
    }
}