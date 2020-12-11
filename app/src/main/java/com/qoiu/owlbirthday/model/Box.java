package com.qoiu.owlbirthday.model;

import androidx.annotation.Nullable;

public class Box {
    private boolean isOpened=false;
    private int giftID;
    public Box(int giftID){
        this.giftID=giftID;
    }

    public int getGiftID() {
        return giftID;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public int removeGift(){
        int id=giftID;
        giftID=0;
        isOpened=true;
        return id;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj==null)return false;
        if(obj.getClass()!=Box.class)return false;
        return giftID==((Box) obj).getGiftID();
    }

    public void setOpened(boolean opened) {
        isOpened = opened;
    }
}
