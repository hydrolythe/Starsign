package com.example.starsign.carddetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.starsign.R
import com.example.starsign.databinding.FragmentTrueMonsterCreatorBinding

/**
 * A simple [Fragment] subclass.
 * Use the [TrueMonsterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TrueMonsterFragment : Fragment() {

    lateinit var binding: FragmentTrueMonsterCreatorBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_true_monster_creator, container, false)
        val args = arguments?.let{TrueMonsterFragmentArgs.fromBundle(it)}
        val dbMonster = args?.card!!
        val layoutManager = LinearLayoutManager(this.context)
        binding.manacost.layoutManager = layoutManager
        val manaadapter = dbMonster.manarequirements.let{ManaDetailAdapter(it)}
        manaadapter.submitList(dbMonster.manarequirements.keys.toList())
        binding.manacost.adapter = manaadapter
        val layoutManager2 = LinearLayoutManager(this.context)
        binding.effects.layoutManager = layoutManager2
        if(dbMonster.spells!=null) {
            val effectAdapter = dbMonster.spells.let { SpellDetailAdapter(it) }
            effectAdapter.submitList(dbMonster.spells.keys.toList())
            binding.effects.adapter = effectAdapter
        }
        binding.submit.setOnClickListener {
            it.findNavController().navigate(TrueMonsterFragmentDirections.actionTrueMonsterFragmentToMonsterEditorFragment(dbMonster))
        }
        return binding.root
    }
}