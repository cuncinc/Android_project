package com.cc.database.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cc.database.R;
import com.cc.database.datasource.Word;

import java.util.List;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.ViewHolder>
{
    private List<Word> wordListList;

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        View itemView;
        TextView word;
        TextView def;

        public ViewHolder(View view)
        {
            super(view);
            itemView = view;
            word = (TextView) view.findViewById(R.id.textView_item_search_word);
            def = (TextView) view.findViewById(R.id.textView_item_search_def);
        }
    }

    public SearchListAdapter(List<Word> words)
    {
        wordListList = words;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seach_list, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        //布局的点击事件
//        holder.itemView.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                int position = holder.getAdapterPosition();
//                holder.word.setText("ItemView+"+position);
//            }
//        });


        // 文本框的点击事件
//        holder.word.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                int position = holder.getAdapterPosition();
//                holder.word.setText("textView+"+position);
//            }
//        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        Word word = wordListList.get(position);
        holder.word.setText(word.getWord());
        String def = word.getDefs().replaceAll("[A-Za-z]\\.*", "");
        def.replaceAll("  ", "");
        holder.def.setText(def);
    }

    @Override
    public int getItemCount()
    {
        return wordListList.size();
    }
}

