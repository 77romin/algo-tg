class Solution {
    public boolean solution(String[] phone_book) {
        for(int i=0; i<phone_book.length; i++) {
            for(int j=0; j<phone_book.length; j++) {
                if(i!=j && phone_book[j].startsWith(phone_book[i]))
                    return false;
            }
        }
        return true;
    }
}

/**
 * <Memo>
 * 내 식대로 풀어보기: String클래스의 startsWith함수 활용하여 문자열 포함여부 확인
 * 시간복잡도: O(N^2) -> 효율성 테스트 3~4 시간초과로 인한 실패
 */
