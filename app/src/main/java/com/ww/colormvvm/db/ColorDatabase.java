package com.ww.colormvvm.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.ww.colormvvm.ColorExecutors;
import com.ww.colormvvm.db.converter.DateConverter;
import com.ww.colormvvm.db.dao.ColorDao;
import com.ww.colormvvm.db.entity.ColorEntity;

import java.util.List;

/**
 * Created by wangwang on 2018/3/22.
 */
@Database(entities = {ColorEntity.class}, version = 3,exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class ColorDatabase extends RoomDatabase{
    private final MutableLiveData<Boolean> isDatabaseCreated = new MutableLiveData<>();
    @VisibleForTesting
    public static final String DATABASE_NAME = "color-db";
    private static ColorDatabase instance;
    public abstract ColorDao colorDao();
    public static ColorDatabase getInstance(final Context context, final ColorExecutors executors) {
        if (instance == null) {
            synchronized (ColorExecutors.class) {
                if (instance == null) {
                    instance = buildDatabase(context.getApplicationContext(), executors);
                    instance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return instance;
    }
    private void updateDatabaseCreated(final Context context){
        if(context.getDatabasePath(DATABASE_NAME).exists()){
            setDatabaseCreated();
        }
    }
    private void setDatabaseCreated(){
        isDatabaseCreated.postValue(true);
    }
    public LiveData<Boolean> getDatabaseCreated() {
        return isDatabaseCreated;
    }
    private static ColorDatabase buildDatabase(final Context appContext,
                                               final ColorExecutors executors){
        return Room.databaseBuilder(appContext,ColorDatabase.class,DATABASE_NAME).addCallback(new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                executors.diskIO().execute(()->{
                ColorDatabase colorDatabase=ColorDatabase.getInstance(appContext,executors);
                List<ColorEntity> colors=ColorGenerator.generateColors();
                insertData(colorDatabase,colors);
                colorDatabase.setDatabaseCreated();
                });
            }
        }).build();
    }
    private static void insertData(final ColorDatabase database, final List<ColorEntity> colors) {
        database.runInTransaction(() -> {
            database.colorDao().insertAll(colors);
        });
    }
}
