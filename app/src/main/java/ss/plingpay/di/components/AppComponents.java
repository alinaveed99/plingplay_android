package ss.plingpay.di.components;

import org.jetbrains.annotations.NotNull;

import javax.inject.Singleton;

import dagger.Component;
import ss.plingpay.di.modules.AppModule;
import ss.plingpay.di.modules.NetModule;
import ss.plingpay.di.modules.RepositoryModule;
import ss.plingpay.services.ContactService;
import ss.plingpay.ui.activities.SettingView.AccountSettingActivity;
import ss.plingpay.ui.activities.mainView.MainActivity;
import ss.plingpay.ui.activities.splashScreenView.NewSplashScreenActivity;
import ss.plingpay.ui.fragments.confirmationFragment.ConfirmationFragment;
import ss.plingpay.ui.fragments.inboxInvoice.invoiceDetailFragment.InvoiceDetailFragment;
import ss.plingpay.ui.fragments.inboxInvoice.invoiceListFragment.InboxInvoicesFragment;
import ss.plingpay.ui.fragments.requestConfirmation.RequestConfirmationFragment;

/**
 * Created by Sammie on 5/31/2017.
 */

@Singleton
@Component(modules = {NetModule.class, RepositoryModule.class, AppModule.class})
public interface AppComponents {
    void Inject(RequestConfirmationFragment requestConfirmationFragment);
    void Inject(InboxInvoicesFragment inboxInvoicesFragment);
    void Inject(InvoiceDetailFragment invoiceDetailFragment);
    void Inject(ConfirmationFragment confirmationFragment);
    void Inject(MainActivity mainActivity);
    void Inject(NewSplashScreenActivity newSplashScreenActivity);
    void Inject(ContactService contactService);

    void Inject(@NotNull AccountSettingActivity accountSettingActivity);
}
