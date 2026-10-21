/*
문제 정의

응모자 아이디 user_id와 불량 사용자 패턴 banned_id가 주어집니다.
불량 사용자 패턴에는 '*'가 포함되어 있으며 '*'는 어떤 문자 하나와도 매칭될 수 있습니다.
단, 문자열의 길이는 반드시 같아야 합니다.

예를 들어, fr*d*는 frodo,fradi와 매칭될 수 있습니다.

각 banned_id에는 서로 다른 user_id 하나씩을 매칭해야 합니다.
같은 user_id를 두 번 사용할 수 없습니다.

모든 banned_id를 매칭했을 때 만들어지는 제재 아이디 목록의 경우의 수를 구해야 합니다.

이때 제재 아이디의 순서는 중요하지 않습니다.
예를 들어 [frodo, abc123]과 [abc123, frodo]는 같은 제재 아이디 목록으로 취급합니다.
*/


/*
접근 방법

DFS + 백트래킹을 사용합니다.
banned_id를 앞에서부터 하나씩 확인하면서 현재 패턴과 매칭되는 user_id를 선택합니다.
user_id의 개수는 최대 8개이므로 비트마스크를 사용해서 어떤 사용자를 이미 선택했는지 관리할 수 있습니다.

예를 들어, user_id 인덱스 0 1 2 3 4 중에서 0번과 3번 사용자를 선택했다면
mask = 01001형태로 표현할 수 있습니다.

DFS에서
index = 현재 확인하고 있는 banned_id의 위치
mask = 현재까지 선택된 user_id의 집합  으로 관리합니다.

현재 banned_id[index]와 매칭되는 user_id를 찾고, 아직 사용하지 않은 사용자라면 선택한 뒤 다음 banned_id로 이동합니다.

모든 banned_id를 처리했다면 현재 mask를 HashSet에 저장합니다.

HashSet에 mask를 저장하는 이유는 제재 아이디 목록의 순서를 무시하기 위해서입니다.

예를 들어, banned_id를 매칭하는 과정에서 frodo -> crodo -> abc123순서로 선택한 경우와
crodo -> frodo -> abc123 순서로 선택한 경우가 생겨도
선택된 사용자 집합은 같으므로 같은 bitmask가 만들어집니다.

따라서 HashSet이 자동으로 중복을 제거합니다.


문자열 매칭 조건은 다음과 같습니다.
1. user_id와 banned_id의 길이가 다르면 매칭 불가능합니다.
2. 같은 위치의 banned_id 문자가 '*'라면 어떤 문자든 가능합니다.
3. '*'가 아니라면 user_id의 문자와 정확히 같아야 합니다.
*/


/*
문제 풀이
*/

import java.util.*;

class Solution {

    static String[] users;
    static String[] banned;

    /*
    완성된 제재 사용자 집합을 저장합니다.

    mask 자체가 선택된 사용자들의 집합을 나타내므로
    순서가 달라도 같은 조합은 같은 값이 됩니다.
    */
    static HashSet<Integer> resultSet;

    /*
    user가 pattern과 매칭되는지 확인합니다.
    */
    static boolean match(String user, String pattern) {
        /*
        길이가 다르면 절대로 매칭될 수 없습니다.
        */
        if (user.length() != pattern.length()) {
            return false;
        }

        for (int i = 0; i < user.length(); i++) {

            char p = pattern.charAt(i);
            /*
            '*'는 어떤 문자와도 매칭됩니다.
            */
            if (p == '*') {
                continue;
            }
            /*
            '*'가 아닌데 문자가 다르면 매칭 실패입니다.
            */
            if (user.charAt(i) != p) {
                return false;
            }
        }

        return true;
    }
    /*
    index = 현재 매칭할 banned_id의 위치
    mask = 현재까지 선택한 user_id 집합
    */
    static void dfs(int index, int mask) {

        /*
        모든 banned_id를 매칭했다면 현재 선택된 사용자 집합을 저장합니다.
        */
        if (index == banned.length) {
            resultSet.add(mask);
            return;
        }

        /*
        현재 banned_id와 매칭할 수 있는 모든 user_id를 확인합니다.
        */
        for (int i = 0; i < users.length; i++) {

            /*
            이미 사용한 사용자라면 다시 사용할 수 없습니다.
            */
            if ((mask & (1 << i)) != 0) {
                continue;
            }

            /*
            현재 사용자가 banned_id 패턴과 매칭되지 않으면 사용할 수 없습니다.
            */
            if (!match(users[i], banned[index])) {
                continue;
            }

            /*
            i번 사용자를 선택하고 다음 banned_id로 이동합니다.
            */
            dfs(index + 1,mask | (1 << i));
        }
    }


    public int solution(String[] user_id, String[] banned_id) {
        users = user_id;
        banned = banned_id;

        resultSet = new HashSet<>();

        /*
        아직 아무 사용자도 선택하지 않은 상태인 mask = 0부터 시작합니다.
        */
        dfs(0, 0);

        /*
        서로 다른 제재 사용자 집합의 개수가 정답입니다.
        */
        return resultSet.size();
    }
}


/*
시간복잡도

user_id의 개수를 U, banned_id의 개수를 B라고 하겠습니다.

각 banned_id마다 최대 U명의 사용자를 확인할 수 있으므로 최악의 경우 순열 형태로 탐색합니다.

대략 P(U, B)개의 경우를 확인할 수 있습니다.

U <= 8이므로 최대 경우의 수는 매우 작습니다.

문자열 비교는 길이가 최대 8이므로 사실상 상수 시간입니다.

따라서 충분히 빠르게 해결할 수 있습니다.

핵심 정리
1. banned_id를 하나씩 보면서 매칭 가능한 user_id를 DFS로 선택합니다.
2. 같은 user_id를 두 번 사용할 수 없으므로 bitmask로 사용 여부를 관리합니다.
3. 모든 banned_id를 매칭하면 현재 mask를 HashSet에 저장합니다.
4. bitmask는 선택된 사용자 집합만 나타내므로 사용자 선택 순서가 달라도 같은 조합은 같은 값이 됩니다.
5. 최종적으로 resultSet.size()가 서로 다른 제재 아이디 목록의 개수입니다.
*/
