package com.tngdev.vnnews.ui.main;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.tngdev.vnnews.R;
import com.tngdev.vnnews.databinding.MainFragmentBinding;
import com.tngdev.vnnews.model.NewsItem;
import com.tngdev.vnnews.network.ApiResource;
import com.tngdev.vnnews.ui.detail.DetailNewsActivity;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainFragment extends Fragment {

    private MainViewModel mViewModel;

    private MainFragmentBinding mBinding;

    private Snackbar noInternetSnackbar;

    private NewsAdapter newsAdapter;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mBinding = MainFragmentBinding.inflate(inflater, container, false);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(mBinding.rvNews.getContext(), DividerItemDecoration.VERTICAL);
        Drawable drawable = ContextCompat.getDrawable(requireContext(), R.drawable.item_divider);
        if (drawable != null) {
            itemDecoration.setDrawable(drawable);
        }
        mBinding.rvNews.addItemDecoration(itemDecoration);

        newsAdapter = new NewsAdapter();
        mBinding.rvNews.setAdapter(newsAdapter);
        newsAdapter.setOnItemClickListener((pos, item) -> DetailNewsActivity.start(requireActivity(), item.getLink()));

        mViewModel.getNews().observe(getViewLifecycleOwner(), new Observer<ApiResource<LiveData<List<NewsItem>>>>() {
            @Override
            public void onChanged(ApiResource<LiveData<List<NewsItem>>> liveDataResource) {
                if (liveDataResource instanceof ApiResource.Success) {
                    hideLoading();
                    hideNoInternet();
                    showData(liveDataResource.getData());
                }
                else if (liveDataResource instanceof ApiResource.Error) {
                    showData(liveDataResource.getData());
                    hideLoading();
                    hideNoInternet();
                    showError(liveDataResource.getMessage());
                }
                else if (liveDataResource instanceof ApiResource.NoInternet) {
                    hideLoading();
                    showNoInternet();
                    showData(liveDataResource.getData());
                }
                else if (liveDataResource instanceof ApiResource.Loading) {
                    showLoading();
                }

            }
        });

        mBinding.srlNews.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mViewModel.refreshData();
            }
        });
    }

    private void showData(LiveData<List<NewsItem>> data) {
        if (data != null) {
            data.observe(getViewLifecycleOwner(), newsItems -> {
                if (newsItems != null) {
                    newsAdapter.setData(newsItems);
                    newsAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    private void showLoading() {
        mBinding.srlNews.setRefreshing(true);
    }

    private void hideLoading() {
        mBinding.srlNews.setRefreshing(false);
    }

    private void showError(CharSequence message) {
        Snackbar.make(mBinding.getRoot(), message, BaseTransientBottomBar.LENGTH_INDEFINITE).show();
    }

    private void showNoInternet() {
        if (noInternetSnackbar == null) {
            noInternetSnackbar = Snackbar.make(mBinding.getRoot(), getString(R.string.no_internet_message), Snackbar.LENGTH_INDEFINITE);
        }

        noInternetSnackbar.show();
    }

    private void hideNoInternet() {
        if (noInternetSnackbar != null)
            noInternetSnackbar.dismiss();
    }
}