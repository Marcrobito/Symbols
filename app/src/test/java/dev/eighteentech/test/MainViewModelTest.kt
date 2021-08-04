package dev.eighteentech.test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dev.eighteentech.test.model.Response
import dev.eighteentech.test.model.State.ConverterState
import dev.eighteentech.test.model.State.MainScreenState
import dev.eighteentech.test.model.UserIntent
import dev.eighteentech.test.ui.MainViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import kotlin.coroutines.ContinuationInterceptor


@InternalCoroutinesApi
class MainViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var mainViewModel:MainViewModel

    @ExperimentalCoroutinesApi
    @Before
    fun setup(){
        mainViewModel = MainViewModel(RepositoryMock())
    }


    @ExperimentalCoroutinesApi
    @Test
    fun validateMainState() = runBlocking {
        val expectedState =  MainScreenState(Response.Loading)
        assertEquals( expectedState, mainViewModel.state.value)
        mainViewModel.userIntent.send(UserIntent.LoadMainScreen)
        assertEquals( MainScreenState(RepositoryMock.successSymbolsResponse), mainViewModel.state.value)
        mainViewModel.userIntent.send(UserIntent.GetCurrencyRates("EUR"))
        Response.Success(listOf(Pair("USD",1.0)))
        assertEquals(ConverterState(Response.Success(listOf(Pair("USD", 1.0)))), mainViewModel.state.value)
    }
}


@ExperimentalCoroutinesApi
class MainCoroutineRule : TestWatcher(), TestCoroutineScope by TestCoroutineScope() {

    override fun starting(description: Description) {
        super.starting(description)
        Dispatchers.setMain(this.coroutineContext[ContinuationInterceptor] as CoroutineDispatcher)
    }

    override fun finished(description: Description) {
        super.finished(description)
        Dispatchers.resetMain()
    }

}