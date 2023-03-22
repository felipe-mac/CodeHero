package com.app.codehero.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.app.codehero.api.Result
import com.app.codehero.domain.model.Character
import com.app.codehero.domain.usecase.CharacterDetailsUseCase
import com.app.codehero.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharacterDetailsViewModel @Inject constructor(
    private val characterDetailsUseCase: CharacterDetailsUseCase
) :
    ViewModel() {

    private val _dialogDisplay = MutableLiveData<Triple<Int, String?, String?>>()
    var dialogDisplay = _dialogDisplay

    private val _character = MutableLiveData<Character>()
    var character = _character

    fun getCharacterDetail(characterId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _dialogDisplay.postValue(Triple(Constants.DIALOGTYPE.PROGRESS, "Aguarde", "Carregando dados"))
            when(val result = characterDetailsUseCase.invoke(characterId)) {
                is Result.Success -> {
                    Log.d("FMS", "getCharacterDetail success")
                    _character.postValue(result.data?.results?.first())
                }
                is Result.Failure -> {
                    Log.d("FMS","getCharacterDetail failure getCharacters: ${result.statusCode}")
                    _dialogDisplay.postValue(Triple(Constants.DIALOGTYPE.ERROR, "Erro ${result.statusCode}", result.exception.message))
                }
                else -> {
                    Log.d("FMS","getCharacterDetail server error getCharacters")
                    _dialogDisplay.postValue(Triple(Constants.DIALOGTYPE.ERROR, "Atenção", "Não foi possível se conectar ao servidor."))
                }
            }
            _dialogDisplay.postValue(Triple(Constants.DIALOGTYPE.DISMISS, null, null))
        }
    }


    /*class ViewModelFactory(private val characterDetailsUseCase: CharacterDetailsUseCase):
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(CharacterDetailsViewModel::class.java)) {
                return CharacterDetailsViewModel(characterDetailsUseCase) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }*/
}