package ss.plingpay;

import org.junit.Test;

import java.text.DecimalFormat;

import ss.plingpay.utilz.Utilz;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {


    @Test
    public void main() throws Exception {
//        System.out.print(addFee("200"));



//        System.out.print(findAab("0.27999779345022"));
        System.out.print(findAw(2, 2));


    }


    static String findAab(String btcAmount) {

        double Aab;

        double A0 = Double.parseDouble(Utilz.removeComma(btcAmount));

        double tf = 0.002500000;

        double wf = 0.0005000000;

//        Tf(x1) = A0b * tf(x1)
        tf = A0 * tf;
//        Aab = A0 -tf(X1)-wf(X1)
        Aab = A0 - tf - wf;

        return Aab + "";

    }


    private static double calculatePercent(String amount) {

        double am = Double.parseDouble(amount);
        double percent = 0.5;

        return (am / 100) * percent;

    }


    static String findAw(double btc, double exchange) {

        double c = 0.01;
        double d = 124;

        double firstPart = btc * exchange - btc * 0.002 * exchange - d;
        double secondPart = 1 + c;

        double result = firstPart / secondPart;

        return result + "";

    }

    private String addFee(String amount) {

        double result = calculatePercent(amount) + Double.parseDouble(amount);

        return new DecimalFormat("##.##").format(result);
    }


}