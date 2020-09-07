import java.util.Date;
import java.util.concurrent.*;
class Report{
    static int result = 0;
    public static synchronized void doSync(int count){
        try{
            /* add required logic to update result for every worker */
        }
        catch(Exception e){}
        
    }
}
class RPrime implements Runnable{
    int wid;
    int alpha;
    int beta;
    public RPrime(int id, int alpha, int beta){
        this.wid = id;
        this.alpha = alpha;
        this.beta = beta;
    }
    public int compute(int start, int end){
        int num_of_primes = 0;
        for (int i = start; i <= end; i++) { 
            if (i == 1 || i == 0) 
                continue; 
            boolean is_prime = true; 
            for (int j = 2; j <= i / 2; j++){ 
                if (i % j == 0) { 
                    is_prime = false; // not a prime 
                    break; 
                } 
            } 
            if (is_prime == true){
                //System.out.println(i);
                num_of_primes++; // found a prime
            }
        } 
        return num_of_primes;
    }
    public void run(){
        try{
            int excess = alpha%beta;
            int start = wid*(alpha/beta);
            int end = start + alpha/beta - 1;
            // distribute excess to workers
            if (wid >= (beta-excess)){
                /* add the necessary logic here to 
                    update start and end */
            }
            /* add required logic to integrate the right code
            to get the myshare populate correctly */
            int myshare = 0;
            System.out.println("Range by wid=" + wid 
                                + " from " + start +  " to " 
                                + end + " is: " + myshare);
            Report.doSync(myshare); 
        }catch(Exception err){
            err.printStackTrace();
        }
    }
}
class Prime{
    public static void doWork(int alpha, int beta){
        try{
            ExecutorService threadPool = Executors.newFixedThreadPool(beta);
            for (int wid =0; wid < beta; wid++) {
               RPrime rp = new RPrime(wid, alpha, beta);
               threadPool.submit(rp);
            }
            threadPool.shutdown();  
            while(true) {
                if(threadPool.isTerminated()) {
                    System.out.println("Total no of primes:" 
                        + Report.result);
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
        int alpha = 200; // try with different nos here. 
        int beta = 5;
        doWork(alpha, beta);
        long end_time = System.nanoTime();
        long total_time = end_time - start_time;
        System.out.println(total_time);
        
    }   
}