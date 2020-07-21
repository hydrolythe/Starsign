package com.example.starsign.cardformulars

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.starsign.R
import com.example.starsign.database.DatabaseMonster
import com.example.starsign.database.Mana
import com.example.starsign.database.Monster
import com.example.starsign.database.Spell
import com.example.starsign.databinding.MonsterCreatorFragmentBinding
import org.koin.android.ext.android.inject

class MonsterEditorFragment : Fragment()  {
    private lateinit var binding : MonsterCreatorFragmentBinding
    private val viewModel: EditorViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args = arguments?.let{MonsterEditorFragmentArgs.fromBundle(it)}
        val monster = args?.card!!
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.monster_creator_fragment,
            container,
            false
        )
        val attAdapter = AttributeAdapter(monster.manarequirements)
        attAdapter.submitList(Mana.values().asList())
        binding.manaselections.adapter = attAdapter
        val spellAdapter = EffectsAdapter(monster.spells)
        spellAdapter.submitList(Spell.values().asList())
        binding.spellfield.adapter = spellAdapter
        val texts = listOf<List<Any>>(
            listOf(binding.titletextfield.text, monster.title),
            listOf(binding.lifetextfield.text, monster.life),
            listOf(binding.attacktextfield.text, monster.attack),
            listOf(binding.defensetextfield.text, monster.defense),
            listOf(binding.magicattacktextfield.text, monster.magicattack),
            listOf(binding.magicdefensefield.text, monster.magicdefense),
            listOf(binding.mptextfield.text, monster.mp)
        )
        texts.forEach { t ->
            var edited = t.get(0)
            val text = t.get(1)
            if (edited is Editable && text is String) {
                edited = text
            }
        }
        binding.submitmonster.setOnClickListener {
            val attributeRequirements = mutableMapOf<Mana, Int>()
            for (index in 0 until (binding.manaselections.adapter as AttributeAdapter).itemCount) {
                val viewHolder =
                    binding.manaselections.findViewHolderForAdapterPosition(index) as AttributeViewHolder
                if (viewHolder.getMana() != null) {
                    attributeRequirements[viewHolder.getMana() ?: Mana.APEIRON] =
                        viewHolder.getManaAmount()
                }
            }
            val spells = mutableMapOf<Spell, Int>()
            for (index in 0 until (binding.spellfield.adapter as EffectsAdapter).itemCount) {
                val viewHolder =
                    binding.spellfield.findViewHolderForAdapterPosition(index) as EffectViewHolder
                if (viewHolder.getSpell() != null) {
                    spells[viewHolder.getSpell() ?: Spell.BOOSTATTACK] =
                        viewHolder.getMpAmount()
                }
            }
            viewModel.updateCard(
                DatabaseMonster(
                    monster.cardid,
                    binding.titletextfield.text.toString(),
                    attributeRequirements,
                    Integer.parseInt(binding.lifetextfield.text.toString()),
                    Integer.parseInt(binding.attacktextfield.text.toString()),
                    Integer.parseInt(binding.defensetextfield.text.toString()),
                    Integer.parseInt(binding.magicattacktextfield.text.toString()),
                    Integer.parseInt(binding.magicdefensefield.text.toString()),
                    Integer.parseInt(binding.mptextfield.text.toString()),
                    spells
                )
            )
        }
        viewModel.cardEditResult.observe(viewLifecycleOwner, Observer {
            if (it.exception != null) {
                Toast.makeText(context, it.exception.message, Toast.LENGTH_SHORT).show()
            }
            if (it.success != null) {
                Toast.makeText(context, String.format("Succesful edit."), Toast.LENGTH_SHORT)
                    .show()
                getActivity()?.supportFragmentManager?.beginTransaction()?.remove(this)
                    ?.commit()
            }
        })
        return inflater.inflate(R.layout.monster_creator_fragment, container, false)
    }
}