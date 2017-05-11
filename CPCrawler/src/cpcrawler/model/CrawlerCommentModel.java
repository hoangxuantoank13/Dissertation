/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpcrawler.model;

import cpcrawler.ulti.CPConfig;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author toanhx
 */
public class CrawlerCommentModel {

    public static final CrawlerCommentModel Instance = new CrawlerCommentModel();
    private static int numOfThread ;
    private static int interval ;
    private static int numOfTask ;
    private static ExecutorService executorService ;

    public CrawlerCommentModel() {
        numOfThread = CPConfig.Instance.getInt("CrawlerCommentModel.numOfThread",10);
        interval = CPConfig.Instance.getInt("CrawlerCommentModel.interval",5);
        numOfTask = CPConfig.Instance.getInt("CrawlerCommentModel.numOfTask",20);
         // creates thread pool with 2 thread
        executorService  = Executors.newFixedThreadPool(numOfThread);

        
    }

    public void run() {
         // scheduler change key
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Random r = new Random();
                int total = r.nextInt(1000);
                System.out.println("Total Videl: " + total);
                int videoPerTask = total / numOfTask;
                for(int i = 0; i < numOfTask; i++) {
                    int start = i * videoPerTask;
                    int end = i * videoPerTask + videoPerTask - 1;
                    if(i == numOfTask - 1) {
                        end = total;
                    }
                    Runnable task = new CrawlerCommentThread(start, end);
                    executorService.execute(task);
                }

            }
        }, 0, interval, TimeUnit.SECONDS);

        
    }
    
     // Runnable thread
    class CrawlerCommentThread implements Runnable {
        private int start;
        private int end;
        public CrawlerCommentThread() {}
        public CrawlerCommentThread(int start, int end) {
            this.start = start;
            this.end = end;
        }
        
        @Override
        public void run() {
            
            crawlerComment(start, end);
        }
    }
    
    
    public void crawlerComment(int start, int end) {
        System.out.println("Range Video: " + start + " - " + end);
    }
    
    
    public static void main(String[] args) {
        CrawlerCommentModel.Instance.run();
    }
}
