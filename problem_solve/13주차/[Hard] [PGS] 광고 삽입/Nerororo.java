import java.util.StringTokenizer;

class Solution {

    StringTokenizer st;

    public String solution(String play_time, String adv_time, String[] logs) {

        int playTime = timeToInt(play_time);
        int advTime = timeToInt(adv_time);

        long[] timeArr = new long[playTime + 2];

        logsToInt(logs, timeArr);

        for (int i = 1; i <= playTime; i++) {
            timeArr[i] += timeArr[i - 1];
        }

        // 0초부터 i초까지의 총 시청시간
        for (int i = 1; i <= playTime; i++) {
            timeArr[i] += timeArr[i - 1];
        }

        long maxViewTime = timeArr[advTime - 1];
        int answerTime = 0;

        // 광고 시작 시간
        for (int start = 1; start <= playTime - advTime; start++) {

            int end = start + advTime - 1;

            long viewTime =
                    timeArr[end] - timeArr[start - 1];

            if (viewTime > maxViewTime) {
                maxViewTime = viewTime;
                answerTime = start;
            }
        }

        return intToTime(answerTime);
    }

    private int timeToInt(String time) {

        st = new StringTokenizer(time, ":");

        int hour = Integer.parseInt(st.nextToken());
        int minute = Integer.parseInt(st.nextToken());
        int second = Integer.parseInt(st.nextToken());

        return hour * 3600 + minute * 60 + second;
    }

    private void logsToInt(String[] logs, long[] timeArr) {

        for (String str : logs) {

            st = new StringTokenizer(str, ":-");

            int startTime = Integer.parseInt(st.nextToken()) * 3600
                            + Integer.parseInt(st.nextToken()) * 60
                            + Integer.parseInt(st.nextToken());

            int endTime = Integer.parseInt(st.nextToken()) * 3600
                            + Integer.parseInt(st.nextToken()) * 60
                            + Integer.parseInt(st.nextToken());

            timeArr[startTime]++;
            timeArr[endTime]--;
        }
    }

    private String intToTime(int time) {

        int hour = time / 3600;
        int minute = (time % 3600) / 60;
        int second = time % 60;

        return String.format("%02d:%02d:%02d", hour, minute, second);
    }
}