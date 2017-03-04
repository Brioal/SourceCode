package com.brioal.sourcecode.classlist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.brioal.sourcecode.R;
import com.brioal.sourcecode.base.BaseActivity;

public class ClassListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_class_list);
        // TODO: 2017/3/4 Layout 
    }

    public static void enterClassList(Context context) {
        Intent intent = new Intent(context, ClassListActivity.class);
        context.startActivity(intent);
    }
}
