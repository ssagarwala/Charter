import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

/************************************************************
 * Class Rewards
 * Calculates and prints the rewards customer gets per
 * month, and also prints the total rewards for three
 * months.(e.g. a $120 purchase = 2x$20 + 1x$50 = 90 points).
 * A customer receives 2 points for every dollar spent over
 * $100 in each transaction, plus 1 point for every dollar spent
 * over $50 in each transaction and only for the portion of the
 * amount that is multiple of 50.
 * @author Sehar Sagarwala
 ***********************************************************/
public class Rewards {
    public static void main (String [] args) {
        List<String> lines = readFile("C:/Charter/src/TestData.txt");
        calcualteRewardsPerMonth(lines);
    }

    /**************************************************************
     * calculateRewards() Takes in a list of lines of a file
     * calculates the rewards for each customer
     * @param lines
     * @return HashMap<String,Integer>
     **************************************************************/
    public static void calcualteRewardsPerMonth (List<String> lines){
        Map<String, Integer> month1 = new HashMap<String, Integer>();
        Map<String, Integer> month2 = new HashMap<String, Integer>();
        Map<String, Integer> month3 = new HashMap<String, Integer>();
        Map<String, Integer> currentMap = new HashMap<String, Integer>();
        Set<String> customersList = new HashSet<>();

        int totalRewards;
        int singleRewards;
        int doubleRewards;

        List<Map> monthsList = Arrays.asList(month1, month2, month3);
        int monthsListCounter = 0;

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            StringTokenizer lineTokens = new StringTokenizer(line, " ");
            if (lineTokens.countTokens() == 1) {
                currentMap = monthsList.get(monthsListCounter);
                //System.out.println("current Map is "+monthsListCounter);
                monthsListCounter++;
            } else {
                String customer = lineTokens.nextToken();
                Integer transaction = Integer.parseInt(lineTokens.nextToken());
                Integer rewardPoints = currentMap.containsKey(customer)? currentMap.get(customer) :0 ;
                    if (transaction >= 100) {
                        totalRewards =0;
                        singleRewards=0;
                        doubleRewards=0;
                        doubleRewards = (transaction - 100) * 2;
                        singleRewards = ((transaction - 50) / 50) * 50;
                        totalRewards = singleRewards + doubleRewards;
                        totalRewards += rewardPoints;
                        currentMap.put(customer, totalRewards);
                    }

            }
        }
        System.out.println("------Month 1 Reward Points---------");
        printRewardsPerMonth(month1);
        System.out.println("------Month 2 Reward Points---------");
        printRewardsPerMonth(month2);
        System.out.println("------Month 3 Reward Points---------");
        printRewardsPerMonth(month3);

        customersList = month1.keySet();
        customersList = month2.keySet();
        customersList = month3.keySet();
        printTotalRewardsForEachCustomer(customersList, month1, month2, month3);

    }

    /*******************************************************************
     * Print Total Rewards For Each WCustomer for three months
     * @param customersList of names
     * @param month1 map of customer and rewards
     * @param month2 map of customer and rewards
     * @param month3 map of customer and rewards
     *******************************************************************/
    public static void  printTotalRewardsForEachCustomer(Set <String> customersList,
                                                         Map<String,Integer> month1,
                                                         Map<String,Integer>month2,
                                                         Map<String, Integer> month3){

      Map<String, Integer> customersTotalPoints = new HashMap();
      Iterator<String> iterator  = customersList.iterator();
      while(iterator.hasNext()){
          Integer totalRewards = 0;
          String customer = iterator.next();
          totalRewards += month1.get(customer);
          totalRewards += month2.get(customer);
          totalRewards += month3.get(customer);
          customersTotalPoints.put(customer, totalRewards);
      }
      System.out.println(customersTotalPoints);
    }
    /*******************************************************************
     * printRewardsPerMonth
     * @param customerRewardsMap  map of customer and rewards
     *******************************************************************/

    public static void printRewardsPerMonth(Map <String,Integer> customerRewardsMap){

        Set set = customerRewardsMap.keySet();
        Iterator<String> iterator = set.iterator();
        while(iterator.hasNext()){
            String customer = iterator.next();
            Integer rewards = customerRewardsMap.get(customer);
            System.out.println(customer + " :" + rewards + "points");
        }
    }

    /**
     * Open and read a file, and return the lines in the file as a list
     * of Strings.
     * @param filename
     * @return a list of lines of file
     */

    private static List<String> readFile(String filename)
    {
        List<String> records = new ArrayList<String>();
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null)
            {
                records.add(line);
            }
            reader.close();
            return records;
        }
        catch (Exception e)
        {
            System.err.format("Exception occurred trying to read '%s'.", filename);
            e.printStackTrace();
            return null;
        }
    }
}
