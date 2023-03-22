package com.app.codehero

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.codehero.databinding.ActivityMainBinding
import com.app.codehero.domain.model.Character
import com.app.codehero.ui.adapter.CharactersAdapter
import com.app.codehero.ui.adapter.PageIndicatorAdapter
import com.app.codehero.ui.main.CharacterDetailsActivity
import com.app.codehero.ui.main.ListCharacterViewModel
import com.app.codehero.utils.Constants
import com.app.codehero.utils.DialogTools
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var p = 0


    private val viewModel: ListCharacterViewModel by viewModels()


    private lateinit var mAdapter: CharactersAdapter
    private lateinit var pageIndicatorAdapter: PageIndicatorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        val view = binding.root
        setContentView(view)

        viewModel.pageIndicator.observe(this) { pages ->
            Log.d("FMS", "montar $pages page indicator")
            createPageIndicator(pages)
        }

        viewModel.characterList.observe(this) { characterList ->
            Log.d("FMS", "chars $characterList ")
            showList(characterList)
        }

        viewModel.pageSelected.observe(this) { page ->
            Log.d("FMS", "pageSelectedObs $page")
            p = page
            binding.imageviewArrowLeft.isEnabled = p != 0
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

        binding.edittextSearchCharacter.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                Log.d("FMS", "search: ${v.text}")
                val imm = this@MainActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, InputMethodManager.RESULT_UNCHANGED_SHOWN)
                viewModel.getCharacters(v.text.toString())
                true
            } else false
        }

        configureRequestPage()

        configureRecyclerViewCharacters()

        viewModel.getCharacters()
    }

    private fun showProgress(message: String?) {
        DialogTools.showProgressDialog(this, message!!)
    }

    private fun showError(title: String?, message: String?) {
        DialogTools.showErrorDialog(this, title!!, message!!)
    }

    private fun configureRequestPage() {
        binding.imageviewArrowLeft.setOnClickListener {
            viewModel.requestPreviousPage()
        }

        binding.imageviewArrowRight.setOnClickListener {
            viewModel.requestNextPage()
        }
    }

    private fun showList(characterList: List<Character>?) {
        characterList?.let {
            mAdapter.updateList(it)
            pageIndicatorAdapter.updatePage(p)
            pageIndicatorAdapter.notifyDataSetChanged()
            binding.recyclerviewPageIndicator.smoothScrollToPosition(p)

        }
    }

    private fun createPageIndicator(totalPages: Int) {
        val pages = mutableListOf<String>()
        for(i in 1..totalPages) {
            pages.add("$i")
        }
        configureRecyclerViewPages(pages)
    }

    private fun configureRecyclerViewCharacters() {
        mAdapter = CharactersAdapter(listOf()) { characterId ->
            openCharacterDetails(characterId)
        }
        binding.recyclerviewCharacters.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = mAdapter
            val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            addItemDecoration(divider)
        }
    }

    private fun openCharacterDetails(characterId: Int) {
        startActivity(Intent(CharacterDetailsActivity.getNewIntent(this, characterId)))
    }

    private fun configureRecyclerViewPages(pages: List<String>) {
        pageIndicatorAdapter = PageIndicatorAdapter(pages, p){
//            Log.d("FMS", "clicked: ${(it as TextView).text}")
            val page = (it as TextView).text.toString().toInt()
            pageSelected(page)
        }
        binding.recyclerviewPageIndicator.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = pageIndicatorAdapter
        }
    }

    private fun pageSelected(page: Int) {
        Log.d("FMS", "pageSelected $page ")
        val textName = binding.edittextSearchCharacter.text.toString()
        viewModel.getCharacters(textName, page-1)
    }
}