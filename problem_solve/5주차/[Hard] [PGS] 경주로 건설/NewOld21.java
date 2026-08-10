import java.util.PriorityQueue;

class Solution {
    public int solution(int[][] board) {
        int answer = 0;
        int N = board[0].length;

        PriorityQueue<Node> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.cnt, o2.cnt));

        
        boolean[][][] visited = new boolean[N][N][2];
        pq.offer(new Node(0, 0, 0, 0));
        answer = shortestPath(pq, visited, board, N);

        pq.clear();
        visited = new boolean[N][N][2];

        pq.offer(new Node(0, 0, 0, 1));
        answer = Math.min(answer, shortestPath(pq, visited, board, N));
    
        return answer * 100;
    }

    private int shortestPath(PriorityQueue<Node> pq, boolean[][][] visited, int[][] board, int N) {

        int [] dx = {0,0,1,-1};
        int [] dy = {1,-1,0,0};
        while (!pq.isEmpty()) {
            Node node = pq.poll();

            if (node.x == N - 1 && node.y == N - 1) {
                return node.cnt;
            }

            visited[node.x][node.y][node.way] = true;

            for (int i = 0; i < 4; i++) {
                int nx = node.x + dx[i];
                int ny = node.y + dy[i];

                if (0 <= nx && nx < N && 0 <= ny && ny < N && board[nx][ny] == 0) {

                    int nextWay = (i == 0 || i == 1) ? 0 : 1;

                    if (visited[nx][ny][nextWay]) {
                        continue;
                    }

                    if (node.way == nextWay) {
                        pq.offer(new Node(node.cnt + 1, nx, ny, nextWay));
                    } else {
                        pq.offer(new Node(node.cnt + 6, nx, ny, nextWay));
                    }
                }
            }
        }

        return Integer.MAX_VALUE;
    }
}

class Node {
    int cnt;
    int x;
    int y;
    int way;

    Node(int cnt, int x, int y, int way) {
        this.cnt = cnt;
        this.x = x;
        this.y = y;
        this.way = way;
    }
}