package com.example.starsign.carddetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.starsign.R
import com.example.starsign.cardformulars.EditorViewModel
import com.example.starsign.database.Card
import com.example.starsign.database.Magic
import com.example.starsign.database.Mana
import com.example.starsign.database.Spell
import com.example.starsign.databinding.FragmentSpellDetailBinding
import com.example.starsign.observers.Observer
import org.koin.android.ext.android.inject


/**
 * A simple [Fragment] subclass.
 * Use the [SpellDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SpellDetailFragment : Fragment(), Observer {
    private lateinit var binding : FragmentSpellDetailBinding
    private val viewModel: EditorViewModel by inject()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_spell_detail, container, false)
        val args = arguments?.let{SpellDetailFragmentArgs.fromBundle(it)}
        val spell = args?.card!!
        createView(spell)
        return inflater.inflate(R.layout.fragment_spell_detail, container, false)
    }

    override fun onPause() {
        viewModel.registerObserver(this)
        super.onPause()
    }

    override fun onResume() {
        viewModel.removeObserver(this)
        super.onResume()
    }

    override fun update(card: Card) {
        if(card is Magic){
            createView(card)
        }
    }

    private fun createView(spell: Magic){
        binding.titlelabel.text = spell.title
        binding.spelltypetext.text = spell.species.name
        val manaAdapter = spell.manaamount.let{ManaDetailAdapter(it)}
        manaAdapter.submitList(spell.manaamount.keys.toList())
        binding.costlist.adapter = manaAdapter
        val spellAdapter = spell.spells.let{SpellDetailAdapter(it)}
        spellAdapter.submitList(spell.spells.keys.toList())
        binding.effectlist.adapter = spellAdapter
        binding.edittextbutton.setOnClickListener { SpellDetailFragmentDirections.actionSpellDetailFragmentToSpellEditorFragment(spell) }
    }
}