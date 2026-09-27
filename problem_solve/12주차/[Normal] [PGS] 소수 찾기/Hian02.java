/*
문제 정의

한 자리 숫자가 적힌 종이 조각들이 주어집니다.
각 종이 조각은 한 번씩만 사용할 수 있으며, 종이 조각의 일부 또는 전부를 사용해서 여러 숫자를 만들 수 있습니다.
예를 들어 numbers = "17"이라면 만들 수 있는 숫자는1 7 17 71입니다.
이 중 소수는 7 17 71이므로 정답은 3입니다.

numbers = "011"이라면
01과 1, 011과 11처럼 앞에 0이 붙어 있어도 숫자로는 같은 값으로 취급합니다.
따라서 동일한 숫자는 한 번만 계산해야 합니다.
만들 수 있는 서로 다른 숫자 중 소수의 개수를 구하는 문제입니다.
*/


/*
접근 방법

DFS + 백트래킹을 사용해서
만들 수 있는 모든 숫자를 생성합니다.

1. 숫자 만들기
각 자리의 숫자를 사용한다 / 아직 사용하지 않는다 형태로 관리합니다.
visited[i] = numbers의 i번째 종이 조각를 현재 숫자를 만드는 데 사용했는지 여부

예를 들어 numbers = "17"이라면
1 -> 17
7 -> 71
과 같이 탐색할 수 있습니다.

현재까지 만든 숫자가 current이고 다음 숫자가 digit이라면 새로운 숫자는 current * 10 + digit으로 만들 수 있습니다.

예를 들어 current = 17, digit = 3이라면 17 * 10 + 3 = 173이 됩니다.

2. 숫자의 길이
종이 조각을 반드시 모두 사용할 필요는 없습니다.
따라서 1자리, 2자리 ... numbers.length()자리 숫자를 모두 확인해야 합니다.

DFS에서 새로운 숫자를 하나 만들 때마다 HashSet에 바로 저장하면 모든 길이의 숫자를 자연스럽게 저장할 수 있습니다.

3. 중복 숫자 제거
numbers에는 같은 숫자가 여러 개 있을 수 있습니다.
예를 들어 "011"에서 서로 다른 1번 종이와 1번 종이를 사용해서 같은 숫자 1이 여러 번 만들어질 수 있습니다.

또한 1 01 001은 모두 정수로는 1입니다.

따라서 HashSet<Integer>를 사용해서 만들어진 숫자의 중복을 제거합니다.

4. 소수 판별
만들어진 서로 다른 숫자를 하나씩 확인합니다.
소수는 1보다 크고, 1과 자기 자신 이외의 약수가 없는 수입니다.
2부터 number - 1까지 전부 나눠볼 필요는 없습니다.

어떤 수 N이 합성수라면 sqrt(N)이하의 약수가 반드시 존재합니다.
따라서 2부터 sqrt(N)까지만 확인합니다.
코드에서는 i * i <= number 조건을 사용합니다.

5. 최종 과정
DFS로 모든 숫자 생성
        ->
HashSet으로 중복 제거
        ->
각 숫자가 소수인지 확인
        ->
소수 개수 반환
*/


/*
문제 풀이
*/

import java.util.*;

class Solution {
    /*
    입력받은 숫자들을 저장합니다.
    */
    static char[] numbers;

    /*
    현재 숫자를 만들 때 각 종이 조각을 사용했는지 저장합니다.
    */
    static boolean[] visited;

    /*
    만들 수 있는 숫자를 저장합니다.
    HashSet을 사용하기 때문에 같은 숫자가 여러 번 만들어져도 하나만 저장됩니다.
    */
    static HashSet<Integer> set;
    /*
    DFS를 이용해서 만들 수 있는 모든 숫자를 생성합니다.
    current = 현재까지 만든 숫자
    */
    static void dfs(int current) {
        /*
        아직 사용하지 않은 종이 조각을 하나씩 선택합니다.
        */
        for (int i = 0; i < numbers.length; i++) {
            /*
            이미 현재 숫자를 만드는 데 사용한 종이 조각이라면 사용할 수 없습니다.
            */
            if (visited[i]) {
                continue;
            }
            /*
            현재 종이 조각을 사용합니다.
            */
            visited[i] = true;
            /*
            char 형태의 숫자를 int로 변환합니다.
            예를 들어 '7' - '0' = 7
            */
            int digit = numbers[i] - '0';
            /*
            기존 숫자의 뒤에 새로운 숫자를 붙입니다.
            예를 들어 current = 17 digit = 3이라면 next = 173
            */
            int next = current * 10 + digit;
            /*
            만들어진 숫자를 HashSet에 저장합니다.
            앞에 0이 붙어 있어도 int로 계산하기 때문에 자동으로 같은 숫자가 됩니다.
            예를 들어 1 01 001 모두 정수 1로 저장됩니다.
            */
            set.add(next);
            /*
            현재 숫자의 뒤에 다른 종이 조각을 추가해서 더 긴 숫자를 만들어 봅니다.
            */
            dfs(next);
            /*
            백트래킹 현재 종이 조각 사용을 취소해서 다른 순서에서도 사용할 수 있게 합니다.
            */
            visited[i] = false;
        }
    }

    /*
    number가 소수인지 확인합니다.
    */
    static boolean isPrime(int number) {
        /*
        0과 1은 소수가 아닙니다.
        */
        if (number < 2) {
            return false;
        }

        /*
        2부터 sqrt(number)까지 나누어 봅니다.
        하나라도 나누어 떨어진다면 소수가 아닙니다.
        */
        for (int i = 2; i * i <= number; i++) {
            if (number % i == 0) {
                return false;
            }
        }

        /*
        나누어 떨어지는 수가 없다면 소수입니다.
        */
        return true;
    }


    public int solution(String input) {
        /*
        문자열을 char 배열로 변환합니다.
        */
        numbers = input.toCharArray();
        /*
        각 종이 조각의 사용 여부를 저장합니다.
        */
        visited = new boolean[numbers.length];
        /*
        만들어지는 숫자들의 중복을 제거하기 위한 HashSet입니다.
        */
        set = new HashSet<>();
        /*
        아무 숫자도 만들지 않은 상태인 0부터 DFS를 시작합니다.
        */
        dfs(0);
        int answer = 0;
        /*
        중복이 제거된 모든 숫자를 확인합니다.
        */
        for (int number : set) {
            /*
            현재 숫자가 소수라면 정답을 증가시킵니다.
            */
            if (isPrime(number)) {
                answer++;
            }
        }
        return answer;
    }
}


/*
시간복잡도

종이 조각의 개수를 N이라고 하겠습니다.
N <= 7입니다.

만들어지는 경우의 수는 P(N, 1) + P(N, 2) + ... + P(N, N)입니다.
N = 7일 때도 경우의 수가 크지 않기 때문에완전탐색이 가능합니다.
각 숫자의 소수 판별은 2부터 sqrt(number)까지 확인하므로 O(sqrt(number))
*/
