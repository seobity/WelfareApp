package com.example.finalproject.localmemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.R

class MemoAdapter(private var memoList: List<Memo>) :
    RecyclerView.Adapter<MemoAdapter.MemoViewHolder>() {

    class MemoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.memoTitle)
        val content: TextView = view.findViewById(R.id.memoContent)
        val date: TextView = view.findViewById(R.id.memoDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_memo, parent, false)
        return MemoViewHolder(view)
    }

    override fun onBindViewHolder(holder: MemoViewHolder, position: Int) {
        val memo = memoList[position]
        holder.title.text = memo.title
        holder.content.text = memo.content
        holder.date.text = memo.date
    }

    override fun getItemCount() = memoList.size

    fun updateData(newList: List<Memo>) {
        memoList = newList
        notifyDataSetChanged()
    }
}