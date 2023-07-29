package com.example.pizzaapp.module

import com.example.pizzaapp.api.ApiService
import com.example.pizzaapp.api.ApiServiceMaker
import com.example.pizzaapp.repository.AppRepository
import com.example.pizzaapp.repository.Repository
import com.example.pizzaapp.ui.activity.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val apiModule = module {
    single { ApiServiceMaker.invoke() }
}

val repositoryModule = module {
    fun provideRepository(apiService: ApiService): Repository {
        return AppRepository(apiService)
    }

    single {
        provideRepository(get())
    }
}

val viewModelModule = module {
    viewModel {
        MainViewModel(get())
    }
}