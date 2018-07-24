package com.grechur.processkeepalive.doubleservice;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.grechur.processkeepalive.ProcessAidl;

import java.util.LinkedList;

public class ProcessService extends Service {
    private final static int MESSAGEID = 1;
    public ProcessService() {
    }


    @Override
    public void onCreate() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Log.e("TAG", "接收消息");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //提高优先级
        startForeground(MESSAGEID,new Notification());
        bindService(new Intent(this,GuardService.class),mMessegeServiceConnection, Context.BIND_IMPORTANT);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return new ProcessAidl.Stub() {};
    }

    private ServiceConnection mMessegeServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Toast.makeText(ProcessService.this,"建立连接",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            startService(new Intent(ProcessService.this,GuardService.class));
            bindService(new Intent(ProcessService.this,GuardService.class),mMessegeServiceConnection, Context.BIND_IMPORTANT);
        }
    };
}
