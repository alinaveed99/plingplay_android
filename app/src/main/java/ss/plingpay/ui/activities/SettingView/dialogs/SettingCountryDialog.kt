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
import ss.plingpay.pojos.CountriesList
import ss.plingpay.ui.adapters.CountryAdapter

/**
 * Created by Sammie on 8/23/2017.
 */
class SettingCountryDialog : DialogFragment() {

    @BindView(R.id.dialog_country_picker_list)
    lateinit var list: RecyclerView

    var realm: Realm? = null
    var unbinder: Unbinder? = null
    var listener: CountryDialogListener? = null

    companion object {
        fun newInstance(): SettingCountryDialog {
            return SettingCountryDialog()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            listener = context as CountryDialogListener
        } catch (ex: Exception) {
            throw Exception("Implement ${CountryDialogListener::javaClass.name} listener ")
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        realm = Realm.getDefaultInstance()
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater?.inflate(R.layout.dialog_country_picker, container, false)
        unbinder = ButterKnife.bind(this, v as View)
        return v
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {

        val result = realm?.where(CountriesList::class.java)?.findAll()
        list.layoutManager = LinearLayoutManager(context)
        list.adapter = CountryAdapter(result) { name, id ->
            listener?.selectedCountry(name, id)
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


    interface CountryDialogListener {
        fun selectedCountry(name: String, id: String)
    }

}