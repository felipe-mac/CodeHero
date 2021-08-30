package com.app.codehero.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.codehero.databinding.ItemCharacterBinding
import com.app.codehero.domain.model.Character
import com.app.codehero.utils.loadFromUrl

class CharactersAdapter (private var dataSet: List<Character>,
                         private val onClick: (characterId: Int) -> Unit) :
    RecyclerView.Adapter<CharactersAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(private val binding: ItemCharacterBinding,
                     onClick: (characterId: Int) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var character: Character

        init {
            binding.root.setOnClickListener {
                onClick(character.id)
            }
        }

        fun bind(character: Character) {
            this.character = character
            binding.texviewCharacterName.text = character.name
            val thumbnail = character.thumbnail
            binding.imageviewCharacter.loadFromUrl(thumbnail)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<Character>) {
        dataSet = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemCharacterBinding.inflate(LayoutInflater.from(viewGroup.context),
        viewGroup, false), onClick)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size

}