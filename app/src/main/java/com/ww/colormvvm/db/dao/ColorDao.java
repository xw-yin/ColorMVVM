package com.ww.colormvvm.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.ww.colormvvm.db.entity.ColorEntity;

import java.util.List;

/**
 * Created by wangwang on 2018/3/22.
 */
@Dao
public interface ColorDao {
    @Query("SELECT * FROM colors")
    LiveData<List<ColorEntity>> loadAllColors();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ColorEntity> products);

    @Query("select * from colors where id = :colorId ")
    LiveData<ColorEntity> loadColor(String colorId);

    @Query("select * from colors  limit 1")
    LiveData<ColorEntity> getFirstColor();
}
