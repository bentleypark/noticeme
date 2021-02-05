package com.project.noticeme.ui.search.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.noticeme.data.room.SearchHistoryEntity
import com.project.noticeme.databinding.SearchHistoryItemBinding
import com.project.noticeme.ui.search.SearchViewModel

class SearchHistoryAdapter(
    private val list: MutableList<SearchHistoryEntity>,
    private val viewModel: SearchViewModel
) :
    RecyclerView.Adapter<SearchHistoryAdapter.SearchHistoryViewHolder>() {

    private lateinit var bindingItem: SearchHistoryItemBinding

    inner class SearchHistoryViewHolder(private val binding: SearchHistoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SearchHistoryEntity, position: Int) {
            binding.apply {
                tvDate.text = item.date
                binding.tvTitle.text = item.keyword
                btnDelete.setOnClickListener {
                    delete(position)
                }
                selectedItem.setOnClickListener {
                    viewModel.updateHistoryTitle(item.keyword)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHistoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        bindingItem = SearchHistoryItemBinding.inflate(layoutInflater)
        return SearchHistoryViewHolder(bindingItem)
    }

    override fun onBindViewHolder(holder: SearchHistoryViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item, position)
    }

    override fun getItemCount() = list.size

    fun add(item: SearchHistoryEntity) {
        list.add(0, item)
        notifyItemInserted(0)
    }

    fun addAll(data: List<SearchHistoryEntity>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    fun clear() {
        viewModel.deleteAllHistory()
        list.clear()
        notifyDataSetChanged()
    }

    fun delete(position: Int) {
        viewModel.deleteHistory(list[position])
        list.removeAt(position)
        notifyDataSetChanged()
    }
}