import java.util.ArrayList;
import java.util.List;

class Solution {
    static List<int[]> list = new ArrayList<>();

    public int[][] solution(int n) {
        hanoi(n, 1, 2, 3);

        int[][] answer = new int[list.size()][2];
        for (int i = 0; i < list.size(); i++) {
            answer[i] = list.get(i);
        }

        return answer;
    }

    static void hanoi(int n, int from, int sub, int to) {
        if (n == 1) {
            list.add(new int[]{from, to});
            return;
        }

        // 1. 위의 n-1개를 보조 기둥으로 이동
        hanoi(n - 1, from, to, sub);

        // 2. 가장 큰 원판을 목적지로 이동
        list.add(new int[]{from, to});

        // 3. 보조 기둥의 n-1개를 목적지로 이동
        hanoi(n - 1, sub, from, to);
    }
}

/*
시간 복잡도는 O(2^n)..
하노이의 탑 재귀 식이 이해가 잘 안가서 GPT 형님께 여쭤봤더니

원판 n개를 옮기려면:
1. n-1개를 옮긴다.
2. 가장 큰 원판을 1번 옮긴다.
3. n-1개를 다시 옮긴다.

그래서
T(n) = 2T(n-1) + 1

이 되고 전개하면:
T(n)
= 2T(n-1) + 1
= 2(2T(n-2) + 1) + 1
= 4T(n-2) + 3
= 8T(n-3) + 7
...
= 2^n - 1

즉 실제 이동 횟수가 정확히:
2^n - 1

이야.
따라서 시간복잡도는:
O(2^n)

이 돼.

라고 하시네
 */