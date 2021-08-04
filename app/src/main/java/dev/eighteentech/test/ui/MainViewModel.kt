package dev.eighteentech.test.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.eighteentech.test.model.Response
import dev.eighteentech.test.model.State
import dev.eighteentech.test.model.State.ConverterState
import dev.eighteentech.test.model.State.MainScreenState
import dev.eighteentech.test.model.UserIntent
import dev.eighteentech.test.model.UserIntent.GetCurrencyRates
import dev.eighteentech.test.model.UserIntent.LoadMainScreen
import dev.eighteentech.test.repository.Repository
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

private const val TAG = "MainViewModel"

@InternalCoroutinesApi
class MainViewModel(private val repository: Repository) : ViewModel() {
    private val _state = MutableStateFlow<State>(MainScreenState(Response.Uninitialized))
    val state: StateFlow<State> get() = _state
    val userIntent = Channel<UserIntent>(Channel.UNLIMITED)

    private var symbols = mutableListOf<Pair<String,String>>()

    init {
        handleIntent()
    }

    private fun handleIntent() {
        _state.value = MainScreenState(Response.Loading)
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect(object : FlowCollector<UserIntent> {
                override suspend fun emit(intent: UserIntent) {
                    when(intent){
                        is LoadMainScreen -> loadMainScreen()
                        is GetCurrencyRates -> getCurrencyRates(intent.currency)
                    }
                }
            })
        }
    }

    suspend fun getCurrencyRates(currency: String){
        _state.value = MainScreenState(Response.Loading)
        _state.value = ConverterState(repository.fetchLatestRatesForSymbol(currency))
    }

    suspend fun loadMainScreen(){
        _state.value = MainScreenState(Response.Loading)
        if(symbols.isNotEmpty()){
            _state.value = MainScreenState(Response.Success(symbols))
            return
        }

        val result = repository.fetchSymbols()
        if(result is Response.Success){
            symbols = result.data as MutableList<Pair<String, String>>
        }
        _state.value = MainScreenState(result)
    }

}