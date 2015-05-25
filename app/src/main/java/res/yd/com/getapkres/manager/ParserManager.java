package res.yd.com.getapkres.manager;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import java.lang.reflect.Field;
import java.util.List;

import dalvik.system.PathClassLoader;
import res.yd.com.getapkres.R;

/**
 * Created by yangde on 15/5/21.
 */
public class ParserManager {
    private static ParserManager instance;

    /**
     * 解析的种类
     */
    public enum ResourceType {
        LAYOUT,
        STRINGS,//
        ANIM
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
            Class<?> forClass = Class.forName(packageName + ".R$layout", true, classLoader);
            Field[] declaredFields = forClass.getDeclaredFields();


            StringBuffer sb = new StringBuffer();
            sb.append("");
            for (Field field : declaredFields) {

            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String parserStringsFile(String packageName) {
        Context runningApkContext = getRunningApkContext(packageName);
        PathClassLoader classLoader = new PathClassLoader(runningApkContext.getPackageResourcePath(), context.getClassLoader());
        try {
            Class<?> forClass = Class.forName(packageName + ".R$string", true, classLoader);
            Field[] declaredFields = forClass.getDeclaredFields();

            StringBuffer sb = new StringBuffer();
            sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
            sb.append("<resources>\n");
            try {
                for (Field field : declaredFields) {
                    String name = field.getName();
                    int resId = field.getInt(R.string.class);
                    String value = runningApkContext.getString(resId);

                    // <string name="app_name">GetApkRes</string>
                    sb.append("\t<string name=\"" + name + "\">" + value + "</string>\n");
                }
                sb.append("</resources>");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return sb.toString();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Context getRunningApkContext(String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> runningTask = activityManager.getRunningTasks(5);
            for (ActivityManager.RunningTaskInfo runningTaskInfo : runningTask) {
                ComponentName componentName = runningTaskInfo.topActivity;
                packageName = componentName.getPackageName();
            }
        }

        try {
            Context runningApkContext = context.createPackageContext(packageName, Context.CONTEXT_IGNORE_SECURITY);
            return runningApkContext;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
