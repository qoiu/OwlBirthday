package com.qoiu.owlbirthday.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Engine {

    private int gridSize=3;
    private List<Box> boxList=new ArrayList<>();
    ArrayList<Integer> items= new ArrayList<>(Arrays.asList(1,1,2,2,3,3,4,4,5));
    private Box openedBox;

    public Engine(){
        startGame();
    }

    public void startGame(){
        Random random=new Random();
        for (int i=0;i<gridSize*gridSize;i++){
            int r=random.nextInt(items.size());
            int id=items.remove(r);
            boxList.add(new Box(id));
        }
    }

    public int getGridSize() {
        return gridSize;
    }

    public List<Box> getBoxList() {
        return boxList;
    }

    public int openBox(int id){
        if(boxList.get(id).getGiftID()==5){
            openedBox=null;
            int state=boxList.get(id).removeGift();
            closeBoxes();
            return state;
        }
        if(!boxList.get(id).isOpened()) {
            if(openedBox==null){
                closeBoxes();
                openedBox=boxList.get(id);
                openedBox.setOpened(true);
            }else {
                if(boxList.get(id).equals(openedBox)){
                    openedBox.removeGift();
                    openedBox=null;
                    return boxList.get(id).removeGift();
                }else {
                    boxList.get(id).setOpened(true);
                    openedBox=null;
                }
            }
        }
        return 0;
    }

    private void closeBoxes(){
        openedBox=null;
        for (Box box:boxList) {
            if(box.getGiftID()!=0){
                box.setOpened(false);
            }
        }
    }
}