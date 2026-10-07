/*
문제 정의

N개의 섬이 있고 각 섬의 X, Y 좌표가 주어집니다. 두 섬을 연결하는 해저터널을 만들 수 있으며, 모든 섬이 직접 또는 간접적으로 연결되어야 합니다.
두 섬 사이의 터널 길이를 L이라고 할 때 환경 부담금은 E * L^2입니다.

두 섬 (x1, y1), (x2, y2) 사이의 거리 제곱은 (x1 - x2)^2 + (y1 - y2)^2입니다.
모든 섬을 연결하면서 환경 부담금의 총합을 최소화하고, 최종 결과를 소수 첫째 자리에서 반올림하여 출력하는 문제입니다.
*/


/*
접근 방법

모든 섬을 하나의 그래프로 생각합니다. 섬은 정점이고 두 섬을 연결하는 해저터널은 간선입니다.
문제의 목표는 모든 정점을 연결하면서 간선 비용의 합을 최소로 만드는 것이므로 최소 신장 트리(MST)를 구하면 됩니다.

간선 하나의 실제 비용은 E * L^2입니다. E는 모든 간선에 동일하게 곱해지는 값이므로 MST를 선택할 때는 E를 제외하고 L^2만 비교해도 결과가 같습니다.
따라서 먼저 모든 터널 길이 제곱의 최소 합을 구한 다음 마지막에 E를 곱합니다.
N <= 1000이고 모든 섬 사이에 터널을 만들 수 있기 때문에 그래프는 완전 그래프입니다.

모든 간선을 직접 만들면 간선의 개수는 최대 N * (N - 1) / 2로 약 500,000개가 됩니다. Kruskal을 사용해도 가능하지만 모든 간선을 저장하고 정렬해야 합니다.
Prim 알고리즘을 사용하면 간선을 따로 저장하지 않고 좌표를 이용해 필요한 순간에 두 섬의 거리를 계산할 수 있습니다.

dist[i] = 현재 MST에 포함된 섬들에서 i번 섬으로 연결할 수 있는 최소 거리 제곱으로 정의합니다.

1. 임의의 0번 섬부터 시작하므로 dist[0] = 0으로 설정합니다.
2. 아직 선택하지 않은 섬 중 dist 값이 가장 작은 섬을 MST에 추가합니다.
3. 새롭게 선택된 섬과 다른 모든 섬 사이의 거리 제곱을 계산하여 dist를 갱신합니다.
4. 모든 섬이 선택될 때까지 반복합니다.
5. 선택할 때 사용한 거리의 합에 E를 곱하고 Math.round()로 반올림합니다.

좌표 차이는 최대 1,000,000이므로 제곱값은 최대 10^12입니다. int 범위를 초과하기 때문에 거리와 MST 합은 반드시 long을 사용해야 합니다.
*/


/*
문제 풀이
*/

import java.io.*;
import java.util.*;

