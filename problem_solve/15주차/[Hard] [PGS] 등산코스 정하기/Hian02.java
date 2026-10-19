/*
문제 정의

n개의 지점이 있고 각 지점은 출입구, 쉼터, 산봉우리 중 하나입니다.
각 지점은 양방향 등산로로 연결되어 있으며 등산로마다 이동 시간이 주어집니다.

등산코스는 출입구 한 곳에서 출발해서 산봉우리 한 곳을 방문한 뒤 다시 원래 출입구로 돌아와야 합니다.
코스 중 다른 출입구나 다른 산봉우리를 방문하면 안 됩니다.

등산코스의 intensity는 해당 코스에서 이용한 등산로 중 가장 긴 이동 시간입니다.

모든 가능한 등산코스 중 intensity가 최소가 되는 산봉우리와 그 intensity를 구해야 합니다.
intensity가 같은 산봉우리가 여러 개라면 번호가 가장 작은 산봉우리를 선택합니다.
*/


/*
접근 방법

다익스트라 알고리즘을 변형해서 해결합니다.
일반적인 다익스트라는 출발점에서 현재 정점까지의 거리 합을 최소화합니다.
하지만 이 문제에서는 등산로 이동 시간의 합이 아니라 경로에서 가장 큰 간선의 값이 intensity가 됩니다.

intensity[next] = Math.min(
    intensity[next],
    Math.max(intensity[current], edgeWeight)
)
형태로 갱신합니다.

예를 들어 현재까지의 intensity가 3이고 다음 등산로의 이동 시간이 5라면 새로운 intensity는 3 + 5가 아니라 Math.max(3, 5) = 5입니다.

출입구가 여러 개이므로 모든 출입구를 시작점으로 PriorityQueue에 넣습니다.
각 출입구의 초기 intensity는 0으로 설정합니다.
그러면 다익스트라 한 번으로 "어떤 출입구에서 출발하든 해당 지점까지 도달할 수 있는 최소 intensity"를 구할 수 있습니다.


또한 산봉우리에 도착했다면 그 이후로 이동하면 안 됩니다.
문제에서는 산봉우리를 하나만 방문해야 하기 때문에 산봉우리를 중간 지점으로 지나갈 수 없습니다.
따라서 현재 정점이 산봉우리라면 더 이상 인접 정점을 탐색하지 않습니다.

출입구 역시 다른 출입구에서 출발한 경로의 중간 지점으로 사용하면 안 됩니다.
하지만 모든 출입구의 intensity를 처음부터 0으로 설정하기 때문에 다른 경로를 통해 출입구에 도착하더라도 더 큰 intensity로 갱신될 수 없습니다.
따라서 출입구를 별도로 차단하지 않아도 중간 경로로 사용되지 않습니다.


왜 출입구 -> 산봉우리 경로만 구하면 되는가?
등산로는 양방향입니다.
출입구에서 산봉우리까지 1 -> 2 -> 4 -> 5 라는 경로로 이동할 수 있다면
5 -> 4 -> 2 -> 1 처럼 동일한 경로를 반대로 이동해서 원래 출입구로 돌아갈 수 있습니다.

왕복해도 사용되는 등산로들의 최대 가중치는 동일하므로 출입구에서 산봉우리까지의 최소 intensity만 구하면 전체 왕복 코스의 최소 intensity와 같습니다.


마지막으로 summits를 오름차순으로 정렬하고 각 산봉우리의 intensity를 확인합니다.
intensity가 더 작을 때만 정답을 갱신하면 intensity가 같은 경우 번호가 가장 작은 산봉우리가 자동으로 선택됩니다.
*/


/*
문제 풀이
*/

import java.util.*;

