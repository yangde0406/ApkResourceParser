package res.yd.com.getapkres.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

/**
 * Created by yangde on 15/5/21.
 */
public class ParserService extends Service {
    private static final String INTENT_RESTYPE = "INTENT_RESTYPE";
    private ResourceType resourceType;
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

    /**
     * 解析的种类
     */
    public enum ResourceType {
        LAYOUT,// 布局文件
        STRING,// 字符串
        VALUES
    }

    public static void Start(Context context, ResourceType resType) {
        Intent intent = new Intent(context, ParserService.class);
        intent.putExtra(INTENT_RESTYPE, resType);
        context.startService(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 获取解析的资源类型
        resourceType = (ResourceType) intent.getSerializableExtra(INTENT_RESTYPE);
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
