package com.tngdev.vnnews.ui.main;

import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tngdev.vnnews.databinding.ItemNewsBinding;
import com.tngdev.vnnews.model.NewsItem;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsVH> {

    private List<NewsItem> data = null;

    private OnItemClickListener onItemClickListener;

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public NewsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNewsBinding binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new NewsVH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsVH holder, int position) {
        NewsItem item = data.get(position);
        if (item == null) return;

        holder.binding.getRoot().setOnClickListener(v -> {
            if (onItemClickListener != null)
                onItemClickListener.onItemClick(position, getData().get(position));
        });

        if (!TextUtils.isEmpty(item.getImage())) {
            holder.binding.tvNewsNoImgTitle.setVisibility(View.GONE);
            Glide.with(holder.itemView)
                    .load(item.getImage())
                    .into(holder.binding.ivNews);

            holder.binding.tvNewsTitle.setText(item.getTitle());
        }
        else {
            holder.binding.tvNewsNoImgTitle.setVisibility(View.VISIBLE);
            holder.binding.ivNews.setVisibility(View.GONE);

            holder.binding.tvNewsNoImgTitle.setText(item.getTitle());
            holder.binding.tvNewsTitle.setText(Html.fromHtml(item.getDescription()));
        }
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    public List<NewsItem> getData() {
        return data;
    }

    public void setData(List<NewsItem> data) {
        this.data = data;
    }

    static class NewsVH extends RecyclerView.ViewHolder {

        ItemNewsBinding binding;

        public NewsVH(ItemNewsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    static interface OnItemClickListener {
        void onItemClick(int pos, NewsItem item);
    }
}
