package com.app.codehero.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.app.codehero.R
import com.app.codehero.api.Result
import com.app.codehero.domain.model.Character
import com.app.codehero.domain.usecase.CharacterDetailsUseCase
import com.app.codehero.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharacterDetailsViewModel constructor(
    private val characterDetailsUseCase: CharacterDetailsUseCase
) :
    ViewModel() {

    private val _dialogDisplay = MutableLiveData<Triple<Int, Any?, Any?>>()
    var dialogDisplay = _dialogDisplay

    private val _character = MutableLiveData<Character>()
    var character = _character

    fun getCharacterDetail(characterId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _dialogDisplay.postValue(Triple(Constants.DIALOGTYPE.PROGRESS, R.string.wait, R.string.loading_data))
            when(val result = characterDetailsUseCase.invoke(characterId)) {
                is Result.Success -> {
                    _character.postValue(result.data?.results?.first())
                    _dialogDisplay.postValue(Triple(Constants.DIALOGTYPE.DISMISS, null, null))
                }
                is Result.Failure -> {
                    _dialogDisplay.postValue(Triple(Constants.DIALOGTYPE.ERROR, "Erro ${result.statusCode}", result.exception.message))
                }
                else -> {
                    _dialogDisplay.postValue(Triple(Constants.DIALOGTYPE.ERROR, R.string.attention, R.string.connection_server_failed))
                }
            }
        }
    }


    class ViewModelFactory(private val characterDetailsUseCase: CharacterDetailsUseCase):
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(CharacterDetailsViewModel::class.java)) {
                return CharacterDetailsViewModel(characterDetailsUseCase) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}