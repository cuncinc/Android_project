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

import static com.cc.database.activity.MainActivity.dbHelper;

public class ReaWordAdapter extends RecyclerView.Adapter<ReaWordAdapter.ViewHolder>
{
    private List<Word> wordsList;

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        View itemView;
        TextView textView_word;
        Button favoriteButton;
        Button deleteButton;

        public ViewHolder(View view)
        {
            super(view);
            itemView = view;
            textView_word = (TextView) view.findViewById(R.id.textView_word);
//            favoriteButton = (Button) view.findViewById(R.id.btnFavarite);
            deleteButton = (Button) view.findViewById(R.id.btnDelete);
        }
    }

    public ReaWordAdapter(List<Word> words)
    {
        wordsList = words;
    }

    public ReaWordAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int i)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_xml, parent, false);
        final ReaWordAdapter.ViewHolder holder = new ReaWordAdapter.ViewHolder(view);

        //布局的点击事件
//        holder.itemView.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                int position = holder.getAdapterPosition();
////                WallPaper wallPaper = wallPaperList.get(position);
//                holder.textView.setText("ItemView+"+position);
//            }
//        });

        // 文本框的点击事件
//        holder.favoriteButton.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
////                int position = holder.getAdapterPosition();
//            }
//        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int position = holder.getAdapterPosition();
                String headWord = wordsList.get(position).getWord();
                wordsList.remove(position);
                notifyDataSetChanged();
                String command_update = "UPDATE WordList SET IncorrectCount = 0 WHERE HeadWord = \"" + headWord + "\"";
                dbHelper.updateDatabase(command_update);
            }
        });
        return holder;
    }

    public void onBindViewHolder(ViewHolder holder, int position)
    {
        Word word = wordsList.get(position);
        holder.textView_word.setText(word.getWord());
    }

    public int getItemCount()
    {
        return wordsList.size();
    }
}
