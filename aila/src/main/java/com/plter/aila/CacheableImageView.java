package com.plter.aila;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by plter on 12/14/15.
 */
public class CacheableImageView extends ImageView {

    private AsyncTask<Void, Void, Bitmap> currentLoadingTask = null;
    private OnImageLoadListener onImageLoadListener = null;
    private Bitmap currentBitmap = null;

    public CacheableImageView(Context context) {
        super(context);

        onInit();
    }

    public CacheableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        onInit();
    }

    public CacheableImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        onInit();
    }

    private void onInit() {
    }


    /**
     * Load a image from net
     *
     * @param urlString
     */
    public void loadImage(final String urlString) {

        if (currentLoadingTask != null) {

            final Context context = getContext();

            currentLoadingTask = new AsyncTask<Void, Void, Bitmap>() {
                @Override
                protected Bitmap doInBackground(Void... params) {

                    Bitmap result = null;

                    try {
                        String localName = URLEncoder.encode(urlString, "UTF-8");
                        File filesDir = context.getFilesDir();
                        File imagesCacheDir = new File(filesDir, "CachedImages");
                        if (!imagesCacheDir.exists()) {
                            imagesCacheDir.mkdirs();
                        }
                        File localImageFile = new File(imagesCacheDir, localName);

                        //check and cache image data
                        if (!localImageFile.exists()) {
                            try {
                                InputStream netInputStream = new URL(urlString).openStream();
                                FileOutputStream fos = new FileOutputStream(localImageFile);
                                byte[] buffer = new byte[2048];
                                int size = -1;
                                while ((size = netInputStream.read(buffer)) != -1) {
                                    fos.write(buffer, 0, size);
                                }
                                fos.flush();

                                netInputStream.close();
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        if (localImageFile.exists()) {
                            try {
                                FileInputStream fis = new FileInputStream(localImageFile);
                                result = BitmapFactory.decodeStream(fis);
                                fis.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    return result;
                }

                @Override
                protected void onPostExecute(Bitmap result) {
                    currentLoadingTask = null;
                    currentBitmap = result;

                    if (result != null) {
                        setImageBitmap(result);
                    }

                    if (getOnImageLoadListener() != null) {
                        getOnImageLoadListener().onLoad(CacheableImageView.this);
                    }
                }
            };

            currentLoadingTask.execute();
        }
    }

    public boolean isLoading() {
        return currentLoadingTask != null;
    }

    public void abortLoading() {
        if (currentLoadingTask != null) {
            currentLoadingTask.cancel(true);
            currentLoadingTask = null;
        }
    }

    public void setOnImageLoadListener(OnImageLoadListener onImageLoadListener) {
        this.onImageLoadListener = onImageLoadListener;
    }

    public OnImageLoadListener getOnImageLoadListener() {
        return onImageLoadListener;
    }

    public interface OnImageLoadListener {
        void onLoad(CacheableImageView target);
    }

    public Bitmap getCurrentBitmap() {
        return currentBitmap;
    }

    public void tryToReleaseCurrentBitmap() {
        if (currentBitmap != null) {
            setImageBitmap(null);
            currentBitmap.recycle();
        }
    }
}
