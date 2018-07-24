package com.grechur.processkeepalive;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.grechur.processkeepalive.doubleservice.ProcessService;

import java.util.List;

/**
 * Created by zz on 2018/7/24.
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class JobWakeService extends JobService{
    private final static int JOBWAKEID = 1;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        JobInfo.Builder jobInfoBuilder = new JobInfo.Builder(JOBWAKEID,new ComponentName(this,JobWakeService.class));
        jobInfoBuilder.setPeriodic(2000);
        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(jobInfoBuilder.build());
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        boolean flag = isServiceExisted(this, ProcessService.class.getName());
        if(!flag){
            startService(new Intent(this,ProcessService.class));
        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    /**
     * 判断某个服务是否正在运行的方法
     *
     * @param context
     * @param className
     *            是包名+服务的类名（例如：net.loonggg.testbackstage.TestService）
     * @return true代表正在运行，false代表服务没有正在运行
     */
    public static boolean isServiceExisted(Context context, String className) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager.getRunningServices(Integer.MAX_VALUE);
        if(!(serviceList.size() > 0)) {
            return false;
        }
        for(int i = 0; i < serviceList.size(); i++) {
            ActivityManager.RunningServiceInfo serviceInfo = serviceList.get(i);
            ComponentName serviceName = serviceInfo.service;
            if(serviceName.getClassName().equals(className)) {
                return true;
            }
        }
        return false;
    }
}
