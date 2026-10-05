import java.io.*;
import java.util.*;

class Solution
{
	public static void main(String args[]) throws Exception
	{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T =  Integer.parseInt(br.readLine());
		StringTokenizer st;


		for(int test_case = 1; test_case <= T; test_case++)
		{
            st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());

            int [] rsv = new int[N];
            st = new StringTokenizer(br.readLine());
            for(int i=0; i<N; i++){
                rsv[i] = Integer.parseInt(st.nextToken()); 
            }
            Arrays.sort(rsv);

            int Bungeo = 0;
            int idx = 0;
            String ans = "Possible";

            for(int i=0; i<=rsv[N-1]; i++){
                if(i>0 && i%M==0){
                    Bungeo += K;
                }

                if(i==rsv[idx]){
                    if(Bungeo==0){
                        ans = "Impossible";
                        break;
                    }
                    Bungeo--;
                    idx++;
                }
            }

            System.out.println("#" + test_case + " " + ans);
		}
	}
}