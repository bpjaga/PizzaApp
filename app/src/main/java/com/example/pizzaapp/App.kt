package com.example.pizzaapp

import android.app.Application
import com.example.pizzaapp.module.apiModule
import com.example.pizzaapp.module.repositoryModule
import com.example.pizzaapp.module.viewModelModule
import leakcanary.LeakCanary
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(
                apiModule,
                repositoryModule,
                viewModelModule
            )
        }
    }

}