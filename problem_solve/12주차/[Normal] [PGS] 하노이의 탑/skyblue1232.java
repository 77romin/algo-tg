import java.util.*;

class Solution {
    static List<int[]> list = new ArrayList<>();

    public int[][] solution(int n) {
        hanoi(n, 1, 3, 2);

        int[][] answer = new int[list.size()][2];

        for (int i = 0; i < list.size(); i++) {
            answer[i] = list.get(i);
        }

        return answer;
    }

    static void hanoi(int n, int start, int target, int empty) {
        if (n == 1) {
            list.add(new int[]{start, target});
            return;
        }

        // 위의 n-1개를 빈 기둥으로
        hanoi(n - 1, start, empty, target);

        // 가장 큰 원판을 target으로
        list.add(new int[]{start, target});

        // empty에 있던 n-1개를 target으로
        hanoi(n - 1, empty, target, start);
    }
}

// 풀이는 싸피에서 제공하는 알고리즘 강의 참고했습니다.
