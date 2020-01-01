package com.cc.database.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cc.database.R;
import com.cc.database.datasource.Word;

import java.util.List;

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ViewHolder>
{
    private List<Word> wordListList;

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        View itemView;
        TextView word;
        TextView pronounce;
        TextView def;

        public ViewHolder(View view)
        {
            super(view);
            itemView = view;
            word = (TextView) view.findViewById(R.id.textView_card_word);
            pronounce = (TextView) view.findViewById(R.id.textView_card_pronounce);
            def = (TextView) view.findViewById(R.id.textView_card_def);
        }
    }

    public CardViewAdapter(List<Word> words)
    {
        wordListList = words;
    }

    @Override
    public CardViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int i)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        final CardViewAdapter.ViewHolder holder = new CardViewAdapter.ViewHolder(view);

//        布局的点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int position = holder.getAdapterPosition();
                holder.def.setText(wordListList.get(position).getDefs());
            }
        });

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
    public void onBindViewHolder(CardViewAdapter.ViewHolder holder, int position)
    {
        Word word = wordListList.get(position);
        holder.word.setText(word.getWord());
        holder.pronounce.setText(word.getPronun());
        holder.def.setText(null);
    }

    @Override
    public int getItemCount()
    {
        return wordListList.size();
    }
}

