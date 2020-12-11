package com.qoiu.owlbirthday.presenter;

import com.qoiu.owlbirthday.GameActivityInterface;
import com.qoiu.owlbirthday.model.Box;
import com.qoiu.owlbirthday.model.Engine;

import java.util.List;

public class GamePresenter extends BasePresenter<GameActivityInterface> {

    private Engine engine=new Engine();
    private static GamePresenter presenter;

    public static GamePresenter getInstance(){
        if(presenter==null)presenter=new GamePresenter();
        return presenter;
    }

    public int clickBox(int id){
        int state= engine.openBox(id);
        if(state>0){
            view().addPrize(state);
        }
        return state;
    }

    public List<Box> getBoxList(){
        return engine.getBoxList();
    }

    @Override
    public int getGridSize() {
        return engine.getGridSize();
    }
}
