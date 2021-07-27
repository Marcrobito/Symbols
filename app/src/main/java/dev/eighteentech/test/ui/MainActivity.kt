package dev.eighteentech.test.ui

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import dev.eighteentech.test.R
import dev.eighteentech.test.databinding.ActivityMainBinding
import dev.eighteentech.test.model.Response
import dev.eighteentech.test.model.State
import dev.eighteentech.test.model.UserIntent
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.Thread.sleep

private const val TAG = "MainActivity"
@InternalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<MainViewModel>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycleScope.launch {
            viewModel.userIntent.send(UserIntent.LoadMainScreen)
        }
        observeViewModel()

    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect(object : FlowCollector<State> {
                override suspend fun emit(state: State) {
                    Log.d(TAG, state.toString())
                    val route = when(state){
                        is State.MainScreenState -> R.id.currencyListFragment
                        is State.ConverterState -> R.id.convertRatesFragment
                    }
                    val navHostFragment =
                        supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
                    val navController = navHostFragment.navController
                    navController.navigate(route)


                    manageResponseStatus(state.response)
                }
            })
        }
    }

    fun manageResponseStatus(response: Response<Any>){
        when(response){
            is Response.Uninitialized,
            is Response.Success -> binding.progressIndicator.visibility = View.GONE
            is Response.Loading -> binding.progressIndicator.visibility = View.VISIBLE
            is Response.Fail -> {
                binding.progressIndicator.visibility = View.GONE
                var dialogFragment:DialogFragment
                dialogFragment= DialogFragment(response.exception.message.toString())
                dialogFragment.show(supportFragmentManager,"Error")
                //Toast.makeText(this, response.exception.message?:getString(R.string.default_error),Toast.LENGTH_LONG).show()
            }
        }
    }
}