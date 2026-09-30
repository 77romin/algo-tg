import java.util.*;

class Solution {

    public int solution(int[] arrows) {
        int answer = 0;

        int[] dy = {1, 1, 0, -1, -1, -1, 0, 1};
        int[] dx = {0, 1, 1, 1, 0, -1, -1, -1};

        HashSet<Long> nodes = new HashSet<>();
        HashSet<Long> lines = new HashSet<>();

        int y = 0;
        int x = 0;

        nodes.add(nodeKey(y, x));

        for (int dir : arrows) {

            // 대각선 교차 처리를 위해 2번 이동
            for (int k = 0; k < 2; k++) {

                int ny = y + dy[dir];
                int nx = x + dx[dir];

                long nextNode = nodeKey(ny, nx);
                long line = lineKey(y, x, dir);

                // 이미 방문한 점인데
                // 처음 지나가는 선이면 새로운 방 생성
                if (nodes.contains(nextNode) && !lines.contains(line)) {
                    answer++;
                }

                nodes.add(nextNode);

                // 현재 -> 다음 방향
                lines.add(line);

                // 다음 -> 현재 방향
                lines.add(lineKey(ny, nx, (dir + 4) % 8));

                y = ny;
                x = nx;
            }
        }

        return answer;
    }

    long nodeKey(int y, int x) {
        return x + (long)y * 100000000;
    }

    long lineKey(int y, int x, int dir) {
        return nodeKey(y, x) * 10 + dir;
    }
}