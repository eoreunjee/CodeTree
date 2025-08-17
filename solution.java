import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int N = sc.nextInt(); // N번째 영화
        int count = 0;        // 몇 번째 종말의 수인지 카운트
        int number = 666;     // 첫 번째 종말의 수
        
        while (true) {
            // 문자열로 변환하여 "666"이 포함되어 있는지 체크
            if (String.valueOf(number).contains("666")) {
                count++;
            }
            // N번째 종말의 수를 찾으면 출력하고 종료
            if (count == N) {
                System.out.println(number);
                break;
            }
            number++;
        }
        sc.close();
    }
}
