import java.io.*;
import java.util.*;

public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());
            int[] people = new int[N];
            st = new StringTokenizer(br.readLine());

            for (int i = 0; i < N; i++) {
                people[i] = Integer.parseInt(st.nextToken());
            }

            Arrays.sort(people);
            boolean possible = true;

            for (int i = 0; i < N; i++) {
                int time = people[i];
                int bread = (time / M) * K;
                
                if (bread < i + 1) {
                    possible = false;
                    break;
                }
            }

            sb.append("#").append(tc).append(" ");

            if (possible) sb.append("Possible");
            else sb.append("Impossible");

            sb.append("\n");
        }

        System.out.print(sb);
    }
}
