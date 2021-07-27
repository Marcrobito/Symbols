package dev.eighteentech.test

import dev.eighteentech.test.network.Network
import dev.eighteentech.test.repository.Repository
import dev.eighteentech.test.repository.RepositoryImpl
import dev.eighteentech.test.ui.MainViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val networkModule = module {
    factory { Network.service }
}

val repositoryModule = module {
    factory<Repository> { RepositoryImpl(get())  }
}

@InternalCoroutinesApi
val appModule = module {
    viewModel{ MainViewModel(get())}
}