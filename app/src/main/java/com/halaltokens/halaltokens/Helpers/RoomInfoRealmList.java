/*
 * Class that stores list of rooms into local storage
 */


package com.halaltokens.halaltokens.Helpers;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmField;

public class RoomInfoRealmList extends RealmObject {

    @PrimaryKey
    private String placeholder = "";

    @RealmField
    private RealmList<RoomInfo> roomInfoArrayList = new RealmList<>();

    public RoomInfoRealmList() {
    }

    public void add(RoomInfo roomInfo) {
        roomInfoArrayList.add(roomInfo);
        placeholder = roomInfoArrayList.get(0).getRoomName();
    }

    public void addList(ArrayList<RoomInfo> arrayList) {
        roomInfoArrayList.addAll(arrayList);
        placeholder = roomInfoArrayList.get(0).getRoomName();
    }

    public RoomInfo getRoomInfo(int i) {
        return roomInfoArrayList.get(i);
    }

    public int size() {
        return roomInfoArrayList.size();
    }

    @Override
    public String toString() {
        String result = "";
        for (RoomInfo room : roomInfoArrayList) {
            result += room + "\n";
        }
        return result;
    }

    public String toStringShare() {
        String result = "";
        for (RoomInfo room : roomInfoArrayList) {
            result += room.toStringShare() + "\n";
        }
        return result;
    }
}
