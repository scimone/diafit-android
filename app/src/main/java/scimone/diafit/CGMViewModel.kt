package scimone.diafit.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import scimone.diafit.db.CGMEntity
import scimone.diafit.db.CGMRepository
import scimone.diafit.DiafitApplication

class CGMViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CGMRepository = CGMRepository(DiafitApplication.db)
    val latestCGMValue: LiveData<CGMEntity> = repository.getLatestCGM()

    fun insertCGMValue(cgmValue: CGMEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertCGMValue(cgmValue)
        }
    }
}
