package com.brioal.sourcecode.api.model;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brioal.sourcecode.api.contract.ApiContract;
import com.brioal.sourcecode.db.DBHelper;
import com.brioal.sourcecode.interfaces.OnHistpryLoadListener;
import com.brioal.sourcecode.interfaces.OnNormalOperatorListener;
import com.socks.library.KLog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brioal on 2017/03/03
 */

public class ApiModelImpl implements ApiContract.Model {
    private Context mContext;

    public ApiModelImpl(Context context) {
        mContext = context;
    }

    @Override
    public void transData() {
        try {
            File file = mContext.getDatabasePath("Apis.db3");
            if (file != null && file.exists()) {
                KLog.e("文件已存在");
                return;
            }
            InputStream istream = null;
            OutputStream   ostream = null;
            try {
                AssetManager am = mContext.getAssets();
                istream = am.open("Apis.db3");
                File dirPath = file.getParentFile();
                if (!dirPath.exists()) {
                    dirPath.mkdir();
                }
                if (!file.exists()) {
                    file.createNewFile();
                }
                ostream = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = istream.read(buffer)) > 0) {
                    ostream.write(buffer, 0, length);
                }

                istream.close();
                ostream.close();
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    if (istream != null)
                        istream.close();
                    if (ostream != null)
                        ostream.close();
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Override
    public void loadHistory(OnHistpryLoadListener loadListener) {
        DBHelper helper = new DBHelper(mContext, "History.db3", null, 1);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from history where 1 order by -mTime", null);
        List<String> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            String key = cursor.getString(0);
            list.add(key);
        }
        if (loadListener == null) {
            return;
        }
        if (list.size() == 0) {
            loadListener.failed("");
            return;
        }
        loadListener.success(list);
    }

    @Override
    public void saveHistory(String key, OnNormalOperatorListener listener) {
        DBHelper helper = new DBHelper(mContext, "History.db3", null, 1);
        SQLiteDatabase db = helper.getReadableDatabase();
        try {
            db.execSQL("INSERT INTO history VALUES ( ? , ? )", new Object[]{
                    key,
                    System.currentTimeMillis()
            });
            if (listener == null) {
                return;
            }
            listener.success("");
        } catch (Exception e) {
            e.printStackTrace();
            if (listener == null) {
                return;
            }
            listener.failed(e.getMessage());
        }

    }
}