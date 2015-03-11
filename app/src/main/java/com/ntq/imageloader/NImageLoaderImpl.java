package com.ntq.imageloader;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

import moony.vn.flavorlife.R;

/**
 *
 * @author TUNGDX
 *
 */

/**
 * <a href="https://github.com/nostra13/Android-Universal-Image-Loader">UIL<a/>
 * library impls {@link NImageLoader} for default ImageLoader
 */
public class NImageLoaderImpl implements NImageLoader {
    private ImageLoaderConfiguration mImageLoaderConfig;
    private DisplayImageOptions mDisplayImageOptions;
    private DisplayImageOptions.Builder mDisplayImageBuilder;

    public NImageLoaderImpl(Context context) {

        mDisplayImageBuilder = new DisplayImageOptions.Builder()
                .cacheInMemory(true).cacheOnDisk(true)
                        // TODO change image on loading
                .showImageOnLoading(R.drawable.ic_launcher)
                .considerExifParams(true).resetViewBeforeLoading(true);
        mDisplayImageOptions = mDisplayImageBuilder.build();
        mImageLoaderConfig = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024).memoryCacheSizePercentage(30)
                        // TODO 50 Mb, maybe change to for your app
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                        // TODO Remove for release app
                .writeDebugLogs().threadPoolSize(3)
                        // .memoryCacheExtraOptions(200, 200)
                .defaultDisplayImageOptions(mDisplayImageOptions).build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(mImageLoaderConfig);
    }

    @Override
    public void display(String url, ImageView imageView) {
        display(url, imageView, null, null, null);
    }

    @Override
    public void display(final String url, ImageView imageView,
                        final NImageLoadingListener listener) {
        display(url, imageView, null, listener, null);
    }

    @Override
    public void display(String url, ImageView imageView,
                        NImageLoadingProgressListener progressListener) {
        display(url, imageView, null, null, progressListener);
    }

    @Override
    public void display(String url, ImageView imageView,
                        NImageLoadingListener listener,
                        NImageLoadingProgressListener progressListener) {
        display(url, imageView, null, listener, progressListener);
    }

    @Override
    public void display(String url, ImageView imageView,
                        NDisplayOptions displayOption) {
        display(url, imageView, displayOption, null, null);
    }

    @Override
    public void display(String url, ImageView imageView,
                        NDisplayOptions options, NImageLoadingListener listener) {
        display(url, imageView, options, listener, null);
    }

    @Override
    public void display(String url, ImageView imageView,
                        NDisplayOptions options,
                        NImageLoadingProgressListener progressListener) {
        display(url, imageView, options, null, progressListener);
    }

    @Override
    public void display(String url, ImageView imageView,
                        NDisplayOptions options, NImageLoadingListener listener,
                        NImageLoadingProgressListener progressListener) {
        if (options != null) {
            ImageAware imageAware = new ImageViewAware(imageView,
                    options.isCheckActualViewSize());
            // TODO display as option param
            DisplayImageOptions displayImageOptions = null;
            if (options.isNoImageWhileLoading()) {
                mDisplayImageBuilder
                        .showImageOnLoading(android.R.color.transparent);
            }
            if (options.getImageOnLoading() != -1) {
                mDisplayImageBuilder.showImageOnLoading(options
                        .getImageOnLoading());
            }
            if (options.getImageOnFail() != -1) {
                mDisplayImageBuilder.showImageOnFail(options.getImageOnFail());
            }
            mDisplayImageBuilder.cacheOnDisk(options.isCacheOnDisk());
            displayImageOptions = mDisplayImageBuilder.build();
            ImageLoader.getInstance().displayImage(url, imageAware,
                    displayImageOptions,
                    new LoadingAndProgressListenerImpl(listener),
                    new LoadingAndProgressListenerImpl(progressListener));
        } else {
            ImageLoader.getInstance().displayImage(url, imageView, null,
                    new LoadingAndProgressListenerImpl(listener),
                    new LoadingAndProgressListenerImpl(progressListener));
        }

    }

    @Override
    public void display(Uri uri, ImageView imageView) {
        display(uri, imageView, null, null, null);
    }

    @Override
    public void display(Uri uri, ImageView imageView,
                        NDisplayOptions displayOption) {
        display(uri, imageView, displayOption, null, null);
    }

    @Override
    public void display(Uri uri, ImageView imageView,
                        NImageLoadingListener listener) {
        display(uri, imageView, null, listener, null);
    }

    @Override
    public void display(Uri uri, ImageView imageView,
                        NImageLoadingProgressListener progressListener) {
        display(uri, imageView, null, null, progressListener);
    }

    @Override
    public void display(Uri uri, ImageView imageView,
                        NImageLoadingListener listener,
                        NImageLoadingProgressListener progressListener) {
        display(uri, imageView, null, listener, progressListener);
    }

    @Override
    public void display(Uri uri, ImageView imageView, NDisplayOptions options,
                        NImageLoadingListener listener) {
        display(uri, imageView, options, listener, null);
    }

    @Override
    public void display(Uri uri, ImageView imageView, NDisplayOptions options,
                        NImageLoadingProgressListener progressListener) {
        display(uri, imageView, options, null, progressListener);
    }

    @Override
    public void display(Uri uri, ImageView imageView, NDisplayOptions options,
                        NImageLoadingListener listener,
                        NImageLoadingProgressListener progressListener) {
        display(uri.toString(), imageView, options, listener, progressListener);
    }

    @Override
    public void display(int resourceId, ImageView imageView) {
        display(resourceId, imageView, null, null, null);
    }

    @Override
    public void display(int resourceId, ImageView imageView,
                        NImageLoadingListener listener) {
        display(resourceId, imageView, null, listener, null);
    }

    @Override
    public void display(int resourceId, ImageView imageView,
                        NImageLoadingProgressListener progressListener) {
        display(resourceId, imageView, null, null, progressListener);
    }

    @Override
    public void display(int resourceId, ImageView imageView,
                        NDisplayOptions options) {
        display(resourceId, imageView, options, null, null);
    }

    @Override
    public void display(int resourceId, ImageView imageView,
                        NDisplayOptions options, NImageLoadingListener listener) {
        display(resourceId, imageView, options, listener, null);
    }

    @Override
    public void display(int resourceId, ImageView imageView,
                        NDisplayOptions options,
                        NImageLoadingProgressListener progressListener) {
        display(resourceId, imageView, options, null, progressListener);
    }

    @Override
    public void display(int resourceId, ImageView imageView,
                        NImageLoadingListener listener,
                        NImageLoadingProgressListener progressListener) {
        display(resourceId, imageView, null, listener, progressListener);
    }

    @Override
    public void display(int resourceId, ImageView imageView,
                        NDisplayOptions options, NImageLoadingListener listener,
                        NImageLoadingProgressListener progressListener) {
        display("drawable://" + resourceId, imageView, options, listener,
                progressListener);
    }

    @Override
    public void display(File file, ImageView imageView) {
        display(file, imageView, null, null, null);
    }

    @Override
    public void display(File file, ImageView imageView,
                        NImageLoadingProgressListener progressListener) {
        display(file, imageView, null, null, progressListener);
    }

    @Override
    public void display(File file, ImageView imageView,
                        NImageLoadingListener listener) {
        display(file, imageView, null, listener, null);
    }

    @Override
    public void display(File file, ImageView imageView,
                        NImageLoadingListener listener,
                        NImageLoadingProgressListener progressListener) {
        display(file, imageView, null, listener, progressListener);
    }

    @Override
    public void display(File file, ImageView imageView, NDisplayOptions options) {
        display(file, imageView, options, null, null);
    }

    @Override
    public void display(File file, ImageView imageView,
                        NDisplayOptions options,
                        NImageLoadingProgressListener progressListener) {
        display(file, imageView, options, null, progressListener);
    }

    @Override
    public void display(File file, ImageView imageView,
                        NDisplayOptions options, NImageLoadingListener listener) {
        display(file, imageView, options, listener, null);
    }

    @Override
    public void display(File file, ImageView imageView,
                        NDisplayOptions options, NImageLoadingListener listener,
                        NImageLoadingProgressListener progressListener) {
        display(Uri.fromFile(file), imageView, options, listener,
                progressListener);
    }

    @Override
    public void loadImage(String url, NImageLoadingListener listener) {
        ImageLoader.getInstance().loadImage(url,
                new LoadingAndProgressListenerImpl(listener));
        loadImage(url, listener, null);
    }

    @Override
    public void loadImage(String url, NImageLoadingListener listener,
                          int widthSize, int heightSize) {
        loadImage(url, listener, null, widthSize, heightSize);
    }

    @Override
    public void loadImage(String url,
                          NImageLoadingProgressListener progressListener) {
        loadImage(url, null, progressListener);
    }

    @Override
    public void loadImage(String url,
                          NImageLoadingProgressListener progressListener, int widthSize,
                          int heightSize) {
        loadImage(url, null, progressListener, widthSize, heightSize);
    }

    @Override
    public void loadImage(String url, NImageLoadingListener listener,
                          NImageLoadingProgressListener progressListener) {
        LoadingAndProgressListenerImpl impl = new LoadingAndProgressListenerImpl(
                listener, progressListener);
        ImageLoader.getInstance().loadImage(url, null, null, impl, impl);
    }

    @Override
    public void loadImage(String url, NImageLoadingListener listener,
                          NImageLoadingProgressListener progressListener, int widthSize,
                          int heightSize) {
        LoadingAndProgressListenerImpl impl = new LoadingAndProgressListenerImpl(
                listener, progressListener);
        ImageSize imageSize = new ImageSize(widthSize, heightSize);
        ImageLoader.getInstance().loadImage(url, imageSize, null, impl, impl);
    }

    private class LoadingAndProgressListenerImpl implements
            ImageLoadingListener, ImageLoadingProgressListener {
        NImageLoadingListener loadingListener;
        NImageLoadingProgressListener progressListener;

        public LoadingAndProgressListenerImpl(
                NImageLoadingListener loadingListener) {
            this.loadingListener = loadingListener;
        }

        public LoadingAndProgressListenerImpl(
                NImageLoadingProgressListener progressListener) {
            this.progressListener = progressListener;
        }

        public LoadingAndProgressListenerImpl(
                NImageLoadingListener loadingListener,
                NImageLoadingProgressListener progressListener) {
            this.loadingListener = loadingListener;
            this.progressListener = progressListener;
        }

        @Override
        public void onLoadingStarted(String paramString, View paramView) {
            if (loadingListener != null)
                loadingListener.onLoadingStarted(paramString, paramView);
        }

        @Override
        public void onLoadingFailed(String paramString, View paramView,
                                    FailReason paramFailReason) {
            if (loadingListener != null)
                loadingListener.onLoadingFailed(paramString, paramView);
        }

        @Override
        public void onLoadingComplete(String paramString, View paramView,
                                      Bitmap paramBitmap) {
            if (loadingListener != null)
                loadingListener.onLoadingComplete(paramString, paramView,
                        paramBitmap);
        }

        @Override
        public void onLoadingCancelled(String paramString, View paramView) {
            if (loadingListener != null)
                loadingListener.onLoadingCancelled(paramString, paramView);
        }

        @Override
        public void onProgressUpdate(String imageUri, View view, int current,
                                     int total) {
            if (progressListener != null)
                progressListener.onProgressUpdate(imageUri, view, current,
                        total);
        }

    }
}
