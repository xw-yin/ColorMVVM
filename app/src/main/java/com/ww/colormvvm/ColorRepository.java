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
    private MediatorLiveData<List<ColorEntity>> mObservableProducts;

    private ColorRepository(final ColorDatabase database) {
        mDatabase = database;
        mObservableProducts = new MediatorLiveData<>();

        mObservableProducts.addSource(mDatabase.colorDao().loadAllColors(),
                colorEntities -> {
                    if (mDatabase.getDatabaseCreated().getValue() != null) {
                        mObservableProducts.postValue(colorEntities);
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
        return mObservableProducts;
    }
    public LiveData<ColorEntity> loadColor(final String colorId) {
        return mDatabase.colorDao().loadColor(colorId);
    }
}
