package com.example.starsign.cardformulars

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.starsign.R
import com.example.starsign.database.Card
import com.example.starsign.database.Mana
import com.example.starsign.database.Source
import com.example.starsign.databinding.SourceCreatorFragmentBinding
import com.example.starsign.databinding.SpellCreatorFragmentBinding
import org.koin.android.ext.android.inject

class SourceCreator : Fragment(){

    private lateinit var binding: SourceCreatorFragmentBinding
    private val viewModel: CardCreatorViewModel by inject()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.source_creator_fragment, container, false)
        val layoutManager = LinearLayoutManager(this.context)
        val requirementAdapter = AttributeAdapter()
        requirementAdapter.submitList(Mana.values().asList())
        binding.sourcetypes.layoutManager = layoutManager
        binding.sourcetypes.adapter = requirementAdapter
        binding.sourcecreatorbutton.setOnClickListener {
            val attributeRequirements = mutableMapOf<Mana, Int>()
            for(index in 0 until (binding.sourcetypes.adapter as AttributeAdapter).itemCount){
                val viewHolder = binding.sourcetypes.findViewHolderForAdapterPosition(index) as AttributeViewHolder
                if(viewHolder.getMana()!=null) {
                    attributeRequirements[viewHolder.getMana()?:Mana.APEIRON] = viewHolder.getManaAmount()
                }
            }
            viewModel.insertCard(Source(binding.sourcetitletext.text.toString(), attributeRequirements))
        }
        viewModel.cardCreationResult.observe(viewLifecycleOwner, Observer{
            if(it.exception != null){
                Toast.makeText(context, it.exception.message, Toast.LENGTH_SHORT).show()
            }
            if(it.success != null){
                for(index in 0 until (binding.sourcetypes.adapter as AttributeAdapter).itemCount){
                    val viewHolder = binding.sourcetypes.findViewHolderForAdapterPosition(index) as AttributeViewHolder
                    viewHolder.cleartext()
                }
                binding.sourcetitletext.text.clear()
                Toast.makeText(context, String.format("Source %s was successfully created.", it.success.title), Toast.LENGTH_SHORT).show()
            }
        })
        return binding.root
    }
}
