package com.app.codehero.ui.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.codehero.CodeHeroApp
import com.app.codehero.data.CharRepository
import com.app.codehero.data.RetrofitCharacterDataSource
import com.app.codehero.databinding.ActivityCharacterDetailsBinding
import com.app.codehero.domain.model.Character
import com.app.codehero.domain.usecase.CharacterDetailsUseCaseImpl
import com.app.codehero.utils.Constants
import com.app.codehero.utils.Constants.CHARACTERID
import com.app.codehero.utils.DialogTools
import com.app.codehero.utils.loadFromUrl
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import javax.inject.Inject

class CharacterDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharacterDetailsBinding

    @Inject
    lateinit var viewModel: CharacterDetailsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterDetailsBinding.inflate(layoutInflater)
        (applicationContext as CodeHeroApp).appComponent.mainComponent().create().inject(this)

        val view = binding.root
        setContentView(view)

        viewModel.character.observe(this) {
            configureDetails(it)
        }

        viewModel.dialogDisplay.observe(this) { data ->
            when (data.first) {
                Constants.DIALOGTYPE.PROGRESS -> {
                    showProgress(data.third)
                }
                Constants.DIALOGTYPE.ERROR -> {
                    showError(data.second, data.third)
                }
                Constants.DIALOGTYPE.DISMISS -> {
                    DialogTools.dismissProgressDialog()
                }
            }
        }

        val characterId = intent.extras?.getInt(CHARACTERID)
        characterId?.let {
            viewModel.getCharacterDetail(it)
        }

    }

    private fun showProgress(message: String?) {
        DialogTools.showProgressDialog(this, message!!)
    }

    private fun showError(title: String?, message: String?) {
        DialogTools.showErrorDialog(this, title!!, message!!)
    }

    private fun configureDetails(character: Character?) {
        Log.d("FMS", "char: $character")
        character?.let {
            val url = it.thumbnail.path + "/standard_fantastic." + it.thumbnail.extension
            Glide.with(this)
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.imageviewCharacter)
            binding.textviewCharacterDescription.text = if(it.description.isBlank()) "Sem descrição" else it.description
            supportActionBar?.title = it.name
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
            else -> super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        fun getNewIntent(context: Context, characterId: Int): Intent {
            return Intent(context, CharacterDetailsActivity::class.java).apply {
                putExtra(CHARACTERID, characterId)
            }
        }
    }
}