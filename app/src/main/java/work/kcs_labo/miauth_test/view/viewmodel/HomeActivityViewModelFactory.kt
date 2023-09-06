package work.kcs_labo.miauth_test.view.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Created by hide1 on 2023/06/28.
 * ProjectName Wisskey
 */

class HomeActivityViewModelFactory(
  private val app: Application
): ViewModelProvider.NewInstanceFactory() {

  @Suppress("UNCHECKED_CAST")
  override fun <T: ViewModel> create(modelClass: Class<T>): T {
    return HomeActivityViewModel(app) as T
  }
}