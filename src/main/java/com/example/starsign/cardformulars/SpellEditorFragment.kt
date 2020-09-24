package com.example.starsign.cardformulars

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.starsign.R
import com.example.starsign.database.*
import com.example.starsign.databinding.SpellCreatorFragmentBinding
import com.example.starsign.network.NetworkMagic
import org.koin.android.ext.android.inject

class SpellEditorFragment : Fragment() {
    private var selectedSpellspecie = MutableLiveData<SpellSpecies>()
    private lateinit var binding: SpellCreatorFragmentBinding
    private val viewModel: EditorViewModel by inject()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.spell_creator_fragment, container, false)
        val args = arguments?.let{SpellEditorFragmentArgs.fromBundle(it)}
        val spell = args?.card!!
        binding.spell = spell
        val layoutManager = LinearLayoutManager(this.context)
        binding.spellspeciesoptions.layoutManager = layoutManager
        selectedSpellspecie.value = spell.species
        val spelladapter = SpellspeciesAdapter(SpellSpeciesListener { species ->
            selectedSpellspecie.value = species
        }, spell.species)
        spelladapter.submitList(SpellSpecies.values().asList())
        binding.spellspeciesoptions.adapter = spelladapter
        val layoutManager2 = LinearLayoutManager(this.context)
        binding.effectspells.layoutManager = layoutManager2
        val magicadapter = EffectsAdapter(spell.spells)
        magicadapter.submitList(Spell.values().asList())
        binding.effectspells.adapter = magicadapter
        val layoutManager3 = LinearLayoutManager(this.context)
        binding.manacost.layoutManager = layoutManager3
        val requirementAdapter = AttributeAdapter(spell.manaamount)
        requirementAdapter.submitList(Mana.values().asList())
        binding.manacost.adapter = requirementAdapter
        binding.addspellbutton.setOnClickListener {
            val attributeRequirements = mutableMapOf<Mana, Int>()
            for (index in 0 until (binding.manacost.adapter as AttributeAdapter).itemCount) {
                val viewHolder =
                    binding.manacost.findViewHolderForAdapterPosition(index) as AttributeViewHolder
                if (viewHolder.getMana() != null && viewHolder.getManaAmount()!=0) {
                    attributeRequirements[viewHolder.getMana() ?: Mana.APEIRON] =
                        viewHolder.getManaAmount()
                }
            }
            val spells = mutableMapOf<Spell, Int>()
            for (index in 0 until (binding.effectspells.adapter as EffectsAdapter).itemCount) {
                val viewHolder =
                    binding.effectspells.findViewHolderForAdapterPosition(index) as EffectViewHolder
                if (viewHolder.getSpell() != null && viewHolder.getMpAmount()!=0) {
                    spells[viewHolder.getSpell() ?: Spell.BOOSTATTACK] =
                        viewHolder.getMpAmount()
                }
            }
            viewModel.updateCard(
                NetworkMagic(
                    spell.cardid,
                    binding.spelltitletext.text.toString(),
                    selectedSpellspecie.value!!,
                    spells,
                    attributeRequirements
                )
            )
        }
        viewModel.cardEditResult.observe(viewLifecycleOwner, Observer {
            if (it.exception != null) {
                Toast.makeText(context, it.exception.message, Toast.LENGTH_SHORT).show()
            }
            if (it.success != null) {
                Toast.makeText(context, String.format("Successful edit."), Toast.LENGTH_SHORT)
                    .show()
            }
        })
        return binding.root
    }
}