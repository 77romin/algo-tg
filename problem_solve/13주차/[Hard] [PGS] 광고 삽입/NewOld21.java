class Solution {

    public String solution(String play_time, String adv_time, String[] logs) {

        int play = toSecond(play_time);
        int adv = toSecond(adv_time);

        long[] time = new long[play + 2];

        for (String log : logs) {

            String[] split = log.split("-");

            int start = toSecond(split[0]);
            int end = toSecond(split[1]);

            time[start]++;
            time[end]--;
        }

        for (int i = 1; i <= play; i++) {
            time[i] += time[i - 1];
        }

        for (int i = 1; i <= play; i++) {
            time[i] += time[i - 1];
        }

        long max = time[adv - 1];
        int answerTime = 0;

        for (int start = 1; start + adv <= play; start++) {

            int end = start + adv - 1;

            long watchTime = time[end] - time[start - 1];

            if (watchTime > max) {
                max = watchTime;
                answerTime = start;
            }
        }

        return toStringTime(answerTime);
    }

    int toSecond(String time) {

        String[] split = time.split(":");

        int hour = Integer.parseInt(split[0]);
        int minute = Integer.parseInt(split[1]);
        int second = Integer.parseInt(split[2]);

        return hour * 3600
                + minute * 60
                + second;
    }

    String toStringTime(int time) {

        int hour = time / 3600;
        time %= 3600;

        int minute = time / 60;
        int second = time % 60;

        return String.format(
                "%02d:%02d:%02d",
                hour,
                minute,
                second);
    }
}