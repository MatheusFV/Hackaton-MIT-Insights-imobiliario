package br.com.baseproject.baseproject.Utils;

/**
 * Created by mvenosa on 15/11/17.
 */

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.nostra13.universalimageloader.cache.disc.impl.LimitedAgeDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.LimitedAgeMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.utils.StorageUtils;

/**
 * Foi necessario criar essa classe porque quando o imageLoader vai pra background perde a instacia por ser singleton
 * a biblioteca cria uma nova instacia sozinha, mas sem as configuracoes custom, com essa classe ele passa as cofiguracoes custom quando for criada
 * uma nova instancia
 */

public class ImageLoaderAS extends ImageLoader {

    private volatile static ImageLoader instance;

    /**
     * Returns singleton class instance
     */

    public static ImageLoader getInstance(Activity activity) {
        if (instance == null) {
            synchronized (ImageLoader.class) {
                if (instance == null) {
                    instance = ImageLoader.getInstance();
                    instance.init(setConfigurations(activity));
                }
            }
        }
        return instance;
    }

    public static ImageLoaderConfiguration setConfigurations(Context context) {
        // UNIVERSAL IMAGE LOADER SETUP
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .showImageOnLoading(android.R.color.darker_gray) //resource/drawable/color
                .showImageForEmptyUri(android.R.color.darker_gray) //resource/drawable/color
                .showImageOnFail(android.R.color.darker_gray) //resource/drawable/color
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .defaultDisplayImageOptions(defaultOptions)//display options
                .memoryCache(new LimitedAgeMemoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024), 5 * 60))
                .diskCache(new LimitedAgeDiskCache(StorageUtils.getIndividualCacheDirectory(context), 24 * 60 * 60))
                .diskCacheSize(100 * 1024 * 1024).build();

        return config;
        // END - UNIVERSAL IMAGE LOADER SETUP
    }

    public static void displayFromFirebase(final Activity activity, StorageReference storageReference, final ImageView imageview) {

        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                ImageLoaderAS.getInstance(activity).displayImage(uri.toString(), imageview);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Log.d("sas","A");
            }
        });
    }
}
