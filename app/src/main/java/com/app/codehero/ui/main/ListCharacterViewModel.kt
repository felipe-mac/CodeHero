package com.app.codehero.ui.main

import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.app.codehero.R
import com.app.codehero.api.Result
import com.app.codehero.domain.model.Character
import com.app.codehero.domain.usecase.ListCharactersUseCase
import com.app.codehero.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.ceil

class ListCharacterViewModel constructor(
    private val listCharactersUseCase: ListCharactersUseCase
) :
    ViewModel() {

    private val initialPage: Int = 0
    private var currentPage: Int = initialPage
    private val itemsPerPage: Int = 4
    private var currentName: String = ""

    private val _dialogDisplay = MutableLiveData<Triple<Int, Any?, Any?>>()
    var dialogDisplay = _dialogDisplay

    private val _pageIndicator = MutableLiveData<Int>()
    var pageIndicator = _pageIndicator

    private val _pageSelected = MutableLiveData<Int>()
    var pageSelected = _pageSelected

    private val _characterList = MutableLiveData<List<Character>>()
    var characterList = _characterList

    fun getCharacters(name: String = "", page: Int=initialPage) {
        currentPage = page*itemsPerPage //ajuste do offset
        currentName = name
        viewModelScope.launch(Dispatchers.IO) {
            _dialogDisplay.postValue(Triple(Constants.DIALOGTYPE.PROGRESS, null, R.string.loading_data))
            when(val result = listCharactersUseCase.invoke(currentName, currentPage, itemsPerPage)) {
                is Result.Success -> {
                    if(currentPage == initialPage) {
                        configurePagination(result.data?.total)
                    }
                    currentPage = result.data?.offset?.div(itemsPerPage)!!
                    _pageSelected.postValue(currentPage)
                    _characterList.postValue(result.data.results)
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

    private fun configurePagination(total: Int?) {
        total?.let {
            //calculando a quantidade de indicadores de páginas para serem montadas
            val pages = ceil(it/itemsPerPage.toDouble()).toInt()
            _pageIndicator.postValue(pages)
        }
    }

    fun requestNextPage() {
        getCharacters(currentName, ++currentPage)
    }

    fun requestPreviousPage() {
        getCharacters(currentName, --currentPage)
    }


    class ViewModelFactory(private val listCharactersUseCase: ListCharactersUseCase):
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(ListCharacterViewModel::class.java)) {
                return ListCharacterViewModel(listCharactersUseCase) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}