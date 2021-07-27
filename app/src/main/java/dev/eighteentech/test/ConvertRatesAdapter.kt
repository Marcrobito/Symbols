package dev.eighteentech.test

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.eighteentech.test.databinding.ItemRatesBinding
import dev.eighteentech.test.databinding.ItemSimbolsBinding


private const val TAG = "ConvertRatesAdapter"

class ConvertRatesAdapter(
    private val list: List<Pair<String, Double>>
) : RecyclerView.Adapter<ConvertRatesAdapter.ViewHolder>() {

    private var mutableList:MutableList<Pair<String, Double>> = list as MutableList<Pair<String, Double>>


    inner class ViewHolder(val binding: ItemRatesBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ConvertRatesAdapter.ViewHolder {
        val binding = ItemRatesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ConvertRatesAdapter.ViewHolder, position: Int) =
        with(holder.binding) {
            simbolStatus.text = mutableList[position].first
            currencyStatus.text =
                "$${"%.2f".format(mutableList[position].second)}"
        }

    fun currencyConvert(amount:Double){
        Log.d(TAG, "currencyConvert")
        mutableList = list.map { Pair(it.first, it.second * amount)} as MutableList<Pair<String, Double>>
        Log.d(TAG, mutableList.toString())
        notifyDataSetChanged()
    }

    override fun getItemCount() = list.size
}

