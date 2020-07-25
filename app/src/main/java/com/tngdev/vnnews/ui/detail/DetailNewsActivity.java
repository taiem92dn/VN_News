package com.tngdev.vnnews.ui.detail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.webkit.WebSettingsCompat;
import androidx.webkit.WebViewFeature;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tngdev.vnnews.PreferencesManager;
import com.tngdev.vnnews.R;
import com.tngdev.vnnews.databinding.ActivityDetailNewsBinding;
import com.tngdev.vnnews.util.Utils;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DetailNewsActivity extends AppCompatActivity {

    ActivityDetailNewsBinding mBinding;

    @Inject
    PreferencesManager preferencesManager;

    private static final String KEY_URL = "URL";

    public static void start(Context context, String url) {
        Intent intent = new Intent(context, DetailNewsActivity.class);
        intent.putExtra(KEY_URL, url);

        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityDetailNewsBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mBinding.wvNews.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                DetailNewsActivity.this.setTitle(view.getTitle());
            }
        });
        String url = getIntent().getStringExtra(KEY_URL);
        if (!TextUtils.isEmpty(url)) {
            mBinding.wvNews.loadUrl(url);
        }

        if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK))
            WebSettingsCompat.setForceDark(mBinding.wvNews.getSettings(),
                    preferencesManager.isDarkMode() ?
                            WebSettingsCompat.FORCE_DARK_ON
                    : WebSettingsCompat.FORCE_DARK_OFF
            );
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mBinding.wvNews.canGoBack()) {
            mBinding.wvNews.goBack();
        } else {
            super.onBackPressed();
        }
    }

//    private void injectCSS() {
//
//        String code = "javascript:(function() {" +
//                "var node = document.createElement('style');"+
//                "node.type = 'text/css';"+
//                " node.innerHTML = 'body, label,th,p,a, td, tr,li,ul,span,table,h1,h2,h3,h4,h5,h6,h7,div,small {"+
//                "     color: #deFFFFFF;"+
//                "background-color: #232323;"+
//                " } ';"+
//                " document.head.appendChild(node);})();";
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            mBinding.wvNews.evaluateJavascript(code,null);
//        }
//
//    }
}