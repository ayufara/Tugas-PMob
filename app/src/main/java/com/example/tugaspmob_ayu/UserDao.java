package com.example.tugaspmob_ayu;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {

    @Insert
    void registerUser(UserEntity userEntity);

    @Query("SELECT * from tbl_user where nim=(:nim) and password=(:password)")
    UserEntity login(String nim, String password);
}