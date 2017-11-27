package ss.plingpay.di.modules;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Sammie on 5/31/2017.
 */

@Module
public class AppModule {

    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }


    @Provides
    Context provideContext() {
        return context;
    }


}
