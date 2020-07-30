package com.example.starsign.carddetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.starsign.R
import com.example.starsign.cardformulars.EditorViewModel
import com.example.starsign.database.*
import com.example.starsign.databinding.FragmentSpellDetailBinding
import org.koin.android.ext.android.inject


/**
 * A simple [Fragment] subclass.
 * Use the [SpellDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SpellDetailFragment : Fragment() {
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
        viewModel.newCard.observe(viewLifecycleOwner, Observer{
            if(it is Magic){
                createView(it)
            }
            else{
                Toast.makeText(context, String.format("Error: The name of the spell got modified while you tried to modify it."), Toast.LENGTH_SHORT)
                    .show()
                getActivity()?.supportFragmentManager?.beginTransaction()?.remove(this)
                    ?.commit()
            }
        })
        return binding.root
    }

    private fun createView(spell: Magic){
        binding.titlelabel.text = spell.title
        binding.spelltypetext.text = spell.species.name
        val layoutManager = LinearLayoutManager(this.context)
        binding.costlist.layoutManager = layoutManager
        val manaAdapter = spell.manaamount.let{ManaDetailAdapter(it)}
        manaAdapter.submitList(spell.manaamount.keys.toList())
        binding.costlist.adapter = manaAdapter
        val layoutManager2 = LinearLayoutManager(this.context)
        binding.effectlist.layoutManager = layoutManager2
        val spellAdapter = spell.spells.let{SpellDetailAdapter(it)}
        spellAdapter.submitList(spell.spells.keys.toList())
        binding.effectlist.adapter = spellAdapter
        val dbSpell = viewModel.getDbCard<DatabaseMagic>(spell.title)
        binding.edittextbutton.setOnClickListener {
            if(dbSpell!=null){
                it.findNavController().navigate(SpellDetailFragmentDirections.actionSpellDetailFragmentToSpellEditorFragment(dbSpell))
            }
            else{
                Toast.makeText(context, String.format("Error: The name of the monster got modified while you tried to modify it."), Toast.LENGTH_SHORT)
                    .show()
                getActivity()?.supportFragmentManager?.beginTransaction()?.remove(this)
                    ?.commit()
            }
        }
    }
}