package dev.eighteentech.test

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.eighteentech.test.databinding.ItemSimbolsBinding
import okhttp3.internal.toLongOrDefault
import java.util.*

private const val TAG = "SymbolsAdapter"

class SymbolsAdapter(
    private val list: List<Pair<String, String>>,
    private val listener: CurrencySelectedListener
) : RecyclerView.Adapter<SymbolsAdapter.ViewHolder>() {

    var mutableList = list as MutableList

    inner class ViewHolder(val binding: ItemSimbolsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SymbolsAdapter.ViewHolder {
        val binding = ItemSimbolsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SymbolsAdapter.ViewHolder, position: Int) =
        with(holder.binding) {
            simbolStatus.text = mutableList[position].first
            countryStatus.text = mutableList[position].second
            root.setOnClickListener {
                listener.onCurrencySelected(mutableList[position].first)
            }
        }

    override fun getItemCount(): Int = mutableList.size

    fun filter(filter: String) {
        mutableList = list.filter {
            it.first.lowercase(Locale.getDefault()).contains(
                filter.lowercase(
                    Locale.getDefault()
                )
            )
        } as MutableList
        notifyDataSetChanged()
    }


}

interface CurrencySelectedListener {
    fun onCurrencySelected(currency: String)
}