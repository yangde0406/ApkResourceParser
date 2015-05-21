package res.yd.com.getapkres.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import res.yd.com.getapkres.manager.ParserManager;

/**
 * Created by yangde on 15/5/21.
 */
public class ParserService extends Service {
    private static final String INTENT_RESTYPE = "INTENT_RESTYPE";
    private ParserManager.ResourceType resourceType;
    private Handler handler = new Handler() {

    };

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            switch (resourceType) {
                case LAYOUT:
                    break;
                case STRING:
                    break;
                default:
                    break;
            }
        }
    };

    public static void Start(Context context, ParserManager.ResourceType resType) {
        Intent intent = new Intent(context, ParserService.class);
        intent.putExtra(INTENT_RESTYPE, resType);
        context.startService(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 获取解析的资源类型
        resourceType = (ParserManager.ResourceType) intent.getSerializableExtra(INTENT_RESTYPE);
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
