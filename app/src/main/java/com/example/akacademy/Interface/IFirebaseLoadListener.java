package com.example.akacademy.Interface;

import com.example.akacademy.Model.ItemGroup;

import java.util.List;

public interface IFirebaseLoadListener {
    void onFirebaseLoadSuccess(List<ItemGroup> itemGroupList);
    void onFirebaseLoadFailed(String message);


}
