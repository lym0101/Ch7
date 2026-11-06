import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class stockTrader {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File("tiny.txt"));
        String symbol = in.next();
        int n = in.nextInt();

        // TODO: create the three arrays, each with n elements
        int years[] = new int[n];
        int months[] = new int[n];
        double[] prices = new double[n];

        // TODO: call loadData, then printData
        loadData(in, years, months, prices);
        printData(years, months, prices);

        int lowest = indexOfLowest(prices);
        System.out.println(
            "Lowest: " + prices[lowest] + " in " + monthName(months[lowest]) + " " + years[lowest]);

        int highest = indexOfHighest(prices);
        System.out.println("Highest: " + prices[highest] + " in " + monthName(months[highest]) + " "
            + years[highest]);

        int biggestDrop = indexOfBiggestDrop(prices);
        System.out.println("Biggest Drop: " + prices[biggestDrop] + " in "
            + monthName(months[biggestDrop]) + " " + years[biggestDrop]);

        System.out.printf("%s lowest: $%.2f in %s %d%n", symbol, prices[indexOfLowest(prices)],
            monthName(months[indexOfLowest(prices)]), years[indexOfLowest(prices)]);
        System.out.printf("%s highest: $%.2f in %s %d%n", symbol, prices[indexOfHighest(prices)],
            monthName(months[indexOfHighest(prices)]), years[indexOfHighest(prices)]);

        double result = simulate(years, months, prices);
        System.out.printf("Final value: $%,.2f%n", result);
    }
    public static void loadData(Scanner in, int[] years, int[] months, double[] prices) {
        for (int i = 0; i < years.length; i++) {
            years[i] = in.nextInt();
            months[i] = in.nextInt();
            prices[i] = in.nextDouble();
        }
    }
    public static String monthName(int month) {
        String[] names = {
            "Jan", "Feb", "Mar", "Apr", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        return names[month - 1];
    }
    public static void printData(int[] years, int[] months, double[] prices) {
        for (int i = 0; i < prices.length; i++) {
            System.out.printf("$%.2f in %s %d%n", prices[i], monthName(months[i]), years[i]);
        }
    }
    public static int indexOfLowest(double[] prices) {
        int lowestIndex = 0;
        for (int i = 0; i < prices.length; i++) {
            if (prices[i] < prices[lowestIndex]) {
                lowestIndex = i;
            }
        }
        return lowestIndex;
    }
    public static int indexOfHighest(double[] prices) {
        int highestIndex = 0;
        for (int i = 0; i < prices.length; i++) {
            if (prices[i] > prices[highestIndex]) {
                highestIndex = i;
            }
        }
        return highestIndex;
    }
    public static int indexOfBiggestDrop(double[] prices) {
        int index = 1;
        double drop = 0;
        for (int i = 1; i < prices.length - 1; i++) {
            drop = (prices[1] - prices[0]) / (prices[0]);
            double change = (prices[i] - prices[i - 1]) / (prices[i - 1]);
            if (change < drop) {
                change = drop;
                index = i;
            }
        }
        return index;
    }
    public static final double START_CASH = 10000.0;
    public static final double FEE = 10.0;

    public static String decide(double[] history, boolean hasShares, double boughtAt) {
        if (!hasShares) {
            if (history.length >= 3 && history[history.length - 1] < history[history.length - 2]
                && history[history.length - 2] < history[history.length - 3]) {
                return "BUY";
            }
        } else {
            if (history[history.length - 1] > boughtAt * 1.05) {
                return "SELL";
            }
        }
        return "HOLD";
    }

    public static double simulate(int[] years, int[] months, double[] prices) {
        double cash = START_CASH;
        int shares = 0;
        double boughtAt = 0.0;

        for (int i = 0; i < prices.length; i++) {
            double[] history = Arrays.copyOf(prices, i + 1);
            String action = decide(history, shares > 0, boughtAt);

            if (action.equals("BUY") && shares == 0) {
                int canBuy = (int) ((cash - FEE) / prices[i]);
                if (canBuy > 0) {
                    shares = canBuy;
                    cash = cash - shares * prices[i] - FEE;
                    boughtAt = prices[i];
                    System.out.printf("%s %d: BUY %d shares at $%.2f%n", monthName(months[i]),
                        years[i], shares, prices[i]);
                }
            } else if (action.equals("SELL") && shares > 0) {
                cash = cash + shares * prices[i] - FEE;
                System.out.printf("%s %d: SELL %d shares at $%.2f%n", monthName(months[i]),
                    years[i], shares, prices[i]);
                shares = 0;
                boughtAt = 0.0;
            }
        }
        return cash + shares * prices[prices.length - 1];
    }
    //public static double myStrategy(){
		
	//}
}
