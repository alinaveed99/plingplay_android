package ss.plingpay.data.transactionRepository.new_transactions;

import java.text.DecimalFormat;

import ss.plingpay.utilz.Utilz;

/**
 * Transaction Helper Class
 */

@Deprecated

class Thc {


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

    static String findAw(double btc, double exchange) {

        double c = 0.01;
        double d = 124;

        double firstPart = btc * exchange - btc * 0.002 * exchange - d;
        double secondPart = 1 + c;

        double result = firstPart / secondPart;

        return result + "";

    }


    // VND CALCULATION
    static String findAabVnd(String btcAmount) {

        double Aab;

        double A0 = Double.parseDouble(Utilz.removeComma(btcAmount));

        double tf = 0.002500000;
//        double tf = findTf(Double.parseDouble(Utilz.removeComma(btcAmount)));

        double wf = 0.0005;

//        Tf(x1) = A0b * tf(x1)
        tf = A0 * tf;
//        Aab = A0 -tf(X1)-wf(X1)
        Aab = A0 - tf - wf;

        return Aab + "";

    }

    static String findAwVnd(double btc, double exchange) {

        double c = 0.0017;
        double d = 40000;

        //Aw = (Aab*R2 - tf(X2)*R2 - D)/(1 + C)

//        (Aab*R2 - tf(X2)*R2 - D)
        double firstPart = btc * exchange - btc * 0.0025 * exchange - d;
//        (1 + C)
        double secondPart = 1 + c;

        double result = firstPart / secondPart;

        return result + "";

    }

    private static double findTf(double btcAmount) {
        return btcAmount / 4;
    }


    private static double calculatePercent(String amount) {

        double am = Double.parseDouble(amount);
        double percent = 0.5;

        return (am / 100) * percent;

    }

    public static String addFee(String amount) {

        double result = calculatePercent(amount) + Double.parseDouble(amount);

        return new DecimalFormat("##.##").format(result);
    }
}
