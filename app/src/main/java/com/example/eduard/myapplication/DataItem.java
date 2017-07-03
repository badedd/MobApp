package com.example.eduard.myapplication;

/**
 * Created by Eduard on 26.06.2017.
 */

public class DataItem extends Todo {
    public DataItem() {
        super();
    }
    public DataItem(String name, String description, boolean favourite, boolean done,String expire, int dbID) {
        super(name, description, favourite, done, expire, dbID);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public String getDescription() {
        return super.getDescription();
    }

    @Override
    public void setDescription(String description) {
        super.setDescription(description);
    }

    @Override
    public boolean isFavourite() {
        return super.isFavourite();
    }

    @Override
    public void setFavourite(boolean favourite) {
        super.setFavourite(favourite);
    }

    @Override
    public void setDone(boolean favourite) {
        super.setDone(favourite);
    }

    @Override
    public boolean isDone() {
        return super.isDone();
    }

    @Override
    public String getExpire() {
        return super.getExpire();
    }

    @Override
    public void setExpire(String expire) {
        super.setExpire(expire);
    }

    @Override
    public void set_dbID(int id) {
        super.set_dbID(id);
    }

    @Override
    public int get_dbID() {
        return super.get_dbID();
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
