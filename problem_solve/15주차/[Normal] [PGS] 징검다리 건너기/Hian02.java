/*
문제 정의

징검다리의 각 디딤돌에는 숫자가 적혀 있고 한 사람이 밟을 때마다 숫자가 1씩 감소합니다.
숫자가 0이 된 디딤돌은 이후의 사람이 밟을 수 없습니다.

밟을 수 없는 디딤돌이 있다면 다음으로 밟을 수 있는 가장 가까운 디딤돌까지 건너뛸 수 있지만, 한 번에 최대 k칸까지만 이동할 수 있습니다.
stones 배열과 최대 이동 거리 k가 주어졌을 때 징검다리를 끝까지 건널 수 있는 최대 사람 수를 구하는 문제입니다.
*/


/*
접근 방법

사람을 한 명씩 직접 이동시키면 stones의 값이 최대 200,000,000이므로 너무 많은 시뮬레이션이 필요합니다.
따라서 mid명의 사람이 건널 수 있는가?를 판단하는 이분 탐색을 사용합니다.

mid번째 사람이 징검다리를 건넌다고 생각해보겠습니다.
mid번째 사람이 오기 전에는 이미 mid - 1명의 사람이 지나간 상태입니다.

초기 숫자가 stones[i]인 디딤돌은 최대 stones[i]번 밟을 수 있으므로 mid번째 사람이 밟을 수 있으려면 stones[i] >= mid이어야 합니다.
반대로 stones[i] < mid라면 mid번째 사람은 해당 디딤돌을 밟을 수 없습니다.
따라서 배열을 왼쪽부터 확인하면서 stones[i] < mid인 디딤돌이 연속으로 몇 개 존재하는지 확인합니다.

밟을 수 없는 디딤돌이 k개 연속으로 존재한다면 그 구간을 뛰어넘으려면 k칸보다 멀리 이동해야 하므로 mid번째 사람은 징검다리를 건널 수 없습니다.

즉 연속된 stones[i] < mid의 개수 >= k이면 mid명은 건널 수 없습니다.

반대로 그런 구간이 없다면 mid명까지 건널 수 있습니다.
따라서 사람 수를 기준으로 이분 탐색할 수 있습니다.

left = 1
right = stones의 최댓값

mid명이 건널 수 있다면 더 많은 사람이 가능한지 확인하기 위해 left = mid + 1
mid명이 건널 수 없다면 사람 수를 줄이기 위해 right = mid - 1 로 이동합니다.
*/


/*
문제 풀이
*/
class Solution {
    /*
    people명의 사람이 징검다리를 건널 수 있는지 확인합니다.
    */
    static boolean canCross(int[] stones, int k, int people) {
        /*
        현재 연속으로 밟을 수 없는 디딤돌의 개수입니다.
        */
        int count = 0;

        for (int stone : stones) {
            /*
            people번째 사람이 이 돌을 밟을 수 없으면 연속 개수를 증가시킵니다.
            */
            if (stone < people) {
                count++;
                /*
                밟을 수 없는 돌이 k개 연속되면 더 이상 건널 수 없습니다.
                */
                if (count >= k) {
                    return false;
                }
            }
            /*
            밟을 수 있는 돌을 만나면 연속 구간이 끊기므로 0으로 초기화합니다.
            */
            else {
                count = 0;
            }
        }

        return true;
    }


    public int solution(int[] stones, int k) {
        /*
        최소 1명부터 확인합니다.
        */
        int left = 1;
        /*
        건널 수 있는 사람 수는 stones의 최댓값을 넘을 수 없습니다.
        */
        int right = 0;
        for (int stone : stones) {
            right = Math.max(right, stone);
        }
        int answer = 0;
        /*
        건널 수 있는 최대 사람 수를 이분 탐색으로 찾습니다.
        */
        while (left <= right) {
            int mid = left + (right - left) / 2;
            /*
            mid명이 건널 수 있다면 정답 후보로 저장하고 더 많은 사람이 가능한지 확인합니다.
            */
            if (canCross(stones, k, mid)) {
                answer = mid;
                left = mid + 1;
            }
            /*
            mid명이 건널 수 없다면 사람 수를 줄입니다.
            */
            else {
                right = mid - 1;
            }
        }
        return answer;
    }
}


/*
시간복잡도

stones의 길이를 N, stones의 최댓값을 M이라고 하겠습니다.

한 번의 canCross에서 stones 배열 전체를 확인하므로 O(N)이 필요합니다.
사람 수를 1부터 M까지 이분 탐색하므로 O(log M)번 확인합니다.

따라서 전체 시간복잡도는 O(N log M)입니다.

공간복잡도

별도의 배열을 사용하지 않고 변수 몇 개만 사용하므로 O(1)입니다.


핵심 정리
1. 사람을 한 명씩 실제로 건너게 하면 너무 느리므로 사람 수를 기준으로 이분 탐색합니다.
2. mid번째 사람이 밟을 수 있는 돌은 stones[i] >= mid인 돌입니다.
3. stones[i] < mid인 돌은 mid번째 사람이 밟을 수 없습니다.
4. 밟을 수 없는 돌이 k개 이상 연속되면 mid명은 건널 수 없습니다.
5. mid명이 가능하면 더 큰 사람 수를 확인하고, 불가능하면 더 작은 사람 수를 확인합니다.
6. 최종적으로 가능한 가장 큰 mid가 정답입니다.
*/
