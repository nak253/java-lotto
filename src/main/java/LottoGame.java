import view.InputView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LottoGame {

    private List<Lotto> lottos;
    private int money;

    public LottoGame(int money) {
        lottos = new ArrayList<>();
        this.money = money;
        int count = money / 1000;

        System.out.println(count + "개를 구매하셨습니다.");
        createLotto(count, createLottoBalls());
    }

    public void start() {
        print();

        String[] split = InputView.winningNumber().split(", ");

        int[] winingNumber = new int[6];

        for (int i = 0; i < winingNumber.length; i++) {
            winingNumber[i] = Integer.parseInt(split[i]);
        }

        int[] result = new int[7];

        for (Lotto lotto : lottos) {
            int rank = lotto.check(winingNumber);
            result[rank]++;
        }

        printResult(result);
    }

    private List<Integer> createLottoBalls() {
        List<Integer> lottoBalls = new ArrayList<>();

        for (int lottoBall = 1; lottoBall <= 45; lottoBall++) {
            lottoBalls.add(lottoBall);
        }
        return lottoBalls;
    }

    private void createLotto(int count, List<Integer> lottoBalls) {
        for (int i = 0; i < count; i++) {
            Collections.shuffle(lottoBalls);
            ArrayList<Integer> al = new ArrayList<>(lottoBalls.subList(0, 6));
            lottos.add(new Lotto(al));
        }
    }

    public void print() {
        for (Lotto lotto : lottos) {
            System.out.println(lotto);
        }
    }

    private void printResult(int[] result) {
        printStatisticalResult(result);
        System.out.printf("총 수익률은 %5.2f%%입니다.", method(result));
    }

    private void printStatisticalResult(int[] result) {
        System.out.println("당첨 통계");
        System.out.println("---------");
        for (int i = 3; i < result.length; i++) {
            System.out.println(i+"개 일치 ("+Rank.aaa(i)+"원)- " + result[i] + "개");
        }
    }

    private double method(int[] result) {
        double benefit = 0;

        for (int i = 3; i < result.length; i++) {
            benefit += Rank.aaa(i) * result[i];
        }

        final double i = ((benefit / money) - 1) * 100;

        return i;
    }

}
