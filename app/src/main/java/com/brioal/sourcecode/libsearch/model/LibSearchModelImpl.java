package com.brioal.sourcecode.libsearch.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brioal.sourcecode.bean.LibBean;
import com.brioal.sourcecode.db.DBHelper;
import com.brioal.sourcecode.interfaces.OnHistpryLoadListener;
import com.brioal.sourcecode.interfaces.OnLibLoadListener;
import com.brioal.sourcecode.libsearch.contract.LibSearchContract;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Brioal on 2017/03/05
 */

public class LibSearchModelImpl implements LibSearchContract.Model {
    private Context mContext;

    public LibSearchModelImpl(Context context) {
        mContext = context;
    }


    @Override
    public void loadHistory(OnHistpryLoadListener listener) {
        //加载Lib的搜索记录
        try {
            DBHelper dbHelper = new DBHelper(mContext, "Apis.db3", null, 1);
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM libhistory ORDER BY -mTime", null);
            List<String> list = new ArrayList<>();
            while (cursor.moveToNext()) {
                String name = cursor.getString(0);
                list.add(name);
            }
            if (listener == null) {
                return;
            }
            if (list.size() == 0) {
                listener.failed("找不到历史记录");
                return;
            }
            listener.success(list);
        } catch (Exception e) {
            e.printStackTrace();
            if (listener == null) {
                return;
            }
            listener.failed(e.getMessage());
        }

    }

    @Override
    public void loadResult(String key, final OnLibLoadListener listener) {
        //加载搜索结果
        if (key == null) {
            return;
        }
        if (key.isEmpty()) {
            return;
        }
        if (listener == null) {
            return;
        }
        BmobQuery<LibBean> query = new BmobQuery<>();
        query.addWhereEqualTo("mLabel", key);
        query.include("mUserBean");
        query.order("-createdAt");
        query.findObjects(new FindListener<LibBean>() {
            @Override
            public void done(List<LibBean> list, BmobException e) {
                if (e != null) {
                    listener.failed(e.getMessage());
                    return;
                }
                if (list.size() == 0) {
                    listener.failed("找不到数据");
                    return;
                }
                listener.success(list);
            }
        });
    }

    @Override
    public void addHistory(String key) {
        //加载Lib的搜索记录
        try {
            DBHelper dbHelper = new DBHelper(mContext, "Apis.db3", null, 1);
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            db.execSQL("INSERT INTO  libhistory VALUES ( ? , ? )", new Object[]{
                    key,
                    System.currentTimeMillis()
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}