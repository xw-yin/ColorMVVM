package com.ww.colormvvm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.ww.colormvvm.db.ColorDatabase;
import com.ww.colormvvm.db.entity.ColorEntity;

import java.util.List;

/**
 * Repository handling the work with products and comments.
 */
public class ColorRepository {

    private static ColorRepository sInstance;

    private final ColorDatabase mDatabase;
    private MediatorLiveData<List<ColorEntity>> colors;
    private MediatorLiveData<ColorEntity> firstColor;
    private ColorRepository(final ColorDatabase database) {
        mDatabase = database;
         colors= new MediatorLiveData<>();
        firstColor=new MediatorLiveData<>();
        firstColor.addSource(mDatabase.colorDao().getFirstColor(),colorEntity -> {
            if (mDatabase.getDatabaseCreated().getValue() != null) {
                firstColor.postValue(colorEntity);
            }
        });
        colors.addSource(mDatabase.colorDao().loadAllColors(),
                colorEntities -> {
                    if (mDatabase.getDatabaseCreated().getValue() != null) {
                        colors.postValue(colorEntities);
                    }
                });
    }

    public static ColorRepository getInstance(final ColorDatabase database) {
        if (sInstance == null) {
            synchronized (ColorDatabase.class) {
                if (sInstance == null) {
                    sInstance = new ColorRepository(database);
                }
            }
        }
        return sInstance;
    }

    /**
     * Get the list of products from the database and get notified when the data changes.
     */
    public LiveData<List<ColorEntity>> getColors() {
        return colors;
    }
    public LiveData<ColorEntity> loadColor(final String colorId) {
        return mDatabase.colorDao().loadColor(colorId);
    }
    public LiveData<ColorEntity> getFirstColor() {
        return firstColor;
    }
}
