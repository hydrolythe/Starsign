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
import com.example.starsign.data.Resulting
import com.example.starsign.database.Card
import com.example.starsign.database.Mana
import com.example.starsign.database.Monster
import com.example.starsign.database.Spell
import com.example.starsign.databinding.MonsterCreatorFragmentBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject

class MonsterCreatorFragment : Fragment() {

    private lateinit var binding : MonsterCreatorFragmentBinding
    private val viewModel: CardCreatorViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.monster_creator_fragment, container, false)
        val layoutManager = LinearLayoutManager(this.context)
        binding.manaselections.layoutManager = layoutManager
        val attAdapter = AttributeAdapter()
        attAdapter.submitList(Mana.values().asList())
        binding.manaselections.adapter = attAdapter
        val layoutManager2 = LinearLayoutManager(this.context)
        binding.spellfield.layoutManager = layoutManager2
        val spellAdapter = EffectsAdapter()
        spellAdapter.submitList(Spell.values().asList())
        binding.spellfield.adapter = spellAdapter
        binding.submitmonster.setOnClickListener {
            val attributeRequirements = mutableMapOf<Mana, Int>()
            for(index in 0 until (binding.manaselections.adapter as AttributeAdapter).itemCount){
                val viewHolder = binding.manaselections.findViewHolderForAdapterPosition(index) as AttributeViewHolder
/*                if(viewHolder.getMana()!=null) {
                    attributeRequirements[viewHolder.getMana()?:Mana.APEIRON] = viewHolder.getManaAmount()
                }
*/            }
            val spells = mutableMapOf<Spell, Int>()
            for(index in 0 until (binding.spellfield.adapter as EffectsAdapter).itemCount){
                val viewHolder = binding.spellfield.findViewHolderForAdapterPosition(index) as EffectViewHolder
                if(viewHolder.getSpell()!=null) {
//                    spells[viewHolder.getSpell()?:Spell.BOOSTATTACK] = viewHolder.getMpAmount()
                }
            }
            viewModel.insertCard(Monster(binding.titletextfield.text.toString(), attributeRequirements, Integer.parseInt(binding.lifetextfield.text.toString()), Integer.parseInt(binding.attacktextfield.text.toString()), Integer.parseInt(binding.defensetextfield.text.toString()), Integer.parseInt(binding.magicattacktextfield.text.toString()), Integer.parseInt(binding.magicdefensefield.text.toString()), Integer.parseInt(binding.mptextfield.text.toString()), spells))
        }
        viewModel.cardCreationResult.observe(viewLifecycleOwner, Observer{
            if(it.exception != null){
                Toast.makeText(context, it.exception.message, Toast.LENGTH_SHORT).show()
            }
            if(it.success != null){
                for(index in 0 until (binding.manaselections.adapter as AttributeAdapter).itemCount){
                    val viewHolder = binding.manaselections.findViewHolderForAdapterPosition(index) as AttributeViewHolder
//                    viewHolder.cleartext()
                }
                for(index in 0 until (binding.spellfield.adapter as EffectsAdapter).itemCount){
                    val viewHolder = binding.spellfield.findViewHolderForAdapterPosition(index) as EffectViewHolder
//                    viewHolder.clearText()
                }
                val texts = listOf<Editable>(binding.titletextfield.text, binding.lifetextfield.text, binding.attacktextfield.text, binding.defensetextfield.text, binding.magicattacktextfield.text, binding.magicdefensefield.text, binding.mptextfield.text)
                texts.forEach { edit -> edit.clear() }
                Toast.makeText(context, String.format("Monster %s was successfully created.", it.success.title), Toast.LENGTH_SHORT).show()
            }
        })
        return binding.root
    }
}
