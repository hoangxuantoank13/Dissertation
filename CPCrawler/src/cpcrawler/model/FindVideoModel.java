/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpcrawler.model;

import cpcrawler.ulti.CPConfig;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author toanhx
 */
public class FindVideoModel {

    public static final FindVideoModel Instance = new FindVideoModel();
    public static final int interval = CPConfig.Instance.getInt("FindVideoModel.interval",30);

    public FindVideoModel() {
        // scheduler change key
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                FindVideoModel.Instance.findVideo();

            }
        }, 0, interval, TimeUnit.MINUTES);
    }

    public void findVideo() {

    }
}
