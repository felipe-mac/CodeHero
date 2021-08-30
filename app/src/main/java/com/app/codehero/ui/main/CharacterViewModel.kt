package com.app.codehero.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.app.codehero.api.Result
import com.app.codehero.data.CharacterRepository
import com.app.codehero.domain.model.Character
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.ceil

//https://howtodoandroid.com/mvvm-kotlin-coroutines-retrofit/#Making_it_work_with_Retrofit
//https://github.com/IsaiasCuvula/android_paging3_retrofit2_mvvm

class CharacterViewModel constructor(
    private val dataSource: CharacterRepository
) :
    ViewModel() {

    private val initialPage: Int = 0
    private var currentPage: Int = initialPage
    private val itemsPerPage: Int = 4
    private var currentName: String = ""

    private val _pageIndicator = MutableLiveData<Int>()
    var pageIndicator = _pageIndicator

    private val _pageSelected = MutableLiveData<Int>()
    var pageSelected = _pageSelected

    private val _characterList = MutableLiveData<List<Character>>()
    var characterList = _characterList

    private val _character = MutableLiveData<Character>()
    var character = _character

    fun getCharacters(name: String = "", page: Int=initialPage) {
        currentPage = page*itemsPerPage //ajuste do offset
        currentName = name
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = dataSource.getChars(currentName, currentPage, itemsPerPage)) {
                is Result.Success -> {
                    Log.d("FMS", "success")
                    if(currentPage == initialPage) {
                        configurePagination(result.data?.total)
                    }
                    currentPage = result.data?.offset?.div(itemsPerPage)!!
                    _pageSelected.postValue(currentPage)
                    _characterList.postValue(result.data?.results)
                }
                is Result.Failure -> {
                    Log.d("FMS","failure getCharacters: ${result.statusCode}")
                }
                else -> Log.d("FMS","server error getCharacters")
            }
        }
    }

    private fun configurePagination(total: Int?) {
        total?.let {
            //calculando a quantidade de páginas para serem montadas
            val pages = ceil(it/itemsPerPage.toDouble()).toInt()
            //mandar para activity
                _pageIndicator.postValue(pages)
        }
    }

    fun getCharacterDetail(characterId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = dataSource.getCharacterDetails(characterId)) {
                is Result.Success -> {
                    Log.d("FMS", "getCharacterDetail success")
                    _character.postValue(result.data?.results?.first())
                }
                is Result.Failure -> {
                    Log.d("FMS","getCharacterDetail failure getCharacters: ${result.statusCode}")
                }
                else -> Log.d("FMS","getCharacterDetail server error getCharacters")
            }
        }
    }

    fun requestNextPage() {
        getCharacters(currentName, ++currentPage)
    }

    fun requestPreviousPage() {
        getCharacters(currentName, --currentPage)
    }


    class ViewModelFactory(private val dataSource: CharacterRepository):
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(CharacterViewModel::class.java)) {
                return CharacterViewModel(dataSource) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}