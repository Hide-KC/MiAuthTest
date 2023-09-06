package work.kcs_labo.miauth_test.view.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import work.kcs_labo.miauth_test.usecase.preference.PreferenceUseCase

/**
 * Created by hide1 on 2023/07/05.
 * ProjectName Wisskey
 */

class AuthActivityViewModelFactory(
  private val app: Application,
  private val preferenceUseCase: PreferenceUseCase
): ViewModelProvider.NewInstanceFactory() {

  @Suppress("UNCHECKED_CAST")
  override fun <T: ViewModel> create(modelClass: Class<T>): T {
    return AuthActivityViewModel(app, preferenceUseCase) as T
  }
}