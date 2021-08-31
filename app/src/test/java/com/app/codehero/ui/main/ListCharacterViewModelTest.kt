package com.app.codehero.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.app.codehero.api.Result
import com.app.codehero.domain.model.Character
import com.app.codehero.domain.model.Characters
import com.app.codehero.domain.model.Thumbnail
import com.app.codehero.domain.usecase.ListCharactersUseCase
import com.app.codehero.getOrAwaitValue
import com.app.codehero.utils.Constants
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.MockitoAnnotations
import java.lang.Exception
import kotlin.math.ceil

/**
 * FELIPE
 */

class ListCharacterViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: ListCharacterViewModel

    @Test
    fun `when view model getCharacters get success then sets characterList and pageSelected `() {
        val offset = 1
        val itemsPerPage = 4
        val name = ""
        val total = 100
        val charList =  listOf(
            Character(1,"Name01","Desc01","-", Thumbnail("path", "extension")),
            Character(2,"Name02","Desc02","-", Thumbnail("path", "extension")),
            Character(3,"Name03","Desc03","-", Thumbnail("path", "extension")),
            Character(4,"Name04","Desc04","-", Thumbnail("path", "extension"))
        )
        val characters = Characters(
           offset,
            itemsPerPage,
            total,
            itemsPerPage,
            charList
        )
        val resultSuccess = MockListCharacterUseCase(Result.Success(characters))
        viewModel = ListCharacterViewModel(resultSuccess)

        viewModel.getCharacters(name, offset)

        assertThat(viewModel.characterList.getOrAwaitValue(), `is`(charList))
        assertThat(viewModel.pageSelected.getOrAwaitValue(), `is`(offset.div(itemsPerPage)))

    }

    @Test
    fun `when view model getCharacters get success then sets pageIndicator if initialPage `() {
        val initialPage = 0
        val offset = initialPage
        val itemsPerPage = 4
        val name = ""
        val total = 100
        val totalPages = ceil(total/itemsPerPage.toDouble()).toInt()
        val charList =  listOf(
            Character(1,"Name01","Desc01","-", Thumbnail("path", "extension"))
        )
        val characters = Characters(
            offset,
            itemsPerPage,
            total,
            itemsPerPage,
            charList
        )
        val resultSuccess = MockListCharacterUseCase(Result.Success(characters))
        viewModel = ListCharacterViewModel(resultSuccess)

        viewModel.getCharacters(name, offset)

        assertThat(viewModel.pageIndicator.getOrAwaitValue(), `is`(totalPages))

    }

    @Test
    fun `when view model getCharacters get failure then sets dialogDisplay`() {
        val error = 400
        val resultFailure = MockListCharacterUseCase(Result.Failure(error, Exception("exception message")))
        viewModel = ListCharacterViewModel(resultFailure)

        viewModel.getCharacters() //comentar o postValue PROGRESS
//        assertThat(viewModel.dialogDisplay.getOrAwaitValue(), `is`(Triple(Constants.DIALOGTYPE.PROGRESS, "Aguarde","Carregando dados")))
        assertThat(viewModel.dialogDisplay.getOrAwaitValue(), `is`(Triple(Constants.DIALOGTYPE.ERROR, "Erro $error","exception message")))
//        assertThat(viewModel.dialogDisplay.getOrAwaitValue(), `is`(Triple(Constants.DIALOGTYPE.DISMISS, null,null)))

    }

    @Test
    fun `when view model getCharacters get error then sets dialogDisplay`() {
        val resultError= MockListCharacterUseCase(Result.Error(Exception("exception message")))
        viewModel = ListCharacterViewModel(resultError)

        viewModel.getCharacters()
        assertThat(viewModel.dialogDisplay.getOrAwaitValue(), `is`(Triple(Constants.DIALOGTYPE.ERROR, "Atenção", "Não foi possível se conectar ao servidor.")))

    }
}

class MockListCharacterUseCase(private val result: Result<Characters?>): ListCharactersUseCase {
    override suspend fun invoke(name: String, offset: Int, count: Int): Result<Characters?> {
        return result
    }
}