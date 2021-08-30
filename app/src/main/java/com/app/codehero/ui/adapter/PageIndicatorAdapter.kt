package com.app.codehero.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.codehero.R
import com.app.codehero.databinding.ItemPageIndicatorBinding
import org.w3c.dom.Text

class PageIndicatorAdapter(
    private var dataSet: List<String>,
    private var page: Int,
    private val onClick: (View) -> Unit
) :
    RecyclerView.Adapter<PageIndicatorAdapter.ViewHolder>() {

    private var currentPageView: View? = null

    class ViewHolder(private val binding: ItemPageIndicatorBinding,
                     onClick: (View) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onClick(binding.root)
            }
        }

        fun bind(page: String) {
            binding.root.text = page
        }
    }

    fun updatePage(newPage: Int) {
        page = newPage
    }

    fun selectPage(view: View, select: Boolean) {
        Log.d("FMS","selectPage: ${(view as TextView).text} $select")
        val textView = view as TextView
        textView.isSelected = select
        textView.setTextColor(ContextCompat.getColor(view.context, if(select) R.color.white else R.color.bg_character_search_name))
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemPageIndicatorBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            ), onClick)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(dataSet[position])
        if(page == position) {
            currentPageView = viewHolder.itemView
            selectPage(currentPageView!!, true)
        } else {
            selectPage(viewHolder.itemView, false)
        }
    }

    override fun getItemCount() = dataSet.size
}
