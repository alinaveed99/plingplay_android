package ss.plingpay.infrastructure;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import ss.plingpay.di.components.AppComponents;
import ss.plingpay.di.components.DaggerAppComponents;
import ss.plingpay.di.modules.AppModule;
import ss.plingpay.di.modules.NetModule;
import ss.plingpay.di.modules.RepositoryModule;
import ss.plingpay.utilz.Utilz;

/**
 * Created by samar_000 on 8/15/2016.
 */

public class PlingPay extends Application {

    private Auth auth;

    private AppComponents appComponents;

    @Override
    public void onCreate() {
        super.onCreate();

        auth = new Auth();
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfig);


        initDagger();

    }

    private void initDagger() {

            appComponents = DaggerAppComponents.builder()
                    .netModule(new NetModule(Utilz.AppConstants.BASE_URL_1))
                    .appModule(new AppModule(this))
                    .repositoryModule(new RepositoryModule())
                    .build();


    }

    public AppComponents getAppComponents() {
        return appComponents;
    }


    public Auth getAuth() {
        return auth;
    }

}
