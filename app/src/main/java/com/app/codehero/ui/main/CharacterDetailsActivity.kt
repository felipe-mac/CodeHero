package com.app.codehero.ui.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import com.app.codehero.databinding.ActivityCharacterDetailsBinding
import com.app.codehero.domain.model.Character
import com.app.codehero.utils.Constants
import com.app.codehero.utils.Constants.CHARACTERID
import com.app.codehero.utils.DialogTools
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharacterDetailsBinding

     private val viewModel: CharacterDetailsViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterDetailsBinding.inflate(layoutInflater)

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