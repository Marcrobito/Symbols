package dev.eighteentech.test.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dev.eighteentech.test.ConvertRatesAdapter
import dev.eighteentech.test.databinding.FragmentConvertRatesBinding
import dev.eighteentech.test.model.Response
import dev.eighteentech.test.model.State
import dev.eighteentech.test.model.UserIntent
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

private const val TAG = "ConvertRatesFragment"


@InternalCoroutinesApi
class ConvertRatesFragment : Fragment(), TextWatcher {


    private val viewModel by activityViewModels<MainViewModel>()
    private lateinit var binding: FragmentConvertRatesBinding
    private lateinit var adapter : ConvertRatesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConvertRatesBinding.inflate(inflater, container, false)
        with(binding) {
            unitsToConvert.addTextChangedListener(this@ConvertRatesFragment)
            Log.d(TAG,"unitsToConvert.text.toString()")
            observeViewModel()
            return root
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    override fun afterTextChanged(s: Editable?) {
        lifecycleScope.launch {
            s?.let {
                var units=it.toString()
                if(this@ConvertRatesFragment::adapter.isInitialized && units != ""){
                    Log.d(TAG, "douh")
                    Log.d(TAG, units)
                    Log.d(TAG, units.toDouble().toString())
                    adapter.currencyConvert(units.toDouble())
                }

            }
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect(object : FlowCollector<State> {
                override suspend fun emit(state: State) {
                    Log.d(TAG, state.toString())
                    if (state is State.ConverterState && state.response is Response.Success) {
                        Log.d(TAG, state.toString())
                        adapter = ConvertRatesAdapter(state.response.data)
                        binding.convrtRatesRecyclerView.adapter = adapter
                        val manager =
                            LinearLayoutManager(
                                requireContext(),
                                LinearLayoutManager.VERTICAL,
                                false
                            )
                        binding.convrtRatesRecyclerView.layoutManager = manager
                    }
                }
            })
        }
    }
}