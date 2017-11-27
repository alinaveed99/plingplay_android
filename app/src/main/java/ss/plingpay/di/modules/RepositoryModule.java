package ss.plingpay.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import ss.plingpay.data.TaskRepository;

/**
 * Created by Sammie on 5/31/2017.
 */
@Module
public class RepositoryModule {

    @Singleton
    @Provides
    TaskRepository provideRepository(Retrofit retrofit) {
        return new TaskRepository(retrofit);
    }

}
