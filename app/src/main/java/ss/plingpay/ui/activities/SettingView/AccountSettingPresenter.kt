package ss.plingpay.ui.activities.SettingView

import com.google.gson.JsonElement
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import ss.plingpay.data.TaskDataSource
import ss.plingpay.pojos.ExchangeListPOJO
import ss.plingpay.pojos.Userinfo

/**
 * Created by Sammie on 8/22/2017.
 */
class AccountSettingPresenter(private val repo: TaskDataSource) : MvpBasePresenter<AccountSettingView>() {

    private val disposable = CompositeDisposable()

    private var currencyId = ""
    private var exchangeId = ""

    fun syncData(firstName: String, lastName: String, phoneNumber: String, addressOne: String,
                 addressTwo: String, country: String, city: String, state: String, zipCode: String,
                 currency: String, exchange: String, exchangeMethod: String, bankAccountNumber: String,
                 bankAccountAddress: String, bankName: String, bankTitle: String, rut: String, id: String, info: Userinfo?) {


        repo.syncUserAccountSetting(firstName, lastName, phoneNumber, addressOne, addressTwo, city, state, country,
                zipCode, currency, "1", "1", id, bankAccountNumber, bankName, bankTitle, "",
                exchangeId, "", bankAccountAddress, exchangeMethod, bankAccountNumber, rut)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally {
                    if (isViewAttached) view?.dismissProgress()
                }
                .subscribeWith(object : DisposableSingleObserver<JsonElement>() {
                    override fun onError(p0: Throwable?) {
                        p0?.printStackTrace()

                        if (isViewAttached) view?.onErrorSyncingData()
                    }

                    override fun onSuccess(p0: JsonElement?) {

                        try {
                            if (p0 != null) {
                                val jsonObj = p0.asJsonObject
                                val response = jsonObj.get("return").asString

                                if (response == "success") {

                                    info?.firstName = firstName
                                    info?.lastName = lastName
                                    info?.phone = phoneNumber
                                    info?.address1 = addressOne
                                    info?.address2 = addressTwo
                                    info?.country = country
                                    info?.city = city
                                    info?.state = state
                                    info?.zip = zipCode

                                    info?.currancyId = currency
                                    info?.curr_id = currencyId
                                    info?.exchangeid = exchangeId
                                    info?.address = bankAccountAddress
                                    info?.bankname = bankName
                                    info?.bankaccountnumber = bankAccountNumber
                                    info?.rut = rut
                                    info?.withdrawaltypetext = exchangeMethod
                                    info?.exchangeid = exchangeId
                                    info?.exchangeid = exchangeId

                                    info?.setCurrencyIdWithListener(currency)
                                    info?.setFirstNameWithListener(firstName)

                                    if (isViewAttached) view?.onSuccessSyncingData(info)

                                } else {
                                    if (isViewAttached) view?.onErrorSyncingData()
                                }


                            } else {
                                if (isViewAttached) view?.onErrorSyncingData()
                            }


                        } catch (ex: Exception) {
                            if (isViewAttached) view?.onErrorSyncingData()
                        }

                    }

                })
    }

    override fun detachView(retainInstance: Boolean) {
        super.detachView(retainInstance)
        if (!retainInstance) disposable.clear()
    }

    fun currencySelected(curr: String?, id: String?) {

        currencyId = id.toString()

        if (isViewAttached) view?.isClpSelected(curr == "CLP")

        when (curr) {
            "PKR" -> if (isViewAttached) view?.setExchangeMethods(arrayOf("Receive to bank account", "Cash withdrawal at Js Bank"))
            "CLP", "VND" -> if (isViewAttached) view?.setExchangeMethods(arrayOf("Receive to bank account"))
            else -> {
                if (isViewAttached) view?.removeExchange()
                exchangeId = ""
            }
        }


        disposable.add(repo.getCurrencyExchange(id, curr)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally {
                    if (isViewAttached) view?.dismissExchangeProgress()
                }
                .subscribeWith(object : DisposableSingleObserver<ExchangeListPOJO>() {

                    override fun onSuccess(response: ExchangeListPOJO?) {

                        if (response != null) {

                            if (response.response == "success") {

                                if (response.exchanges.isNotEmpty()) {

                                    val arr = arrayOfNulls<CharSequence>(response.exchanges.size)

                                    (0 until response.exchanges.size)
                                            .forEach { arr[it] = response.exchanges[it].title }

                                    if (isViewAttached) view?.onSuccessExchangeFound(arr as Array<CharSequence>, response.exchanges)

                                } else {
                                    if (isViewAttached) view?.onNoExchangeFound()
                                }

                            } else if (response.response == "faild") {
                                if (isViewAttached) view?.onNoExchangeFound()
                            }

                        } else {
                            if (isViewAttached) view?.onErrorGettingExchange()
                        }

                    }

                    override fun onError(p0: Throwable?) {

                        p0?.printStackTrace()
                        if (isViewAttached) view?.onErrorGettingExchange()

                    }

                }))

    }

    fun onExchangeSelected(exchange: String, id: String) {
        exchangeId = id
    }

    fun onExchangeMethodSelected(toString: String) {
    }

    fun onCountrySelected(name: String, id: String) {
    }


}