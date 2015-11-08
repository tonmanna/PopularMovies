package lib;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.integration.okhttp.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;
import com.facebook.stetho.okhttp.StethoInterceptor;
import com.squareup.okhttp.OkHttpClient;

import java.io.InputStream;

/**
 * Created by Tonman on 27/10/2558.
 */
public class StethoOkHttpGlideModule  implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) { }

    @Override
    public void registerComponents(Context context, Glide glide) {
        OkHttpClient client = new OkHttpClient();
        client.networkInterceptors().add(new StethoInterceptor());
        glide.register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(client));
    }
}
