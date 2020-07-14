package com.example.starsign.cardformulars

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer

import com.example.starsign.R
import com.example.starsign.database.*
import com.example.starsign.databinding.SpellCreatorFragmentBinding
import org.koin.android.ext.android.inject

class SpellCreator : Fragment() {

    private lateinit var selectedSpellspecie: SpellSpecies
    private lateinit var binding: SpellCreatorFragmentBinding
    private val viewModel: CardCreatorViewModel by inject()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.spell_creator_fragment, container, false)
        val spelladapter = SpellspeciesAdapter(SpellSpeciesListener { species -> selectedSpellspecie = species })
        spelladapter.submitList(SpellSpecies.values().asList())
        binding.spellspeciesoptions.adapter = spelladapter
        val magicadapter = EffectsAdapter()
        magicadapter.submitList(Spell.values().asList())
        binding.effectspells.adapter = magicadapter
        val requirementAdapter = AttributeAdapter()
        requirementAdapter.submitList(Mana.values().asList())
        binding.manacost.adapter = requirementAdapter
        binding.addspellbutton.setOnClickListener {
            val attributeRequirements = mutableMapOf<Mana, Int>()
            for(index in 0 until (binding.manacost.adapter as AttributeAdapter).itemCount){
                val viewHolder = binding.manacost.findViewHolderForAdapterPosition(index) as AttributeViewHolder
                if(viewHolder.getMana()!=null) {
                    attributeRequirements[viewHolder.getMana()?:Mana.APEIRON] = viewHolder.getManaAmount()
                }
            }
            val spells = mutableMapOf<Spell, Int>()
            for(index in 0 until (binding.effectspells.adapter as EffectsAdapter).itemCount){
                val viewHolder = binding.effectspells.findViewHolderForAdapterPosition(index) as EffectViewHolder
                if(viewHolder.getSpell()!=null) {
                    spells[viewHolder.getSpell()?:Spell.BOOSTATTACK] = viewHolder.getMpAmount()
                }
            }
            viewModel.insertCard(Magic(binding.spelltitletext.text.toString(), selectedSpellspecie, spells, attributeRequirements))
        }
        viewModel.cardCreationResult.observe(viewLifecycleOwner, Observer{
            if(it.exception != null){
                Toast.makeText(context, it.exception.message, Toast.LENGTH_SHORT).show()
            }
            if(it.success != null){
                for(index in 0 until (binding.manacost.adapter as AttributeAdapter).itemCount){
                    val viewHolder = binding.manacost.findViewHolderForAdapterPosition(index) as AttributeViewHolder
                    viewHolder.cleartext()
                }
                for(index in 0 until (binding.effectspells.adapter as EffectsAdapter).itemCount){
                    val viewHolder = binding.effectspells.findViewHolderForAdapterPosition(index) as EffectViewHolder
                    viewHolder.clearText()
                }
                val viewHolder = binding.spellspeciesoptions.findViewHolderForAdapterPosition(0) as SpellspeciesViewHolder
                viewHolder.check()
                viewHolder.uncheck()
                Toast.makeText(context, String.format("Spell %s was successfully created.", it.success.title), Toast.LENGTH_SHORT).show()
            }
        })
        return inflater.inflate(R.layout.spell_creator_fragment, container, false)
    }

}
