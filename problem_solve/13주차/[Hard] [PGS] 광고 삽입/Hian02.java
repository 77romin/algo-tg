/*
문제 정의

동영상의 전체 재생시간 play_time, 광고의 길이 adv_time, 시청자들의 재생 기록 logs가 주어집니다.
광고는 동영상의 특정 시각부터 adv_time 동안 재생되며, 광고가 재생되는 구간에서 시청자들이 영상을 보고 있었던 누적 시간을 최대화해야 합니다.

예를 들어 어떤 1초 동안 3명의 시청자가 영상을 보고 있었다면 그 1초의 누적 재생시간은 3초입니다.
광고 구간 전체에 대해 이러한 시청자 수를 모두 더했을 때 값이 가장 큰 시작 시각을 구합니다.

누적 재생시간이 최대인 시작 시각이 여러 개라면 가장 빠른 시각을 반환해야 합니다.
*/


/*
접근 방법

logs의 개수는 최대 300,000개이고 동영상 길이는 최대 99:59:59이므로 초 단위로 변환하면 약 360,000초입니다.
모든 광고 시작 위치마다 logs 전체를 확인하면 시간복잡도가 너무 커지므로 누적합을 사용합니다.

1. 모든 시간을 초 단위로 변환합니다.
예를 들어 01:30:59라면 1 * 3600 + 30 * 60 + 59초로 변환합니다.

2. 차이 배열을 사용해서 각 초에 몇 명이 영상을 보고 있는지 계산합니다.
시청 기록이 start부터 end까지라면 실제 시청 구간은 [start, end)입니다.
따라서 start초에는 시청자가 추가되고 end초부터는 시청하지 않습니다.

viewer[start] += 1
viewer[end] -= 1

3. viewer 배열에 누적합을 한 번 적용하면 각 초마다 실제 시청자 수를 구할 수 있습니다.
예를 들어 viewer[100] = 3이라면 100초부터 101초까지 3명이 영상을 보고 있다는 의미입니다.

4. 시청자 수에 다시 누적합을 적용해서 0초부터 특정 시각까지의 누적 재생시간을 구합니다.
prefix[t] = 0초부터 t초 직전까지의 누적 재생시간으로 정의합니다.

그러면 광고가 start초부터 start + adv초까지 재생될 때 누적 재생시간은 prefix[start + adv] - prefix[start]로 O(1)에 계산할 수 있습니다.

5. 광고를 삽입할 수 있는 모든 시작 시각 0 ~ playTime - advTime을 확인합니다.
현재 누적 재생시간이 기존 최댓값보다 클 때만 정답을 갱신합니다.
같은 경우에는 갱신하지 않으면 자동으로 가장 빠른 시작 시각이 유지됩니다.

시청자 수는 최대 300,000명이고 동영상 길이도 매우 길기 때문에 누적 재생시간은 int 범위를 넘어갈 수 있습니다.
따라서 누적합과 광고 구간의 누적 재생시간은 반드시 long을 사용합니다.
*/


/*
문제 풀이
*/

class Solution {
    /*
    HH:MM:SS 형식의 시간을 초 단위 정수로 변환합니다.
    예를 들어 01:30:59 -> 1 * 3600 + 30 * 60 + 59 = 5459초입니다.
    */
    static int toSecond(String time) {
        int hour = Integer.parseInt(time.substring(0, 2));
        int minute = Integer.parseInt(time.substring(3, 5));
        int second = Integer.parseInt(time.substring(6, 8));

        return hour * 3600 + minute * 60 + second;
    }
    /*
    초 단위 시간을 HH:MM:SS 형태로 변환합니다.
    */
    static String toTime(int time) {
        int hour = time / 3600;
        time %= 3600;

        int minute = time / 60;
        int second = time % 60;

        return String.format("%02d:%02d:%02d", hour, minute, second);
    }

