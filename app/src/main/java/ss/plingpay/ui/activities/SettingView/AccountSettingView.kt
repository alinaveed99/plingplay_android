package ss.plingpay.ui.activities.SettingView

import com.hannesdorfmann.mosby3.mvp.MvpView
import ss.plingpay.pojos.Exchange
import ss.plingpay.pojos.Userinfo

/**
 * Created by Sammie on 8/22/2017.
 */
interface AccountSettingView: MvpView {
    fun dismissExchangeProgress()
    fun onErrorGettingExchange()
    fun onNoExchangeFound()
    fun onSuccessExchangeFound(exchanges: Array<CharSequence>, exchanges1: MutableList<Exchange>)
    fun setExchangeMethods(exchangeMethods: Array<CharSequence>)
    fun removeExchange()
    fun isClpSelected(b: Boolean)
    fun dismissProgress()
    fun onErrorSyncingData()
    fun onSuccessSyncingData(info: Userinfo?)
}