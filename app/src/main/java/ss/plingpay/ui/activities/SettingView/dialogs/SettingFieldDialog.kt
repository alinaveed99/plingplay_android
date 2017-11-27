package ss.plingpay.ui.activities.SettingView.dialogs

import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.Unbinder
import ss.plingpay.R

/**
 * Created by Sammie on 8/21/2017.
 */
class SettingFieldDialog : DialogFragment() {

    var listener: Listeners? = null
    var unbind: Unbinder? = null

    private var title = ""
    private var summary = ""
    private var _id = 0
    private var isNumber = false


    @BindView(R.id.setting_dialog_summary)
    lateinit var etSummary: EditText


    override fun onAttach(context: Context?) {
        super.onAttach(context)

        try {
            listener = context as Listeners
        } catch (ex: Exception) {
            throw Exception("Must Implement Exception")
        }

    }

    companion object {

        private val TITLE = "title"
        private val SUMMARY = "summary"
        private val ID = "id"
        private val IS_NUMBER = "isnumber"

        fun newInstance(title: String, summary: String, id: Int, isNumber: Boolean): SettingFieldDialog {
            val fragment = SettingFieldDialog()
            val args = Bundle()

            args.putString(TITLE, title)
            args.putString(SUMMARY, summary)
            args.putInt(ID, id)
            args.putBoolean(IS_NUMBER, isNumber)

            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = arguments.getString(TITLE)
        summary = arguments.getString(SUMMARY)
        _id = arguments.getInt(ID)
        isNumber = arguments.getBoolean(IS_NUMBER)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater?.inflate(R.layout.setting_dialog, container, false)
        ButterKnife.bind(this, v as View)
        return v
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog.setTitle(title)

        if (isNumber)
            etSummary.inputType = InputType.TYPE_CLASS_PHONE
        else
            etSummary.inputType = InputType.TYPE_CLASS_TEXT



        etSummary.setText(summary)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbind?.unbind()
    }

    @OnClick(R.id.setting_dialog_btnOk)
    fun onOkClick() {
        listener?.onOkClick(etSummary.text.toString(), _id)
        dialog.dismiss()
    }

    @OnClick(R.id.setting_dialog_btnCancel)
    fun onCancelClick() {
        dialog.dismiss()
    }

    interface Listeners {
        fun onOkClick(newValue: String, id: Int)
    }
}