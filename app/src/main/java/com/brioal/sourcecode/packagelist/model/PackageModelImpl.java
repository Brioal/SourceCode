package com.brioal.sourcecode.packagelist.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brioal.index.IndexBean;
import com.brioal.sourcecode.bean.ApiBean;
import com.brioal.sourcecode.db.DBHelper;
import com.brioal.sourcecode.interfaces.OnApiBeanLoadListener;
import com.brioal.sourcecode.packagelist.contract.PackageContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brioal on 2017/03/05
 */

public class PackageModelImpl implements PackageContract.Model {
    private Context mContext;

    public PackageModelImpl(Context context) {
        mContext = context;
    }

    @Override
    public void loadPackages(OnApiBeanLoadListener listener) {
        //加载Package列表
        DBHelper helper = new DBHelper(mContext, "Apis.db3", null, 1);
        SQLiteDatabase db = helper.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM Indexs where mType = 1", null);
            List<IndexBean> list = new ArrayList<>();
            while (cursor.moveToNext()) {
                String name = cursor.getString(1);
                String url = cursor.getString(0);
                int type = cursor.getInt(2);
                IndexBean bean = new ApiBean(name, url, type);
                list.add(bean);
            }
            if (listener == null) {
                return;
            }
            if (list.size() == 0) {
                listener.failed("找不到数据");
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
}