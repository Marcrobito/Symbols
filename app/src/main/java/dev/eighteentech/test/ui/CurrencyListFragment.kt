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
import dev.eighteentech.test.CurrencySelectedListener
import dev.eighteentech.test.SymbolsAdapter
import dev.eighteentech.test.databinding.FragmentCurrencyListBinding
import dev.eighteentech.test.model.Response
import dev.eighteentech.test.model.State
import dev.eighteentech.test.model.UserIntent
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch


private const val TAG = "CurrencyListFragment"

@InternalCoroutinesApi
class CurrencyListFragment : Fragment(), TextWatcher, CurrencySelectedListener {

    private val viewModel by activityViewModels<MainViewModel>()
    private lateinit var binding: FragmentCurrencyListBinding
    private lateinit var adapter: SymbolsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCurrencyListBinding.inflate(inflater, container, false)
        with(binding) {
            observeViewModel()
            filterSymbols.addTextChangedListener(this@CurrencyListFragment)
            return root
        }

    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect(object : FlowCollector<State> {
                override suspend fun emit(state: State) {
                    if (state is State.MainScreenState && state.response is Response.Success) {
                        Log.d(TAG, state.toString())
                        adapter = SymbolsAdapter(state.response.data, this@CurrencyListFragment)
                        binding.symbolsRecyclerView.adapter = adapter
                        val manager =
                            LinearLayoutManager(
                                requireContext(),
                                LinearLayoutManager.VERTICAL,
                                false
                            )


                        binding.symbolsRecyclerView.layoutManager = manager
                    }
                }
            })
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

    override fun afterTextChanged(s: Editable?) {
        if (this::adapter.isInitialized)
            s?.let {
                adapter.filter(s.toString())
            }

    }

    override fun onCurrencySelected(currency: String) {
        lifecycleScope.launch {
            viewModel.userIntent.send(UserIntent.GetCurrencyRates(currency))
        }
    }


}