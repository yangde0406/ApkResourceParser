package res.yd.com.getapkres.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import res.yd.com.getapkres.manager.ParserManager;

/**
 * Created by yangde on 15/5/21.
 */
public class ParserService extends Service {
    private static final String INTENT_RESTYPE = "INTENT_RESTYPE";
    private static final String INTENT_APK_PACKAGENAME = "INTENT_APK_PACKAGENAME";
    private String apkPackageName;
    private ParserManager.ResourceType resourceType;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            System.out.println("---------------------------字符串文件--------------------------------------");
            System.out.println("" + msg.obj.toString());
            System.out.println("---------------------------字符串文件--------------------------------------");
        }
    };

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            switch (resourceType) {
                case LAYOUT:

                    break;
                case STRINGS:
                    String strinsFile = ParserManager.getInstance(getApplicationContext()).parserStringsFile(apkPackageName);
                    Message message = handler.obtainMessage(1, strinsFile);
                    handler.sendMessage(message);
                    break;
                default:
                    break;
            }
        }
    };

    public static void Start(Context context, String apkPackageName, ParserManager.ResourceType resType) {
        Intent intent = new Intent(context, ParserService.class);
        intent.putExtra(INTENT_RESTYPE, resType);
        intent.putExtra(INTENT_APK_PACKAGENAME, apkPackageName);
        context.startService(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 获取解析的资源类型
        resourceType = (ParserManager.ResourceType) intent.getSerializableExtra(INTENT_RESTYPE);
        apkPackageName = intent.getStringExtra(INTENT_APK_PACKAGENAME);
//        handler.post(runnable);
        new Thread(runnable).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
