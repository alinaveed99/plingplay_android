package ss.plingpay

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.webkit.*
import android.widget.ProgressBar

/**
 * Created by Sammie on 10/19/2017.
 */
class CustomWebViewActivity : AppCompatActivity() {

    lateinit var wv: WebView
    lateinit var progressBar: ProgressBar
    lateinit var toolbar: Toolbar

    private val URL = "url"
    private val JOB = "job"

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        wv = findViewById(R.id.webview) as WebView
        progressBar = findViewById(R.id.pb) as ProgressBar
        toolbar = findViewById(R.id.app_toolbar) as Toolbar

        val url = intent.extras.getString(URL)
        val jobName = "Registration"

        setSupportActionBar(toolbar)
        title = jobName

        toolbar.setNavigationIcon(R.drawable.ic_cancel)
        toolbar.setNavigationOnClickListener({
            finish()
        })

        wv.setWebViewClient(WebViewClient())
        wv.settings.javaScriptEnabled = true
        wv.loadUrl(url)

        progressBar.visibility = View.VISIBLE

        wv.setWebViewClient(object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                progressBar.visibility = View.GONE
            }

            override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
                super.onReceivedError(view, request, error)
                progressBar.visibility = View.GONE
            }

            override fun onReceivedHttpError(view: WebView, request: WebResourceRequest, errorResponse: WebResourceResponse) {
                super.onReceivedHttpError(view, request, errorResponse)
                progressBar.visibility = View.GONE
            }
        })


    }

    override fun onBackPressed() {
        if (wv.canGoBack()) {
            wv.goBack()
        } else {
            super.onBackPressed()
        }
    }

}