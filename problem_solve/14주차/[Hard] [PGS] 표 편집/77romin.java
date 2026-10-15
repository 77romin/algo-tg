import java.util.*;

class Solution {
    
    /*
        n: 처음 표의 행 개수
        k: 행의 위치
        cmd: 명령어들
    */
    
    private class Node {
        boolean isExist;
        int pre, next;
        Node(boolean isExist, int pre, int next) {
            this.isExist = isExist; // 삭제 여부
            this.pre = pre; // 이전 노드
            this.next = next; // 다음 노드
        }
    }
    
    private static Node[] nodes; // 표
    
    public String solution(int n, int k, String[] cmd) {
        // 표 초기화
        nodes = new Node[n];
        
        for(int i=0; i<n; i++) 
            nodes[i] = new Node(true, i-1, i+1);
        
        // 양끝 칸 초기화
        nodes[0].pre = -1;
        nodes[n-1].next = -1;
        
        // 삭제 칸 저장하는 스택
        Stack<Integer> stack = new Stack<>();
        
        for(String oneLine : cmd) {
            String[] lineBits = oneLine.split(" ");
            switch(lineBits[0]) {
                case "D": 
                    
                    for(int i=0; i<Integer.parseInt(lineBits[1]); i++) // 다음 노드 타고 넘어가기
                        k = nodes[k].next;
                    
                    break;
                    
                case "U":
                    
                    for(int i=0; i<Integer.parseInt(lineBits[1]); i++) // 이전 노드 타고 넘어가기
                        k = nodes[k].pre;
                    
                    break;
                    
                case "C":
                    nodes[k].isExist = false; // 존재여부 false로 전환
                    stack.push(k); // 삭제 칸 저장 스택에 넣기
                    
                    int pre = nodes[k].pre; // 삭제한 노드의 이전 노드번호
                    int next = nodes[k].next; // 삭제한 노드의 다음 노드번호

                    // 양옆 노드 연결 갱신
                    if (pre != -1) nodes[pre].next = next; // 삭제한 노드가 첫 칸이 아니면
                    if (next != -1) nodes[next].pre = pre; // 삭제한 노드가 마지막 칸이 아니면
                    
                    if(next != -1) // 삭제한 노드가 마지막 칸이 아니면 다음 칸으로 이동
                        k = next;
                    else // 삭제한 노드가 마지막 칸이면 이전 칸으로 이동
                        k = pre;
                    
                    break;
                    
                case "Z":
                    if(stack.isEmpty()) break; // 스택이 비워져있으면 이번 명령은 무시
                    
                    int revive = stack.pop(); // 최근 삭제한 노드 되살리기
                    nodes[revive].isExist = true;
                    
                    pre = nodes[revive].pre; // 되살린 노드가 가리키는 이전 노드
                    next = nodes[revive].next; // 되살린 노드가 가리키는 다음 노드
                    
                    if (pre != -1) nodes[pre].next = revive; // 되살린 노드가 첫 칸이 아니면
                    if (next != -1) nodes[next].pre = revive; // 되살린 노드가 마지막 칸이 아니면
                    
                    break;
            }
        }
        
        StringBuilder sb = new StringBuilder(); // 시간초과를 해결하기 위해 사용
        for(Node node : nodes)
            if(node.isExist)
                sb.append("O");
            else
                sb.append("X");
        
        return sb.toString();
    }
}

/**
 * StringBuilder를 써야 효율성테스트를 통과한다. (String에 +연산으로 문자열 추가하면 안됨)
 * 시간복잡도: O(N+ sum(X))  (N개의 노드, X번의 이동)
 */