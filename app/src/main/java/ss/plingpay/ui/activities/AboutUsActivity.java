package ss.plingpay.ui.activities;

        import android.annotation.SuppressLint;
        import android.content.Intent;
        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.view.View;
        import android.webkit.WebResourceError;
        import android.webkit.WebResourceRequest;
        import android.webkit.WebResourceResponse;
        import android.webkit.WebView;
        import android.webkit.WebViewClient;
        import android.widget.ProgressBar;

        import ss.plingpay.R;
        import ss.plingpay.ui.activities.mainView.MainActivity;

/**
 * Created by samar_000 on 8/24/2016.
 */

public class AboutUsActivity extends BaseActivity {

    WebView webView;
    View progressFrame, showError;
    ProgressBar progressBar;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
//        getAcBar().setmTitle("FAQs");


        toolbar.setNavigationIcon(R.drawable.ic_cancel);
        toolbar.setNavigationOnClickListener(v -> {

                    Intent intent = new Intent(AboutUsActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                });


        progressFrame = findViewById(R.id.activity_faq_progressFrame);
        showError = findViewById(R.id.activity_faq_error);
        webView = (WebView) findViewById(R.id.activity_faq_WebView);
        progressBar = (ProgressBar) findViewById(R.id.activity_faq_progressBar);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://bit4m.org");
        progressBar.setProgress(webView.getProgress());

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressFrame.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                progressFrame.setVisibility(View.GONE);

            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
                progressFrame.setVisibility(View.GONE);
            }
        });


    }
}