    public String solution(String play_time, String adv_time, String[] logs) {
        /*
        전체 영상 시간과 광고 시간을 초 단위로 변환합니다.
        */
        int playTime = toSecond(play_time);
        int advTime = toSecond(adv_time);

        /*
        change[t]는 t초에서 시청자 수가 얼마나 변하는지를 저장하는 차이 배열입니다.
        playTime 위치에도 종료 정보를 기록해야 하므로 크기를 playTime + 1로 만듭니다.
        */
        long[] change = new long[playTime + 1];

        /*
        모든 시청 기록에 대해 시작 시각에는 +1, 종료 시각에는 -1을 기록합니다.
        시청 구간은 [start, end)이므로 end 시각부터는 해당 시청자가 포함되지 않습니다.
        */
        for (String log : logs) {
            int start = toSecond(log.substring(0, 8));
            int end = toSecond(log.substring(9, 17));

            change[start]++;
            change[end]--;
        }

        /*
        첫 번째 누적합을 통해 각 초마다 몇 명이 영상을 보고 있었는지 계산합니다.
        change[t]는 이제 t초부터 t+1초까지의 시청자 수가 됩니다.
        */
        for (int i = 1; i < playTime; i++) {
            change[i] += change[i - 1];
        }

        /*
        prefix[t] = 0초부터 t초 직전까지의 누적 재생시간입니다.

        prefix[0] = 0
        prefix[1] = 0초 ~ 1초의 누적 재생시간
        prefix[2] = 0초 ~ 2초의 누적 재생시간
        ...
        */
        long[] prefix = new long[playTime + 1];

        for (int i = 0; i < playTime; i++) {
            prefix[i + 1] = prefix[i] + change[i];
        }

        /*
        광고 시작 시각이 0초일 때의 누적 재생시간을 초기 최댓값으로 설정합니다.
        광고 구간은 [0, advTime)입니다.
        */
        long maxWatchTime = prefix[advTime] - prefix[0];
        int answerStart = 0;

        /*
        광고를 삽입할 수 있는 모든 시작 시각을 확인합니다.
        start + advTime <= playTime이어야 하므로 start의 최댓값은 playTime - advTime입니다.
        */
        for (int start = 1; start + advTime <= playTime; start++) {
            /*
            광고 구간 [start, start + advTime)의 누적 재생시간을 누적합으로 O(1)에 계산합니다.
            */
            long watchTime = prefix[start + advTime] - prefix[start];

            /*
            더 큰 누적 재생시간을 발견한 경우에만 정답을 갱신합니다.
            watchTime == maxWatchTime인 경우에는 갱신하지 않습니다.
            앞에서부터 시작 시각을 확인하고 있기 때문에 이렇게 하면 가장 빠른 시각이 자동으로 유지됩니다.
            */
            if (watchTime > maxWatchTime) {
                maxWatchTime = watchTime;
                answerStart = start;
            }
        }

        /*
        초 단위 정답을 HH:MM:SS 형식으로 변환하여 반환합니다.
        */
        return toTime(answerStart);
    }
}

/*
시간복잡도

logs의 개수를 L, 동영상 전체 길이를 초 단위로 P라고 하겠습니다.

모든 로그를 한 번씩 확인하는 데 O(L)이 필요합니다.
각 초의 시청자 수를 계산하는 첫 번째 누적합에 O(P), 누적 재생시간을 계산하는 두 번째 누적합에 O(P), 모든 광고 시작 위치를 확인하는 데 O(P)가 필요합니다.
따라서 전체 시간복잡도는 O(L + P)입니다.
logs <= 300,000이고 play_time은 최대 약 360,000초이므로 충분히 빠르게 해결할 수 있습니다.

공간복잡도
초 단위 시청자 수를 저장하는 change 배열과 누적 재생시간을 저장하는 prefix 배열을 사용합니다.
두 배열의 크기는 동영상 길이에 비례하므로 공간복잡도는 O(P)입니다.

핵심 정리
1. 모든 시각을 초 단위로 변환합니다.
2. 각 시청 기록 [start, end)에 대해 change[start]++, change[end]--를 수행합니다.
3. 첫 번째 누적합으로 각 초마다 시청 중인 사람 수를 구합니다.
4. 두 번째 누적합으로 특정 시간까지의 누적 재생시간을 구합니다.
5. 광고 구간 [start, start + advTime)의 누적 재생시간은 prefix[start + advTime] - prefix[start]입니다.
6. 모든 시작 시각을 앞에서부터 확인하고 더 큰 값일 때만 갱신하면 누적 재생시간이 같은 경우 가장 빠른 시작 시각이 선택됩니다.
7. 누적 재생시간은 int 범위를 넘을 수 있으므로 반드시 long을 사용합니다.
*/
