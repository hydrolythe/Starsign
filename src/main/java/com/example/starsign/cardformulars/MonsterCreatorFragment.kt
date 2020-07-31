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
import androidx.navigation.findNavController

import com.example.starsign.R
import com.example.starsign.database.*
import com.example.starsign.databinding.MonsterCreatorFragmentBinding
import org.koin.android.ext.android.inject

class MonsterCreatorFragment : Fragment() {

    private lateinit var binding : MonsterCreatorFragmentBinding
    private val viewModel : CardCreatorViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.monster_creator_fragment, container, false)
        binding.submitmonster.setOnClickListener {
            val protoMonster = ProtoMonster(binding.titletextfield.text.toString(), Integer.parseInt(binding.lifetextfield.text.toString()), Integer.parseInt(binding.attacktextfield.text.toString()), Integer.parseInt(binding.defensetextfield.text.toString()), Integer.parseInt(binding.magicattacktextfield.text.toString()), Integer.parseInt(binding.magicdefensefield.text.toString()), Integer.parseInt(binding.mptextfield.text.toString()))
            it.findNavController().navigate(MonsterCreatorFragmentDirections.actionMonsterCreatorFragmentToTrueMonsterCreatorFragment(protoMonster))
        }
        viewModel.cardCreationResult.observe(viewLifecycleOwner, Observer {
            if(it.success!=null) {
                val texts = listOf<Editable>(
                    binding.titletextfield.text,
                    binding.lifetextfield.text,
                    binding.attacktextfield.text,
                    binding.defensetextfield.text,
                    binding.magicattacktextfield.text,
                    binding.magicdefensefield.text,
                    binding.mptextfield.text
                )
                texts.forEach { edit -> edit.clear() }
            }
        })
        return binding.root
    }
}
