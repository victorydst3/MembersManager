package com.asiantech.membersmanager.models;

import lombok.Data;

/**
 *
 * Created by xuanphu on 06/10/2015.
 */
@Data
public class Notification {
    private int mAvata;
    private  String mSender, mTittle, mContent, mTime;
    private Boolean isFavorite, isHot, isCheck;
}
