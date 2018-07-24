package com.grechur.processkeepalive.doubleservice;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.widget.Toast;

import com.grechur.processkeepalive.ProcessAidl;

public class GuardService extends Service {
    private final static int GUARDID = 1;
    public GuardService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //提高优先级
        startForeground(GUARDID,new Notification());
        bindService(new Intent(this,ProcessService.class),mServiceConnection, Context.BIND_IMPORTANT);
        return START_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return new ProcessAidl.Stub() {};
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Toast.makeText(GuardService.this,"建立连接",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            startService(new Intent(GuardService.this,ProcessService.class));
            bindService(new Intent(GuardService.this,ProcessService.class),mServiceConnection, Context.BIND_IMPORTANT);
        }
    };
}
