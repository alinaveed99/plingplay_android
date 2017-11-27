package ss.plingpay.ui.activities.SettingView

import android.os.Bundle
import android.support.annotation.IdRes
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.hannesdorfmann.mosby3.mvp.MvpActivity
import ss.plingpay.R
import ss.plingpay.data.TaskRepository
import ss.plingpay.infrastructure.PlingPay
import ss.plingpay.pojos.Exchange
import ss.plingpay.pojos.Userinfo
import ss.plingpay.ui.activities.SettingView.dialogs.SettingCountryDialog
import ss.plingpay.ui.activities.SettingView.dialogs.SettingCurrencyDialog
import ss.plingpay.ui.activities.SettingView.dialogs.SettingFieldDialog
import ss.plingpay.utilz.Listeners
import ss.plingpay.utilz.Utilz
import javax.inject.Inject


/**
 * Created by Sammie on 7/18/2017.
 */

class AccountSettingActivity : MvpActivity<AccountSettingView, AccountSettingPresenter>(),
        AccountSettingView, SettingFieldDialog.Listeners, Listeners.CurrencySelecter, SettingCountryDialog.CountryDialogListener {


    @Inject
    lateinit var taskRepo: TaskRepository

    override fun createPresenter(): AccountSettingPresenter {
        (application as PlingPay).appComponents.Inject(this)
        return AccountSettingPresenter(taskRepo)
    }


    //Personal Info Fields
    @BindView(R.id.setting_firstName_tx)
    lateinit var tvFirstName: TextView

    @BindView(R.id.setting_lastName_tx)
    lateinit var tvLastName: TextView

    @BindView(R.id.setting_phoneNumber_tx)
    lateinit var tvPhoneNumber: TextView

    @BindView(R.id.setting_addressOne_tx)
    lateinit var tvAddressOne: TextView

    @BindView(R.id.setting_addressTwo_tx)
    lateinit var tvAddressTwo: TextView

    @BindView(R.id.setting_country_tx)
    lateinit var tvCountry: TextView

    @BindView(R.id.setting_city_tx)
    lateinit var tvCity: TextView

    @BindView(R.id.setting_state_tx)
    lateinit var tvState: TextView

    @BindView(R.id.setting_zipCode_tx)
    lateinit var tvZipCode: TextView

    //Bank Info Fields
    @BindView(R.id.setting_currency_tx)
    lateinit var tvCurrency: TextView

    @BindView(R.id.setting_exchange_tx)
    lateinit var tvExchange: TextView

    @BindView(R.id.setting_exchange_progress)
    lateinit var exchangeProgress: ProgressBar

    @BindView(R.id.setting_exchangeMethod_tx)
    lateinit var tvExchangeMethod: TextView

    @BindView(R.id.setting_bankAccountNumber_tx)
    lateinit var tvBankAccountNumber: TextView

    @BindView(R.id.setting_bankAddress_tx)
    lateinit var tvBankAccountAddress: TextView

    @BindView(R.id.setting_bankName_tx)
    lateinit var tvBankName: TextView

    @BindView(R.id.setting_bankAccountTitle_tx)
    lateinit var tvBankTitle: TextView

    @BindView(R.id.setting_bankRut_tx)
    lateinit var tvRut: TextView


    @BindView(R.id.setting_btnSync)
    lateinit var btnSync: FloatingActionButton


    private var exchangeDialog: AlertDialog? = null
    private var exchangeMethodDialog: AlertDialog? = null

    private var userInfo: Userinfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acivity_setting)
        ButterKnife.bind(this)
        bindDataWithViews()
    }

    private fun bindDataWithViews() {

        userInfo = (application as PlingPay).auth.user.userinfo

        //Personal fields
        tvFirstName.text = userInfo?.firstName
        tvLastName.text = userInfo?.lastName
        tvPhoneNumber.text = userInfo?.phone
        tvAddressOne.text = userInfo?.address1
        tvAddressTwo.text = userInfo?.address2
        tvCity.text = userInfo?.city
        tvCountry.text = userInfo?.country
        presenter.onCountrySelected(userInfo?.country.toString(), userInfo?.country.toString())
        tvState.text = userInfo?.state
        tvZipCode.text = userInfo?.zip


        //bank fields
        tvCurrency.text = userInfo?.currancyId
        presenter.currencySelected(userInfo?.currancyId, userInfo?.curr_id)
        tvExchange.text = userInfo?.exchangeid
        tvExchangeMethod.text = userInfo?.withdrawaltypetext
        tvBankAccountNumber.text = userInfo?.bankaccountnumber
        tvBankName.text = userInfo?.bankname
        tvBankTitle.text = userInfo?.accounttilte
        tvRut.text = userInfo?.rut


    }

    @OnClick(R.id.setting_btnSync)
    fun onSyncClick() {

        presenter.syncData(tvFirstName.txt(), tvLastName.txt(), tvPhoneNumber.txt(), tvAddressOne.txt(), tvAddressTwo.txt(),
                tvCountry.txt(), tvCity.txt(), tvState.txt(), tvZipCode.txt(), tvCurrency.txt(), tvExchange.txt(),
                tvExchangeMethod.txt(), tvBankAccountNumber.txt(), tvBankAccountAddress.txt(), tvBankName.txt(),
                tvBankTitle.txt(), tvRut.txt(), userInfo?.id.toString(), userInfo)
    }


    //Personal Info Fields
    @OnClick(R.id.setting_firstName)
    fun onFirstNameClick() {
        openDialog("First Name", tvFirstName)
    }

    @OnClick(R.id.setting_lastName)
    fun onLastNameClick() {
        openDialog("Last Name", tvLastName)
    }

    @OnClick(R.id.setting_phoneNumber)
    fun onPhoneNumberClick() {
        openDialog("Phone Number", tvPhoneNumber, true)
    }

    @OnClick(R.id.setting_addressOne)
    fun onAddressClick() {
        openDialog("First Address", tvAddressOne)
    }

    @OnClick(R.id.setting_addressTwo)
    fun onAddressTwoClick() {
        openDialog("Second Address", tvAddressTwo)
    }

    @OnClick(R.id.setting_country)
    fun onCountryClick() {
        val countryDialog = SettingCountryDialog.newInstance()
        countryDialog.show(supportFragmentManager, "dialog")
    }

    @OnClick(R.id.setting_city)
    fun onCityClick() {
        openDialog("City", tvCity)
    }

    @OnClick(R.id.setting_state)
    fun onStateClick() {
        openDialog("State", tvState)
    }

    @OnClick(R.id.setting_zipCode)
    fun onZipCodeClick() {
        openDialog("Zip COde", tvZipCode)
    }


    //Bank Info Fields
    @OnClick(R.id.setting_currency)
    fun onCurrencyClick() {
        val dialog = SettingCurrencyDialog.newInstance()
        dialog.show(supportFragmentManager, "currency_dialog")
    }

    @OnClick(R.id.setting_exchange)
    fun onExchangeClick() {
        exchangeDialog?.show()
    }

    @OnClick(R.id.setting_exchangeMethod)
    fun onExchangeMethodClick() {
        exchangeMethodDialog?.show()
    }

    @OnClick(R.id.setting_bankAccountNumber)
    fun onAccountNumClick() {
        openDialog("Account Number", tvBankAccountNumber)
    }

    @OnClick(R.id.setting_bankAddress)
    fun onBankAddressClick() {
        openDialog("Bank Account Address", tvBankAccountAddress)
    }

    @OnClick(R.id.setting_bankName)
    fun onBankNameClick() {
        openDialog("Bank Name", tvBankName)
    }

    @OnClick(R.id.setting_bankAccountTitle)
    fun onBankTitleClick() {
        openDialog("Bank Account Title", tvBankTitle)
    }


    @OnClick(R.id.setting_bankRut)
    fun onRutClick() {
        openDialog("RUT", tvRut)
    }


    override fun dismissExchangeProgress() {
        if (exchangeProgress.visibility == View.VISIBLE)
            exchangeProgress.visibility = View.GONE
    }

    override fun onErrorGettingExchange() {
        Utilz.tmsgError(this, "Error")
    }

    override fun onNoExchangeFound() {
        tvExchange.text = ""
        hideField(R.id.setting_exchange)
        exchangeDialog = null
    }


    override fun onSuccessExchangeFound(exchanges: Array<CharSequence>, exchanges1: MutableList<Exchange>) {

        showField(R.id.setting_exchange)

        tvExchange.text = exchanges[0]

        exchangeDialog = setChooserDialog("Select Exchange", exchanges, onClick = { pos ->
            tvExchange.text = exchanges[pos].toString()
            presenter.onExchangeSelected(exchanges[pos].toString(), exchanges1[pos].id)
        })
    }

    override fun setExchangeMethods(exchangeMethods: Array<CharSequence>) {

        showField(R.id.setting_exchangeMethod)

        tvExchangeMethod.text = exchangeMethods[0].toString()

        exchangeMethodDialog = setChooserDialog("Select Exchange Method", exchangeMethods, onClick = { pos ->
            tvExchangeMethod.text = exchangeMethods[pos].toString()
            presenter.onExchangeMethodSelected(exchangeMethods[pos].toString())
        })

    }

    override fun removeExchange() {
        hideField(R.id.setting_exchangeMethod)
        tvExchangeMethod.text = ""
        exchangeMethodDialog = null
    }


    override fun isClpSelected(b: Boolean) {
        if (b)
            showField(R.id.setting_bankRut)
        else {
            hideField(R.id.setting_bankRut)
            tvRut.text = ""
        }
    }

    override fun onOkClick(newValue: String, id: Int) {
        val tv = findViewById(id) as TextView
        tv.text = newValue
    }

    private fun openDialog(title: String, tv: TextView, isNumber: Boolean = false) {
        val dialog = SettingFieldDialog.newInstance(title, tv.text.toString(), tv.id, isNumber)
        dialog.show(supportFragmentManager, "setting_dialog")
    }

    override fun selectedItem(curr: String?, id: String?) {
        tvCurrency.text = curr
        exchangeProgress.visibility = View.VISIBLE
        presenter.currencySelected(curr, id)
    }

    override fun dismissProgress() {
    }

    override fun onErrorSyncingData() {
        Utilz.tmsgError(this, "Error Syncing Data")
    }

    override fun onSuccessSyncingData(info: Userinfo?) {
        Utilz.tmsgSuccess(this, "Syncing Complete")
        (application as PlingPay).auth.user.userinfo = info
    }


    override fun selectedCountry(name: String, id: String) {
        tvCountry.text = name
        presenter.onCountrySelected(name, id)
    }


    private inline fun setChooserDialog(title: String, arr: Array<CharSequence>, crossinline onClick: (pos: Int) -> Unit): AlertDialog {
        return AlertDialog.Builder(this)
                .setTitle(title)
                .setItems(arr) { dialog, which ->
                    dialog.dismiss()
                    onClick(which)
                }.create()
    }

    private fun hideField(@IdRes id: Int) {
        (findViewById(id)).visibility = View.GONE
    }

    private fun showField(@IdRes id: Int) {
        (findViewById(id)).visibility = View.VISIBLE
    }

    private fun TextView.txt(): String {
        return this.text.toString()
    }


}
