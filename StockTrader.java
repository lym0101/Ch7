
 //PART 2: LOWEST AND HIGHEST PRICES
 // File                  Lowest     When        Highest    When
 // tiny.txt               $30.00     Mar 2020    $70.00     Jul 2020
 // rising.txt             $20.00     Jan 2010    $48.14     Dec 2014
 // falling.txt            $30.36     Dec 2014    $100.00    Jan 2010
 // zigzag.txt             $35.00     Jul 2010    $65.00     Mar 2010
 // aapl_2000_2010.txt     $7.07      Mar 2003    $223.02    Mar 2010
 // amzn_2000_2010.txt     $5.97      Sep 2001    $135.91    Nov 2009
 // msft_2000_2010.txt     $15.81     Feb 2009    $43.22     Mar 2000
 // ibm_2000_2010.txt      $53.01     Sep 2002    $130.32    Dec 2009
 // goog_2004_2010.txt     $102.37    Aug 2004    $707.00    Oct 2007
 // sbux_2007_2016.txt     $4.09      Nov 2008    $62.16     Oct 2015
 // sp500_1925_1945.txt    $4.77      Jun 1932    $31.30     Sep 1929
 // sp500_1990_2025.txt    $307.12    Oct 1990    $6853.03   Dec 2025
 //
 // Q1. MSFT's highest price comes before its lowest. What does that mean for
 //     someone who bought Microsoft in 2000?
 //     ANSWER: The highest price was $43.22 in Mar 2000 and the lowest was $15.81 in Feb 2009. Think about what happened to someone who bought near the top in 2000, and what buy-and-hold ended with on this file ($7,237.50 from $10,000).
 //
 // Q2. What happened in the world around the lowest month for sbux_2007_2016
 //     and sp500_1925_1945? Does the timing make sense?
 //     ANSWER: sbux's lowest month was Nov 2008 and sp500_1925_1945's was Jun 1932. Look up what was happening in the world at those dates. Also, their biggest one-month drops (Nov 2008 and Nov 1929) were in the same crises.
 //
 // Challenge: worst single month (biggest % drop):
 //   tiny.txt               -25.0%   Mar 2020
 //   rising.txt             +1.5%    Apr 2011   (never actually drops)
 //   falling.txt            -2.0%    May 2014
 //   zigzag.txt             -21.2%   Jun 2010
 //   aapl_2000_2010.txt     -57.7%   Sep 2000
 //   amzn_2000_2010.txt     -41.1%   Feb 2001
 //   msft_2000_2010.txt     -34.4%   Apr 2000
 //   ibm_2000_2010.txt      -22.6%   Sep 2002
 //   goog_2004_2010.txt     -18.5%   Nov 2008
 //   sbux_2007_2016.txt     -32.1%   Nov 2008
 //   sp500_1925_1945.txt    -26.5%   Nov 1929
 //   sp500_1990_2025.txt    -20.4%   Oct 2008
 //
 //STEP E: COMPARING STRATEGIES
 // File                   Buy & hold   Dip buyer    Moving avg   My strategy
 // tiny.txt               $12,975.00   $14,975.00   $10,000.00   $10,000.00
 // rising.txt             $24,031.86   $10,000.00   $20,411.92   $21,007.72
 // falling.txt            $3,095.64    $3,159.28    $10,000.00   $10,000.00
 // zigzag.txt             $12,101.39   $38,071.99   $9,848.78    $2,326.29
 // aapl_2000_2010.txt     $85,865.80   $36,610.49   $114,231.31  $95,997.06
 // amzn_2000_2010.txt     $19,886.04   $14,192.52   $38,170.87   $40,581.44
 // msft_2000_2010.txt     $7,237.50    $49,273.15   $7,208.23    $8,827.69
 // ibm_2000_2010.txt      $12,467.97   $27,998.86   $7,549.75    $7,096.18
 // goog_2004_2010.txt     $54,398.54   $13,655.41   $18,706.20   $20,481.03
 // sbux_2007_2016.txt     $36,280.60   $15,759.50   $67,500.23   $62,487.15
 //
 // Q1. Which strategy did best on the most files? Was any best on every file?
 //     ANSWER: The dip buyer did best on the most files: 4 (tiny, zigzag, msft, ibm). The moving average won 2 (aapl, sbux), buy-and-hold won 2 (rising, goog), and my strategy won 1 (amzn). On falling, the moving average and my strategy tied at $10,000. No strategy was best on every file.
 //
 // Q2. Pick a file where the dip buyer did badly. Use the trades to explain why.
 //     ANSWER: goog is a good pick ($13,655 vs $54,398 for buy-and-hold). It made three winning trades in 2005–2006 (gains of 11–26%), then bought 24 shares at $691.48 in Dec 2007, right near the top, and never got back up 10% to sell. On rising.txt it never bought at all, because the price never dropped two months in a row.
 //
 // Q3. Pick a file where the moving average beat buy and hold. What happened?
 //     ANSWER: sbux is a good pick ($67,500 vs $36,281). It sat out the 2008 crash and only bought in Apr 2009 at $6.62, near the bottom. Buy-and-hold bought at the start and rode the whole drop down to $4.09.
 //
 // Q4. How did your strategy do against buy and hold? If you tuned it for one
 //     file, did it still work on the others?
 //     ANSWER: It beat buy-and-hold on 5 files (falling, aapl, amzn, msft, sbux) and lost on 5 (tiny, rising, zigzag, ibm, goog). zigzag is its worst result, ending at $2,326. On a choppy stock it buys near the top of each swing and sells near the bottom: it bought at $60.61 and sold at $39.39, then five more times bought at $50.00 and sold at $39.39, losing about 21% on each of those trips. The $10 fees are small next to those losses.

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class StockTrader {

    public static void main(String[] args) throws FileNotFoundException {
        String fileName = "tiny.txt";   // change this to test other files
        Scanner in = new Scanner(new File(fileName));
        String symbol = in.next();
        int n = in.nextInt();

        int[] years = new int[n];
        int[] months = new int[n];
        double[] prices = new double[n];

        // Part 1
        loadData(in, years, months, prices);
        printData(years, months, prices);

        // Part 2
        int lowest = indexOfLowest(prices);
        int highest = indexOfHighest(prices);
        System.out.printf("%s lowest:  $%.2f in %s %d%n",
                symbol, prices[lowest], monthName(months[lowest]), years[lowest]);
        System.out.printf("%s highest: $%.2f in %s %d%n",
                symbol, prices[highest], monthName(months[highest]), years[highest]);

        // Part 2 challenge (optional)
        int drop = indexOfBiggestDrop(prices);
        double pct = (prices[drop] - prices[drop - 1]) / prices[drop - 1] * 100;
        System.out.printf("%s biggest drop: %.1f%% in %s %d%n",
                symbol, pct, monthName(months[drop]), years[drop]);

        // Part 3
        double result = simulate(years, months, prices);
        System.out.printf("Final value: $%,.2f%n", result);
    }

    //PART 1

    public static void loadData(Scanner in, int[] years, int[] months, double[] prices) {
        for (int i = 0; i < prices.length; i++) {
            years[i] = in.nextInt();
            months[i] = in.nextInt();
            prices[i] = in.nextDouble();
        }
    }

    public static String monthName(int month) {
        String[] names = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
                          "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        return names[month - 1];
    }

    public static void printData(int[] years, int[] months, double[] prices) {
        for (int i = 0; i < prices.length; i++) {
            System.out.printf("%s %d: $%.2f%n", monthName(months[i]), years[i], prices[i]);
        }
    }

    // PART 2

    // Uses < (not <=) so a tie keeps the earliest index.
    public static int indexOfLowest(double[] prices) {
        int lowestIndex = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] < prices[lowestIndex]) {
                lowestIndex = i;
            }
        }
        return lowestIndex;
    }

    // Uses > (not >=) so a tie keeps the earliest index.
    public static int indexOfHighest(double[] prices) {
        int highestIndex = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[highestIndex]) {
                highestIndex = i;
            }
        }
        return highestIndex;
    }

    // Challenge: index of the month with the largest % drop from the month before.
    // Starts at 1 because month 0 has no previous month. Needs at least 2 prices.
    public static int indexOfBiggestDrop(double[] prices) {
        int worstIndex = 1;
        double worstChange = (prices[1] - prices[0]) / prices[0];
        for (int i = 2; i < prices.length; i++) {
            double change = (prices[i] - prices[i - 1]) / prices[i - 1];
            if (change < worstChange) {
                worstChange = change;
                worstIndex = i;
            }
        }
        return worstIndex;
    }

    // PART 3: SIMULATOR

    public static final double START_CASH = 10000.0;
    public static final double FEE = 10.0;

    public static String decide(double[] history, boolean holding, double boughtAt) {
        return buyAndHold(history, holding, boughtAt);   // change this line to switch strategies
        // return dipBuyer(history, holding, boughtAt);
        // return movingAverage(history, holding, boughtAt);
        // return myStrategy(history, holding, boughtAt);
    }

    // Step A: buy in the first month and never sell.
    public static String buyAndHold(double[] history, boolean holding, double boughtAt) {
        return "BUY";
    }

    // Step B: buy after two drops in a row; sell once up at least 10%.
    public static String dipBuyer(double[] history, boolean holding, double boughtAt) {
        int today = history.length - 1;
        if (!holding) {
            if (history.length >= 3
                    && history[today] < history[today - 1]
                    && history[today - 1] < history[today - 2]) {
                return "BUY";
            }
        } else {
            if (history[today] >= boughtAt * 1.10) {
                return "SELL";
            }
        }
        return "HOLD";
    }

    // Step C: buy above the 12-month average, sell below it.
    public static String movingAverage(double[] history, boolean holding, double boughtAt) {
        if (history.length < 12) {
            return "HOLD";
        }
        double today = history[history.length - 1];
        double avg = average(history, 12);

        if (!holding && today > avg) {
            return "BUY";
        }
        if (holding && today < avg) {
            return "SELL";
        }
        return "HOLD";
    }

    // Helper: average of the last `count` prices in history (including today).
    public static double average(double[] history, int count) {
        double sum = 0;
        for (int i = history.length - count; i < history.length; i++) {
            sum += history[i];
        }
        return sum / count;
    }

    //
    // Step D: My strategy ("trend with a cushion and a safety net")
    //
    // Rules:
    //   - Wait until there are 10 months of prices.
    //   - If I don't own shares, BUY when today's price is above the 10-month average.
    //   - If I own shares, SELL when today's price is more than 3% below the
    //     10-month average, OR when it has fallen 20% below what I paid.
    //   - Otherwise, HOLD.
    //
    // Why I think it works:
    //   - Like the moving average, it rides long uptrends and gets out of long
    //     crashes (2000, 2008).
    //   - A 10-month average reacts a bit faster than a 12-month one.
    //   - The 3% cushion stops it from selling every time the price dips just
    //     under the average, which saves $10 fees on choppy stocks.
    //   - The 20% stop-loss caps the damage if a crash happens right after buying.
    //
    public static String myStrategy(double[] history, boolean holding, double boughtAt) {
        if (history.length < 10) {
            return "HOLD";
        }
        double today = history[history.length - 1];
        double avg = average(history, 10);

        if (!holding && today > avg) {
            return "BUY";
        }
        if (holding && (today < avg * 0.97 || today < boughtAt * 0.80)) {
            return "SELL";
        }
        return "HOLD";
    }

    // Simulator — do not change.
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
                    System.out.printf("%s %d: BUY %d shares at $%.2f%n",
                            monthName(months[i]), years[i], shares, prices[i]);
                }
            } else if (action.equals("SELL") && shares > 0) {
                cash = cash + shares * prices[i] - FEE;
                System.out.printf("%s %d: SELL %d shares at $%.2f%n",
                        monthName(months[i]), years[i], shares, prices[i]);
                shares = 0;
                boughtAt = 0.0;
            }
        }
        return cash + shares * prices[prices.length - 1];
    }
}
