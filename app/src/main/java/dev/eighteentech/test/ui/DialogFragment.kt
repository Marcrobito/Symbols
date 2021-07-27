package dev.eighteentech.test.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.eighteentech.test.R
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import dev.eighteentech.test.databinding.FragmentDialogBinding
import dev.eighteentech.test.model.UserIntent
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

@InternalCoroutinesApi
class DialogFragment(private val errorDialog:String) : DialogFragment() {
    lateinit var binding: FragmentDialogBinding
    private val viewModel by activityViewModels<MainViewModel>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentDialogBinding.inflate(inflater,container,false)
        with(binding){
            errorDialog.text= this@DialogFragment.errorDialog
            aceptDialog.setOnClickListener{
                dismiss()
                lifecycleScope.launch {
                    viewModel.userIntent.send(UserIntent.LoadMainScreen)
                }
            }
        }


        return binding.root
    }

}