package com.example.starsign.carddetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.starsign.R
import com.example.starsign.cardformulars.EditorViewModel
import com.example.starsign.database.*
import com.example.starsign.databinding.MonsterDetailFragmentBinding
import com.example.starsign.network.NetworkMonster
import org.koin.android.ext.android.inject

class MonsterDetailFragment : Fragment() {

    private lateinit var binding : MonsterDetailFragmentBinding
    private val viewModel: EditorViewModel by inject()
    private lateinit var dbMonster : NetworkMonster
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.monster_detail_fragment, container, false)
        val args = arguments?.let{MonsterDetailFragmentArgs.fromBundle(it)}
        val monster = args?.card!!
        createView(monster)
        viewModel.newCard.observe(viewLifecycleOwner, Observer{
            if(it is Monster){
                createView(it)
            }
            else{
                Toast.makeText(context, String.format("Error: The name of the monster got modified while you tried to modify it."), Toast.LENGTH_SHORT)
                    .show()
                activity?.supportFragmentManager?.beginTransaction()?.remove(this)
                    ?.commit()
            }
        })
        return binding.root
    }

    private fun createView(monster:Monster){
        binding.editbutton.isEnabled = false
        binding.titlelabel.text = monster.title
        binding.healthtext.text = monster.life.toString()
        binding.attacktext.text = monster.attack.toString()
        binding.defensetext.text = monster.defense.toString()
        binding.magicattacktext.text = monster.magicattack.toString()
        binding.magicdefensetext.text = monster.magicdefense.toString()
        binding.mptext.text = monster.mp.toString()
        viewModel.getDbCard<NetworkMonster>(monster.title)
        viewModel.dbCardResult.observe(viewLifecycleOwner, Observer{
            if(it.success!=null && it.success is NetworkMonster){
                dbMonster = it.success
                binding.editbutton.isEnabled = true
            }
            else{
                Toast.makeText(context, String.format("Error: The name of the monster got modified while you tried to modify it."), Toast.LENGTH_SHORT)
                    .show()
                activity?.supportFragmentManager?.beginTransaction()?.remove(this)
                    ?.commit()
            }
        })
        binding.editbutton.setOnClickListener {
            it.findNavController().navigate(MonsterDetailFragmentDirections.actionMonsterDetailFragmentToTrueMonsterFragment(dbMonster))
        }
    }

}