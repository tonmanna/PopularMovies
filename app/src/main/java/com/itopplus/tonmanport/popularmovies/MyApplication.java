package com.itopplus.tonmanport.popularmovies;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

import java.io.File;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        deleteCache(this);
//        Stetho.initialize(
//                Stetho.newInitializerBuilder(this)
//                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
//                        .build());
    }

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
            }
        } catch (Exception e) {}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }
}