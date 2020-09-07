import java.util.Arrays;
import java.util.concurrent.*;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
class RTweets implements Runnable{
    /* add the required code here to setup the RTweets class
    where we had implemented the Runnable class */
    int wid;
    int tweets_per_day;
    int[] tweets_populated;
    public RTweets(int id, int tweets_per_day, int[] tweets_populated){
        this.wid = id;
        this.tweets_per_day = tweets_per_day;
        this.tweets_populated = tweets_populated;
    }
    public void run(){
        System.out.println("#thread initiated");
        int negative_count = 0, neutral_count = 0, positive_count = 0;
        /* add the required logic here to iterate 
        through the number of tweets_per_day for the corresponding 
        worker, and update the final verdict for the day 
        in the tweets_processed array. */
    }
}
public class Tweets{
    public static int[] tweets_processed;
    public static int[] loadData(int[] tweets){
        /*
        Data is prepared by inserting random values 
        0, 1, and 2. Data items may be repeated. 
            0 = neutral
            1 = positive
            2 = negative
        */
        Random rand = new Random(); 
        for (int i = 0; i < tweets.length; i++){
                tweets[i] = rand.nextInt(3);
        }
        return tweets;
    }
    public static void startProcess(int[] tweets_populated, int num_of_threads) {
        int tweets_per_day = tweets_populated.length/num_of_threads;
        try{
            tweets_processed = new int[num_of_threads]; 
            ExecutorService threadPool = Executors.newFixedThreadPool(num_of_threads);
            for (int wid = 0; wid < num_of_threads; wid++) {
               RTweets rt = new RTweets(wid, tweets_per_day, tweets_populated); /* make the required changes here */
               threadPool.submit(rt);
            }
            threadPool.shutdown();  
            while(true) {
                if(threadPool.isTerminated()) {
                    for (int k = 0; k < tweets_processed.length; k++){
                        if (tweets_processed[k] == 0)
                            System.out.println("\tTweets for day " + k + " is neutral");
                        else if (tweets_processed[k] == 1)
                            System.out.println("\tTweets for day " + k + " is positive");
                        else
                            System.out.println("\tTweets for day " + k + " is negative");       
                    }                   
                    break;
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex.toString());
        }
    }
    public static void main(String[] args){
        long start_time = System.nanoTime();
        int tweets_per_day = 10;
        int no_of_days = 5; // number of days is number of threads.
        int[] tweets_initialized = new int[no_of_days*tweets_per_day]; 
        /* a set of sentiment scores for tweets using   
            a randomized distribution of scorings. */
   
        int[] tweets_populated = loadData(tweets_initialized);
        //System.out.println("Tweet Sentiment Score Recordings:\t" + Arrays.toString(tweets_populated));   
        System.out.println("#loading completed...");
        

        /* The lines below will print the output. 
        Do not uncomment these lines.  */
        
        System.out.println("#processing started...");
        startProcess(tweets_populated, no_of_days);
        System.out.println("#processing completed...");
        
        long end_time = System.nanoTime();
        long total_time = end_time - start_time;
        System.out.println("Total time taken:" + total_time);
        
    }
}