package res.yd.com.getapkres.manager;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;

import java.lang.reflect.Field;
import java.util.List;

import dalvik.system.PathClassLoader;

/**
 * Created by yangde on 15/5/21.
 */
public class ParserManager {
    private static ParserManager instance;

    /**
     * 解析的种类
     */
    public enum ResourceType {
        LAYOUT(1, "layout", "布局文件"),
        STRINGS(2, "strings", "字符串"),//
        ANIM(3, "anim", "动画文件");


        private int code;
        private String value;
        private String description;

        ResourceType(int code, String value, String description) {
            this.code = code;
            this.value = value;
            this.description = description;
        }

        public int getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }

        public String getDescription() {
            return description;
        }
    }

    private Context context;

    private ParserManager(Context context) {
        this.context = context;
    }

    public static ParserManager getInstance(Context context) {
        if (instance == null) {
            instance = new ParserManager(context);
        }
        return instance;
    }

    public void parserLayoutFile(String packageName) {
        Context runningApkContext = getRunningApkContext(packageName);
        PathClassLoader classLoader = new PathClassLoader(runningApkContext.getPackageResourcePath(), context.getClassLoader());
        try {
            Class<?> forClass = Class.forName(packageName + ".R$" + ResourceType.LAYOUT.getValue(), true, classLoader);
            Field[] declaredFields = forClass.getDeclaredFields();


            StringBuffer sb = new StringBuffer();
            sb.append("");
            for (Field field : declaredFields) {

            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Context getRunningApkContext(String packageName) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTask = activityManager.getRunningTasks(5);
        String runApkPackageName;
        for (ActivityManager.RunningTaskInfo runningTaskInfo : runningTask) {
            ComponentName componentName = runningTaskInfo.topActivity;
            runApkPackageName = componentName.getPackageName();
            if (runApkPackageName.equals(packageName)) {
                break;
            }
        }

        try {
            Context runningApkContext = context.createPackageContext(packageName, Context.CONTEXT_INCLUDE_CODE | Context.CONTEXT_IGNORE_SECURITY);
            return runningApkContext;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
