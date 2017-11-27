package ss.plingpay.ui.activities.SettingView.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import io.realm.Realm
import ss.plingpay.R
import ss.plingpay.pojos.CurrencyPOJO.CurrencyList
import ss.plingpay.ui.adapters.CurrencyAdapter
import ss.plingpay.utilz.Listeners

/**
 * Created by Sammie on 8/22/2017.
 */
class SettingCurrencyDialog : DialogFragment() {

    @BindView(R.id.dialog_currency_picker_list)
    lateinit var list: RecyclerView

    var realm: Realm? = null
    var unbinder: Unbinder? = null
    var listener: Listeners.CurrencySelecter? = null

    companion object {
        fun newInstance(): SettingCurrencyDialog {
            return SettingCurrencyDialog()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            listener = context as Listeners.CurrencySelecter
        } catch (ex: Exception) {
            throw Exception("Implement ${SettingCurrencyDialog::javaClass.name} listener ")
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        realm = Realm.getDefaultInstance()
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater?.inflate(R.layout.dialog_currency_picker, container, false)
        unbinder = ButterKnife.bind(this, v as View)
        return v
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {

        val result = realm?.where(CurrencyList::class.java)?.findAll()
        list.layoutManager = LinearLayoutManager(context)
        list.adapter = CurrencyAdapter(result, activity as AppCompatActivity) { curr, id ->
            listener?.selectedItem(curr, id)
            dialog.dismiss()
        }

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window.requestFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onDestroy() {
        super.onDestroy()
        unbinder?.unbind()
    }

}