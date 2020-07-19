package com.example.starsign.carddetail

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.starsign.R
import com.example.starsign.cardformulars.EditorViewModel
import com.example.starsign.database.Card
import com.example.starsign.database.Mana
import com.example.starsign.database.Monster
import com.example.starsign.database.Spell
import com.example.starsign.databinding.MonsterCreatorFragmentBinding
import com.example.starsign.databinding.MonsterDetailFragmentBinding
import com.example.starsign.observers.Observer
import org.koin.android.ext.android.inject
import java.util.*

class MonsterDetailFragment() : Fragment(), Observer {

    private lateinit var binding : MonsterDetailFragmentBinding
    private val viewModel: EditorViewModel by inject()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.monster_detail_fragment, container, false)
        val args = arguments?.let{MonsterDetailFragmentArgs.fromBundle(it)}
        val monster = args?.card!!
        createView(monster)
        return inflater.inflate(R.layout.monster_detail_fragment, container, false)
    }

    override fun update(card: Card) {
        if(card is Monster){
            createView(card)
        }
    }

    override fun onPause() {
        viewModel.registerObserver(this)
        super.onPause()
    }

    override fun onResume() {
        viewModel.removeObserver(this)
        super.onResume()
    }

    private fun createView(monster:Monster){
        binding.titlelabel.text = monster.title
        val manaAdapter = monster.manarequirements.let { ManaDetailAdapter(it) }
        manaAdapter.submitList(monster.manarequirements.keys.toList())
        binding.manarequirementslist.adapter = manaAdapter
        binding.healthtext.text = monster.life.toString()
        binding.attacktext.text = monster.attack.toString()
        binding.defensetext.text = monster.defense.toString()
        binding.magicattacktext.text = monster.magicattack.toString()
        binding.magicdefensetext.text = monster.magicdefense.toString()
        binding.mptext.text = monster.mp.toString()
        if(monster.spells!=null) {
            val spellAdapter = monster.spells.let { SpellDetailAdapter(it) }
            spellAdapter.submitList(monster.spells.keys.toList())
            binding.spelllist.adapter = spellAdapter
        }
        else{
            binding.spellrow.isEnabled = false
        }
        binding.editbutton.setOnClickListener { MonsterDetailFragmentDirections.actionMonsterDetailFragmentToMonsterEditorFragment(monster) }
    }

}