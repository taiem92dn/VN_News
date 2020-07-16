package com.tngdev.vnnews.ui.detail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tngdev.vnnews.R;
import com.tngdev.vnnews.databinding.ActivityDetailNewsBinding;

public class DetailNewsActivity extends AppCompatActivity {

    ActivityDetailNewsBinding mBinding;

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
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}