package com.brioal.sourcecode.useredit.model;

import android.content.Context;

import com.brioal.sourcecode.bean.UserBean;
import com.brioal.sourcecode.interfaces.OnNormalOperatorListener;
import com.brioal.sourcecode.useredit.contract.UserEditContract;
import com.socks.library.KLog;

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by Brioal on 2017/03/01
 */

public class UserEditModelImpl implements UserEditContract.Model {
    private Context mContext;

    @Override
    public void saveUserBean(String headUrl, final UserBean userBean, final OnNormalOperatorListener listener) {
        if (headUrl != null) {
            //先上传图片,再保存信息
            try {
                final BmobFile file = new BmobFile(new File(headUrl));
                file.upload(new UploadFileListener() {
                    @Override
                    public void done(BmobException e) {
                        if (listener == null) {
                            return;
                        }
                        if (e != null) {
                            listener.failed(e.getMessage());
                        }
                        KLog.e("上传图片成功");
                        UserBean user = new UserBean();
                        user.setHead(file);
                        user.setBlogUrl(userBean.getBlogUrl());
                        user.setPro(userBean.getPro());
                        user.setCompany(userBean.getCompany());
                        user.setDesc(userBean.getDesc());
                        BmobUser bmobUser = BmobUser.getCurrentUser(UserBean.class);
                        user.update(bmobUser.getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e != null) {
                                    listener.failed(e.getMessage());

                                }
                                listener.success("");
                            }
                        });
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                if (listener == null) {
                    return;
                }
                listener.failed(e.getMessage());
            }
        } else {
            UserBean user = new UserBean();
            user.setBlogUrl(userBean.getBlogUrl());
            user.setPro(userBean.getPro());
            user.setCompany(userBean.getCompany());
            user.setDesc(userBean.getDesc());
            BmobUser bmobUser = BmobUser.getCurrentUser(UserBean.class);
            user.update(bmobUser.getObjectId(), new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e != null) {
                        listener.failed(e.getMessage());

                    }
                    listener.success("");
                }
            });
        }


    }
}