class Solution {
    /*
    그래프의 간선을 표현합니다.
    to     : 연결된 정점
    weight : 등산로의 이동 시간
    */
    static class Edge {
        int to;
        int weight;

        Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }
    /*
    PriorityQueue에 저장할 상태입니다.
    node      : 현재 정점
    intensity : 현재 정점까지 이동하는 경로의 intensity
    */
    static class Node implements Comparable<Node> {
        int node;
        int intensity;
        Node(int node, int intensity) {
            this.node = node;
            this.intensity = intensity;
        }
        /*
        intensity가 작은 상태부터 처리합니다.
        */
        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.intensity, other.intensity);
        }
    }

    public int[] solution(int n, int[][] paths, int[] gates, int[] summits) {
        /*
        인접 리스트를 만듭니다.
        */
        ArrayList<Edge>[] graph = new ArrayList[n + 1];

        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        /*
        등산로는 양방향이므로 양쪽에 모두 추가합니다.
        */
        for (int[] path : paths) {

            int a = path[0];
            int b = path[1];
            int weight = path[2];

            graph[a].add(new Edge(b, weight));
            graph[b].add(new Edge(a, weight));
        }

        /*
        산봉우리 여부를 빠르게 확인하기 위한 배열입니다.
        */
        boolean[] isSummit = new boolean[n + 1];

        for (int summit : summits) {
            isSummit[summit] = true;
        }


        /*
        intensity[i] = 어떤 출입구에서 i번 지점까지 갈 때 가능한 최소 intensity
        */
        int[] intensity = new int[n + 1];

        Arrays.fill(intensity, Integer.MAX_VALUE);

        /*
        intensity가 작은 경로부터 처리하기 위한 PriorityQueue입니다.
        */
        PriorityQueue<Node> pq = new PriorityQueue<>();

        /*
        모든 출입구를 동시에 시작점으로 사용합니다.
        */
        for (int gate : gates) {
            intensity[gate] = 0;
            pq.offer(new Node(gate, 0));
        }

        /*
        변형 다익스트라를 수행합니다.
        */
        while (!pq.isEmpty()) {

            Node current = pq.poll();

            int curNode = current.node;
            int curIntensity = current.intensity;

            /*
            이미 더 좋은 경로를 찾은 상태라면 현재 PriorityQueue 값은 무시합니다.
            */
            if (curIntensity > intensity[curNode]) {
                continue;
            }

            /*
            산봉우리에 도착한 경우 더 이상 이동하지 않습니다.
            산봉우리를 중간 지점으로 사용할 수 없기 때문입니다.
            */
            if (isSummit[curNode]) {
                continue;
            }

            /*
            현재 지점과 연결된 모든 등산로를 확인합니다.
            */
            for (Edge edge : graph[curNode]) {
                int next = edge.to;
                /*
                현재 경로에서 next까지 이동했을 때의 intensity입니다.
                기존 intensity와 새로운 간선의 가중치 중 더 큰 값이 새로운 경로의 intensity가 됩니다.
                */
                int nextIntensity = Math.max(curIntensity, edge.weight);

                /*
                더 작은 intensity로 next에 도달할 수 있다면 갱신합니다.
                */
                if (nextIntensity < intensity[next]) {
                    intensity[next] = nextIntensity;
                    pq.offer( new Node(next, nextIntensity));
                }
            }
        }


        /*
        산봉우리 번호가 작은 순서대로 확인하기 위해 정렬합니다.
        */
        Arrays.sort(summits);

        int answerSummit = 0;
        int answerIntensity = Integer.MAX_VALUE;

        /*
        모든 산봉우리의 최소 intensity를 확인합니다.
        */
        for (int summit : summits) {
            /*
            더 작은 intensity를 가진 산봉우리를 발견한 경우에만 갱신합니다.

            summits를 오름차순으로 정렬했으므로 intensity가 같은 경우에는 먼저 확인한 번호가 작은 산봉우리가 유지됩니다.
            */
            if (intensity[summit] < answerIntensity) {
                answerIntensity = intensity[summit];
                answerSummit = summit;
            }
        }

        return new int[]{answerSummit,answerIntensity};
    }
}


/*
시간복잡도

정점의 개수를 V, 간선의 개수를 E라고 하겠습니다.
인접 리스트를 만드는 데 O(E)가 필요합니다.

다익스트라에서 각 간선을 확인하고 PriorityQueue의 삽입/삭제에는 O(log V)가 필요합니다.
따라서 다익스트라의 시간복잡도는 O((V + E) log V)입니다.

산봉우리 정렬에는 산봉우리 개수를 S라고 할 때 O(S log S)가 필요합니다.
따라서 전체 시간복잡도는 O((V + E) log V)정도로 볼 수 있습니다.


핵심 정리
1. 이 문제는 일반 최단거리의 합이 아니라 경로에서 가장 큰 간선의 가중치를 최소화하는 문제입니다.
2. 다익스트라의 거리 갱신을 dist[current] + weight가 아니라 Math.max(intensity[current], weight)로 변경합니다.
3. 출입구가 여러 개이므로 모든 출입구를 intensity 0으로 PriorityQueue에 넣는 멀티 소스 다익스트라를 사용합니다.
4. 산봉우리에 도착하면 탐색을 중단해서 산봉우리를 중간 지점으로 지나가지 않도록 합니다.
5. 등산로가 양방향이므로 출입구 -> 산봉우리의 최소 intensity 경로를 그대로 반대로 돌아오면 왕복 코스를 만들 수 있습니다.
6. 산봉우리 번호를 오름차순으로 정렬한 뒤 최소 intensity를 확인하면 동률일 때 번호가 가장 작은 산봉우리를 선택할 수 있습니다.
*/
