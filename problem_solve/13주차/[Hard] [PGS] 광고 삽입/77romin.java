class Solution {
    /**
     * play_time: 전체 재생시간 길이
     * adv_time: 공익광고 재생시간 길이
     * log: 시청자 재생 구간 배열
     */
    
    private int ansTime;
    
    private int playTime;
    private int advTime;
    private Node[] playLogs;
    
    private long[] time;
    private long[] prefix;
    
    class Node {
        int startTime, endTime;
        
        Node(int startTime, int endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
        }
    }
    
    private String transAnsTime() {
        String answer = "";
        int unit = 3600;
        for(int i=0; i<2; i++) {
            int part = ansTime/unit;
            
            ansTime %= unit;
            unit /= 60;
            
            if(part<10) answer += "0";
            answer += part+":";
        }
        
        if(ansTime<10) answer += "0";
        answer += ansTime;
        return answer;
    }
    
    private void init(String play_time, String adv_time, String[] logs) {
        ansTime=playTime=advTime=0;
        
        // 총 영상길이를 초 단위로 변환
        String[] ptBits = play_time.split(":", 3);
        int unit = 3600;
        for(String ptBit : ptBits) {
            playTime += Integer.parseInt(ptBit)*unit;
            unit/=60;
        }
        time = new long[playTime+1]; // 시간의 흐름: 0초 ~ 총 영상길이(초)
        prefix = new long[playTime+1]; // 시청자 누적합 배열
        
        // 광고 길이를 초 단위로 변환
        String[] atBits = adv_time.split(":", 3);
        unit = 3600;
        for(String atBit : atBits) {
            advTime += Integer.parseInt(atBit)*unit;
            unit/=60;
        }
        
        // 시청자 재생구간을 초 단위로 변환 (시작시각, 종료시각)
        playLogs = new Node[logs.length];
        int cnt=0;
        for(String log : logs) {
            String[] oneLog = log.split("-", 2);
            
            int startTime=0;
            String[] startBits = oneLog[0].split(":", 3);
            unit = 3600;
            for(String startBit : startBits) {
                startTime += Integer.parseInt(startBit)*unit;
                unit/=60;
            }
            
            int endTime=0;
            String[] endBits = oneLog[1].split(":", 3);
            unit = 3600;
            for(String endBit : endBits) {
                endTime += Integer.parseInt(endBit)*unit;
                unit/=60;
            }
            
            playLogs[cnt++] = new Node(startTime, endTime);
        }
        
        // 각 시점에서의 시청자 수 저장
        for(Node playLog : playLogs) {
            for(int i=playLog.startTime; i<playLog.endTime; i++)
                time[i] += 1;
        }
        
        // 누적합 저장
        prefix[0] = time[0];
        for(int i=1; i<prefix.length; i++)
            prefix[i] = prefix[i-1] + time[i];
        
    }
    
    private void findTime() {
        if(playTime==advTime) return; // 총 영상길이가 광고영상길이와 같을 경우
        long maxWatch = prefix[advTime-1]; // 0초에 광고 시작할 때로 누적시청자수 초기화
        
        for(int i=1; i<=playTime-advTime; i++) {
            long watch = prefix[i+advTime-1]-prefix[i-1];
            if(maxWatch<watch) {
                maxWatch = watch;
                ansTime = i;
            }
        }
        
    }
    
    public String solution(String play_time, String adv_time, String[] logs) {
        init(play_time, adv_time, logs);
        findTime();
        System.out.println(ansTime);
        return transAnsTime();
    }
}

/**
 * 정규화시키기: h*3600+m*60+s
 * 알고리즘: 누적합
 * 시간복잡도: O(N*K) --> 테스트17 실패
 */
