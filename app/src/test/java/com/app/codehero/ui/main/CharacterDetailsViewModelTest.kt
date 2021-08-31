package com.app.codehero.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.app.codehero.api.Result
import com.app.codehero.domain.model.Character
import com.app.codehero.domain.model.Characters
import com.app.codehero.domain.model.Thumbnail
import com.app.codehero.domain.usecase.CharacterDetailsUseCase
import com.app.codehero.getOrAwaitValue
import com.app.codehero.utils.Constants
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test

/**
 * FELIPE
 */
class CharacterDetailsViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: CharacterDetailsViewModel

    @Test
    fun `when view model getCharacterDetail get success then sets character `() {
        val characterId = 123
        val charList = listOf(
            Character(123, "Name01", "Desc01", "-", Thumbnail("path", "extension")),
        )
        val characters = Characters(
            0,
            0,
            0,
            0,
            charList
        )
        val resultSuccess = MockCharacterDetailsUseCase(Result.Success(characters))
        viewModel = CharacterDetailsViewModel(resultSuccess)

        viewModel.getCharacterDetail(characterId)

        assertThat(
            viewModel.character.getOrAwaitValue(),
            `is`(charList.first())
        )

    }

    @Test
    fun `when view model getCharacterDetail get failure then sets dialogDisplay`() {
        val characterId = 123
        val error = 400
        val resultFailure = MockCharacterDetailsUseCase(Result.Failure(400, Exception("exception message")))
        viewModel = CharacterDetailsViewModel(resultFailure)

        viewModel.getCharacterDetail(characterId)
        assertThat(
            viewModel.dialogDisplay.getOrAwaitValue(),
            `is`(
                Triple(
                    Constants.DIALOGTYPE.ERROR,
                    "Erro $error",
                    "exception message"
                )
            )
        )
    }

    @Test
    fun `when view model getCharacterDetail get error then sets dialogDisplay`() {
        val characterId = 123
        val resultError = MockCharacterDetailsUseCase(Result.Error(Exception("exception message")))
        viewModel = CharacterDetailsViewModel(resultError)

        viewModel.getCharacterDetail(characterId)
        assertThat(
            viewModel.dialogDisplay.getOrAwaitValue(),
            `is`(
                Triple(
                    Constants.DIALOGTYPE.ERROR,
                    "Atenção",
                    "Não foi possível se conectar ao servidor."
                )
            )
        )
    }
}

class MockCharacterDetailsUseCase(private val result: Result<Characters?>) :
    CharacterDetailsUseCase {
    override suspend fun invoke(charId: Int): Result<Characters?> {
        return result
    }

}