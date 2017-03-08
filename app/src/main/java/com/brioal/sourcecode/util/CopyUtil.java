package com.brioal.sourcecode.util;

import android.content.ClipboardManager;
import android.content.Context;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/3/8.
 */

public class CopyUtil {
    //复制指定的内容到剪贴板
    public static void copy(Context context, String str) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText(str);
    }
}
