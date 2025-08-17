import java.util.Scanner;

public class Main {
    
    // 숫자에 '666'이 연속으로 있는지 판별
    private static boolean contains666(int num) {
        int consecutiveSixes = 0;
        
        while (num > 0) {
            if (num % 10 == 6) {
                consecutiveSixes++;
                if (consecutiveSixes >= 3) return true;
            } else {
                consecutiveSixes = 0;
            }
            num /= 10;
        }
        return false;
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int N = sc.nextInt();
        int count = 0;
        int number = 666;
        
        while (true) {
            if (contains666(number)) {
                count++;
                if (count == N) {
                    System.out.println(number);
                    break;
                }
            }
            number++;
        }
        
        sc.close();
    }
}
