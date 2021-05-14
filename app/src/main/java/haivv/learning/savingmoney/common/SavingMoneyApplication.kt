package haivv.learning.savingmoney.common

import android.app.Application
import com.facebook.FacebookSdk
import haivv.learning.savingmoney.di.DaggerSavingMoneyComponent
import haivv.learning.savingmoney.di.SavingMoneyComponent

class SavingMoneyApplication : Application() {
    val savingMoneyComponent : SavingMoneyComponent by lazy {
        initializeComponent()
    }

    private fun initializeComponent() : SavingMoneyComponent{
       return DaggerSavingMoneyComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        FacebookSdk.sdkInitialize(getApplicationContext())
    }
}