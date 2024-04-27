//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final int DIGIT = 3; // 게임에 사용될 자릿수
    private static final int MIN_NUMBER = 1; // 난수의 최솟값
    private static final int MAX_NUMBER = 9; // 난수의 최댓값

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); //사용자 입력을 받기 위한 Scanner 객체 생성
        System.out.println("숫자 야구 게임을 시작합니다."); // 게임 시작 메시지 출력

        boolean isNewGame = true; // 새 게임 여부를 나타내는 변수
        while (isNewGame) { // 새 게임이 시작되는 동안 반복
            playBaseballGame(scanner); // 야구 게임을 진행하는 메서드
            System.out.println("게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요: ");
            int choice = scanner.nextInt();
            if (choice == 2) { // 2 선택하면 게임종료
                isNewGame = false;
            }
        }
        scanner.close(); // scanner 객체 닫기
    }

    // 야구 게임을 진행하는 메서드
    private static void playBaseballGame(Scanner scanner) {
        int[] computerNumbers = generateRandomNumbers(); // 컴터 숫자 배열 생성
        while (true) { // 게임 종료 조건 만족할때까지 반복
            int[] userNumbers = getUserNumbers(scanner); // 사용자 입력받은 숫자 배열 생성
            String result = calculateResult(computerNumbers, userNumbers); // 게임 결과 계산
            System.out.println(result); // 결과 출력
            if (result.equals("3스트라이크")) { // 게임 종료 조건
                break; // 반복문 빠져나오고 게임 종료
            }
        }
    }

    // 컴퓨터가 선택한 임의의 숫자 배열 생성 메서드
    private static int[] generateRandomNumbers() {
        Random random = new Random(); // 난수 생성을 위한 Random 객체 생성
        int[] numbers = new int[DIGIT]; // 컴터가 선택한 숫자 배열 생성

        for (int i = 0; i < DIGIT; i++) { // 3자리수 생성하기 위해 3번 반복
            numbers[i] = random.nextInt(MAX_NUMBER - MIN_NUMBER + 1) + MIN_NUMBER; // bound는 0 ~ bound-1 까지 반환하는데 그래서 +1한거고 1~9까지 중에 랜덤 할거라서 +min한거다
            for (int j = 0; j < i; j++) { // 중복된 숫자인지 확인하기 위해 이전에 생성된 숫자들과 비교
                if (numbers[i] == numbers[j]) { // 중복된 숫자가 있을 경우
                    i--; // 다시 숫자 생성
                    break;
                }
            }
        }
        return numbers; // 생성된 숫자 배열 반환
    }

    // 사용자로부터 숫자 입력받는 메서드
    private static int[] getUserNumbers(Scanner scanner) {
        System.out.print("서로 다른 " + DIGIT + "자리의 숫자를 입력하세요: ");
        String input = scanner.next(); // 사용자로부터 입력받은 문자열

        if (input.length() != DIGIT || !input.matches("[0-9]+")) { // 입력값이 유효한지 검사
            System.out.println("잘못된 입력입니다. 숫자 " + DIGIT + "자리를 입력해주세요.");
            return getUserNumbers(scanner); // 메서드 호출하여 다시 입력 받기
        }

        int[] numbers = new int[DIGIT]; // 사용자가 입력한 숫자 배열 생성
        for (int i = 0; i < DIGIT; i++) { // 입력값을 숫자 배열로 변환
            numbers[i] = input.charAt(i) - '0';
        }
        return numbers; // 변환된 숫자 배열 반환
    }

    private static String calculateResult(int[] computerNumbers, int[] userNumbers) {
        int strike = 0; //스트라이크 개수 초기화
        int ball = 0; //볼 개수 초기화

        for (int i = 0; i < DIGIT; i++) { // 각 자리에 대해 반복하여 스트라이크와 볼 계수 계산
            int userNumber = userNumbers[i];
            if (computerNumbers[i] == userNumbers[i]) { //같은 자리에 같은 숫자인 경우
                strike++;
            } else if (Arrays.stream(computerNumbers).anyMatch(num -> num == userNumber)) { // 다른 자리에 같은 숫자인 경우
                ball++; // 볼 증가
            }
        }
        if (strike == DIGIT) { // 3스트라이크인 경우
            return "3스트라이크";
        } else if (strike > 0 || ball > 0) { //스트라이크 또는 볼이 있는 경우
            return ball + "볼 " + strike + "스트라이크";
        } else {
            return "낫싱";
        }
    }
}


