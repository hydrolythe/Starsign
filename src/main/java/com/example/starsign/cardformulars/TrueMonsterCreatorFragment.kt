package com.example.starsign.cardformulars

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.starsign.R
import com.example.starsign.carddetail.TrueMonsterFragmentArgs
import com.example.starsign.database.Magic
import com.example.starsign.database.Mana
import com.example.starsign.database.Monster
import com.example.starsign.database.Spell
import com.example.starsign.databinding.FragmentTrueMonsterCreatorBinding
import org.koin.android.ext.android.inject


/**
 * A simple [Fragment] subclass.
 * Use the [TrueMonsterCreatorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TrueMonsterCreatorFragment : Fragment() {

    lateinit var binding : FragmentTrueMonsterCreatorBinding
    val viewModel : CardCreatorViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_true_monster_creator, container, false)
        val args = arguments?.let{ TrueMonsterCreatorFragmentArgs.fromBundle(it)}
        val protoMonster = args?.protomonster!!
        val layoutManager = LinearLayoutManager(this.context)
        val requirementAdapter = AttributeAdapter()
        requirementAdapter.submitList(Mana.values().asList())
        binding.manacost.layoutManager = layoutManager
        binding.manacost.adapter = requirementAdapter
        val layoutManager2 = LinearLayoutManager(this.context)
        val magicadapter = EffectsAdapter()
        magicadapter.submitList(Spell.values().asList())
        binding.effects.layoutManager = layoutManager2
        binding.effects.adapter = magicadapter
        binding.submit.setOnClickListener {
            val attributeRequirements = mutableMapOf<Mana, Int>()
            for(index in 0 until (binding.manacost.adapter as AttributeAdapter).itemCount){
                val viewHolder = binding.manacost.findViewHolderForAdapterPosition(index) as AttributeViewHolder
                if(viewHolder.getMana()!=null && viewHolder.getManaAmount()!=0) {
                    attributeRequirements[viewHolder.getMana()?:Mana.APEIRON] = viewHolder.getManaAmount()
                }
            }
            val spells = mutableMapOf<Spell, Int>()
            for(index in 0 until (binding.effects.adapter as EffectsAdapter).itemCount){
                val viewHolder = binding.effects.findViewHolderForAdapterPosition(index) as EffectViewHolder
                if(viewHolder.getSpell()!=null && viewHolder.getMpAmount()!=0) {
                    spells[viewHolder.getSpell()?:Spell.BOOSTATTACK] = viewHolder.getMpAmount()
                }
            }
            viewModel.insertCard(
                Monster(protoMonster.title, attributeRequirements, protoMonster.life, protoMonster.attack, protoMonster.defense, protoMonster.magicattack, protoMonster.magicdefense, protoMonster.mp, spells)
            )
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
                Toast.makeText(context, String.format("Card was successfully edited."), Toast.LENGTH_SHORT)
                activity?.supportFragmentManager?.beginTransaction()?.remove(this)
                    ?.commit()
            }
        })
        return binding.root
    }

}