public class Solution {
    static int N;
    static long[] x;
    static long[] y;
    /*
    Prim 알고리즘을 이용해 MST의 간선 거리 제곱 합을 구합니다.
    */
    static long prim() {
        /*
        dist[i] = 현재 MST에서 i번 섬을 연결할 수 있는 최소 거리 제곱입니다.
        */
        long[] dist = new long[N];
        /*
        selected[i] = i번 섬이 이미 MST에 포함되었는지를 저장합니다.
        */
        boolean[] selected = new boolean[N];
        Arrays.fill(dist, Long.MAX_VALUE);
        /*
        0번 섬부터 시작합니다. 시작 섬은 연결 비용이 필요하지 않으므로 0입니다.
        */
        dist[0] = 0;
        long total = 0;

        /*
        총 N개의 섬을 하나씩 MST에 포함시킵니다.
        */
        for (int count = 0; count < N; count++) {
            int current = -1;
            long minDist = Long.MAX_VALUE;

            /*
            아직 MST에 포함되지 않은 섬 중 현재 가장 작은 비용으로 연결할 수 있는 섬을 찾습니다.
            */
            for (int i = 0; i < N; i++) {
                if (!selected[i] && dist[i] < minDist) {
                    minDist = dist[i];
                    current = i;
                }
            }

            /*
            current번 섬을 MST에 포함합니다.
            */
            selected[current] = true;

            /*
            현재 섬을 MST에 연결할 때 사용한 비용을 더합니다.
            시작 섬은 dist[0] = 0이므로 비용에 영향을 주지 않습니다.
            */
            total += dist[current];

            /*
            새롭게 추가한 current번 섬을 이용해서 다른 섬들의 최소 연결 비용을 갱신합니다.
            */
            for (int next = 0; next < N; next++) {
                if (selected[next]) {
                    continue;
                }
                /*
                두 섬 사이의 거리 제곱을 계산합니다.
                L^2 = (x1 - x2)^2 + (y1 - y2)^2
                */
                long dx = x[current] - x[next];
                long dy = y[current] - y[next];

                long distance = dx * dx + dy * dy;

                /*
                current번 섬을 거쳐 연결하는 것이 더 저렴하다면 갱신합니다.
                */
                if (distance < dist[next]) {
                    dist[next] = distance;
                }
            }
        }

        return total;
    }


    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        for (int tc = 1; tc <= T; tc++) {
            N = Integer.parseInt(br.readLine());
            x = new long[N];
            y = new long[N];

            /*
            X 좌표 입력
            */
            st = new StringTokenizer(br.readLine());

            for (int i = 0; i < N; i++) {
                x[i] = Long.parseLong(st.nextToken());
            }

            /*
            Y 좌표 입력
            */
            st = new StringTokenizer(br.readLine());

            for (int i = 0; i < N; i++) {
                y[i] = Long.parseLong(st.nextToken());
            }

            /*
            환경 부담 세율 E는 실수이므로 double로 입력받습니다.
            */
            double E = Double.parseDouble(br.readLine());

            /*
            MST에서 사용하는 모든 터널의 거리 제곱 합을 구합니다.
            */
            long mstCost = prim();

            /*
            실제 환경 부담금은 E * 거리 제곱 합입니다.
            소수 첫째 자리에서 반올림해야 하므로 Math.round()를 사용합니다.
            */
            long answer = Math.round(mstCost * E);

            sb.append("#").append(tc).append(" ").append(answer).append('\n');
        }

        System.out.print(sb);
    }
}


/*
시간복잡도
Prim 알고리즘에서 N개의 섬을 선택합니다.
각 섬을 선택할 때 아직 선택하지 않은 섬 중 최소 dist를 찾기 위해 O(N), 새로운 섬을 선택한 후 다른 섬의 거리를 갱신하기 위해 O(N)이 필요합니다.
따라서 전체 시간복잡도는 O(N^2)입니다.
N <= 1000이므로 테스트 케이스 하나당 약 1,000,000번 정도의 연산으로 충분히 해결할 수 있습니다.

공간복잡도
X 좌표 배열 O(N), Y 좌표 배열 O(N), dist 배열 O(N), selected 배열 O(N)을 사용합니다.
모든 간선을 별도로 저장하지 않으므로 전체 공간복잡도는 O(N)입니다.


핵심 정리
1. 모든 섬을 최소 비용으로 연결해야 하므로 최소 신장 트리(MST) 문제입니다.
2. 환경 부담금 E는 모든 간선에 동일하게 적용되므로 MST를 구할 때는 거리의 제곱 L^2만 사용합니다.
3. 두 섬 사이의 거리 제곱은 (x1 - x2)^2 + (y1 - y2)^2입니다.
4. 완전 그래프이므로 간선을 전부 저장하지 않고 O(N^2) Prim 알고리즘으로 해결합니다.
5. 좌표 제곱값이 int 범위를 넘어갈 수 있으므로 반드시 long을 사용합니다.
6. MST의 거리 제곱 합을 구한 뒤 E를 곱하고 Math.round()로 반올림합니다.
*/
