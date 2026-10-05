/*
문제 정의

진기는 0초부터 붕어빵을 만들기 시작하며 M초마다 K개의 붕어빵을 만듭니다.
N명의 손님이 각각 특정 시간에 도착하며, 모든 손님은 도착하자마자 기다리지 않고 붕어빵 1개를 받아야 합니다.

모든 손님에게 기다리는 시간 없이 붕어빵을 제공할 수 있다면 "Possible", 한 명이라도 바로 제공할 수 없다면 "Impossible"을 출력하는 문제입니다.
*/


/*
접근 방법

손님들의 도착 시간을 오름차순으로 정렬해서 가장 빨리 도착하는 손님부터 확인합니다.
어떤 손님이 time초에 도착했을 때, 그 시간까지 만들어진 붕어빵의 총 개수는 (time / M) * K입니다.

예를 들어 M = 2, K = 3이라면 2초에 3개, 4초에 6개, 6초에 9개의 붕어빵이 만들어집니다.
손님을 도착 시간 순서대로 확인했을 때 i번째 인덱스의 손님은 지금까지 총 i + 1명의 손님이 도착했다는 의미입니다.
따라서 현재 시간까지 만들어진 붕어빵의 개수가 지금까지 도착한 손님의 수보다 작다면 해당 손님에게 붕어빵을 제공할 수 없습니다.

즉 다음 조건을 확인하면 됩니다.
(time / M) * K < i + 1
위 조건을 만족하면 Impossible입니다.

반대로 모든 손님에 대해 만들어진 붕어빵 개수가 도착한 손님 수 이상이라면 Possible입니다.
이 방법을 사용하면 실제로 붕어빵 재고를 하나씩 증가시키거나 감소시킬 필요가 없습니다.
*/


/*
문제 풀이
*/

import java.io.*;
import java.util.*;

public class Solution {

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());

            int[] arrival = new int[N];
            st = new StringTokenizer(br.readLine());

            for (int i = 0; i < N; i++) {
                arrival[i] = Integer.parseInt(st.nextToken());
            }

            /*
            손님을 도착 시간 순서대로 확인하기 위해 오름차순 정렬합니다.
            */
            Arrays.sort(arrival);
            boolean possible = true;

            for (int i = 0; i < N; i++) {
                /*
                현재 손님이 도착하는 시간입니다.
                */
                int time = arrival[i];
                /*
                time초까지 만들어진 붕어빵의 총 개수입니다.
                M초마다 K개씩 만들어지므로 (time / M) * K개가 완성되어 있습니다.
                */
                int made = (time / M) * K;
                /*
                i번째 손님까지 총 i + 1명의 손님이 붕어빵을 받아야 합니다.
                만들어진 붕어빵보다 손님 수가 많다면 현재 손님에게 바로 제공할 수 없습니다.
                */
                if (made < i + 1) {
                    possible = false;
                    break;
                }
            }
            System.out.println("#" + tc + " " + (possible ? "Possible" : "Impossible"));
        }
    }
}


/*
시간복잡도
N명의 손님 도착 시간을 정렬하는 데 O(N log N)이 필요합니다.
정렬 후 N명의 손님을 한 번씩 확인하므로 O(N)이 필요합니다.
따라서 전체 시간복잡도는 O(N log N)입니다.
N <= 100이므로 충분히 빠르게 해결할 수 있습니다.

공간복잡도
N명의 도착 시간을 저장하는 배열을 사용하므로 O(N)입니다.

핵심 정리
1. 손님의 도착 시간을 오름차순으로 정렬합니다.
2. time초까지 만들어진 붕어빵의 개수는 (time / M) * K입니다.
3. 정렬된 배열에서 i번째 손님까지 총 i + 1명의 손님이 도착한 상태입니다.
4. (time / M) * K < i + 1이면 붕어빵이 부족하므로 Impossible입니다.
5. 모든 손님이 조건을 만족하면 Possible입니다.
*/
