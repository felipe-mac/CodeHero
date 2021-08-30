package com.app.codehero.ui.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.codehero.data.RemoteCharacterDataSource
import com.app.codehero.databinding.ActivityCharacterDetailsBinding
import com.app.codehero.databinding.ActivityMainBinding
import com.app.codehero.domain.model.Character
import com.app.codehero.utils.Constants.CHARACTERID
import com.app.codehero.utils.loadFromUrl
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class CharacterDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharacterDetailsBinding

    private val viewModel: CharacterViewModel by lazy {
        ViewModelProvider(
            this,
            CharacterViewModel.ViewModelFactory(RemoteCharacterDataSource())
        ).get(CharacterViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel.character.observe(this, Observer {
            configureDetails(it)
        })

        val characterId = intent.extras?.getInt(CHARACTERID)
        characterId?.let {
            viewModel.getCharacterDetail(it)
        }

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
            binding.textviewTitleCharacterDescription.text = if(it.description.isBlank()) "Sem descrição" else it.description
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