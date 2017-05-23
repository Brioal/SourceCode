package com.brioal.sourcecode.apiresult.model;

import android.content.Context;
import android.database.Cursor;

import com.brioal.index.IndexBean;
import com.brioal.sourcecode.apiresult.contract.ApiResultContract;
import com.brioal.sourcecode.bean.ApiBean;
import com.brioal.sourcecode.db.DBHelper;
import com.brioal.sourcecode.interfaces.OnApiBeanLoadListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brioal on 2017/03/04
 */

public class ApiResultModelImpl implements ApiResultContract.Model {
    private Context mContext;

    public ApiResultModelImpl(Context context) {
        mContext = context;
    }

    @Override
    public void loadResult(String key, OnApiBeanLoadListener listener) {
        //根据关键字加载搜索结果
        DBHelper helper = new DBHelper(mContext, "Apis.db3", null, 1);
        List<IndexBean> classes = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = helper.getReadableDatabase().rawQuery("select * from Indexs where mTitle  like '%" + key + "%' order by mTitle ", null);
            while (cursor.moveToNext()) {
                IndexBean c = new ApiBean(cursor.getString(1), cursor.getString(0), cursor.getInt(2));
                classes.add(c);
            }
            if (listener != null) {
                if (classes.size() == 0) {
                    listener.failed("找不到相符记录");
                } else {
                    listener.success(classes);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}