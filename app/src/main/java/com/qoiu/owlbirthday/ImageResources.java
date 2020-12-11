package com.qoiu.owlbirthday;

import com.qoiu.owlbirthday.model.Box;

public class ImageResources {
    private static final int[] imgRes = {
            R.mipmap.item_0,
            R.mipmap.item_1,
            R.mipmap.item_2,
            R.mipmap.item_3,
            R.mipmap.item_4,
            R.mipmap.item_5};


    public static int getBackImg(Box box) {
        return box.isOpened() ? R.mipmap.box_opened : R.mipmap.box_closed;
    }

    public static int getImageById(int id){
        return imgRes[id];
    }
}
