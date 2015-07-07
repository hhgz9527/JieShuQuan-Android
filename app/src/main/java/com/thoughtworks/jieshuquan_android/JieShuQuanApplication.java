package com.thoughtworks.jieshuquan_android;

import android.app.Application;
import android.util.Log;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.thoughtworks.jieshuquan_android.service.model.Book;
import com.thoughtworks.jieshuquan_android.service.model.BookEntity;
import com.thoughtworks.jieshuquan_android.service.model.BorrowRecord;
import com.thoughtworks.jieshuquan_android.service.model.Discover;

/**
 * Created by leihuang on 5/27/15.
 */
public class JieShuQuanApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AVObject.registerSubclass(Book.class);
        AVObject.registerSubclass(BookEntity.class);
        AVObject.registerSubclass(Discover.class);
        AVObject.registerSubclass(BorrowRecord.class);
        // init AVOS
        AVOSCloud.initialize(this, "gdakm21nfqik6swplef3anis5fu078gex0zb36uzoz0vippb", "5ptvr2vqoyzqxka9yjsahnq0mo28traj6w7d1v5t8pj5can6");

        // open crash report
        AVAnalytics.enableCrashReport(this.getApplicationContext(), true);
        AVOSCloud.setLastModifyEnabled(true);
        AVOSCloud.setDebugLogEnabled(true);

        // init AVOS push
        String installationId = AVInstallation.getCurrentInstallation().getInstallationId();
        Log.v("Application", "getCurrentInstallation  " + installationId);
        AVInstallation.getCurrentInstallation().saveInBackground();

    }


}
