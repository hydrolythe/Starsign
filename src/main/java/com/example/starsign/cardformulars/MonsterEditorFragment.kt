package com.example.starsign.cardformulars

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import android.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.starsign.R
import com.example.starsign.database.*
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
        binding.titletextfield.setText(monster.title)
        val texts = listOf(
            listOf(binding.lifetextfield, monster.life),
            listOf(binding.attacktextfield, monster.attack),
            listOf(binding.defensetextfield, monster.defense),
            listOf(binding.magicattacktextfield, monster.magicattack),
            listOf(binding.magicdefensefield, monster.magicdefense),
            listOf(binding.mptextfield, monster.mp)
        )
        texts.forEach { t ->
            val edited = t.get(0)
            val text = t.get(1)
            if (edited is EditText && text is Int) {
                edited.setText(text.toString())
            }
        }
        binding.submitmonster.setOnClickListener {
            val protoMonster = ProtoMonster(binding.titletextfield.text.toString(), Integer.parseInt(binding.lifetextfield.text.toString()), Integer.parseInt(binding.attacktextfield.text.toString()), Integer.parseInt(binding.defensetextfield.text.toString()), Integer.parseInt(binding.magicattacktextfield.text.toString()), Integer.parseInt(binding.magicdefensefield.text.toString()), Integer.parseInt(binding.mptextfield.text.toString()))
            it.findNavController().navigate(MonsterEditorFragmentDirections.actionMonsterEditorFragmentToTrueMonsterEditorFragment(protoMonster, monster))
        }
        viewModel.cardEditResult.observe(viewLifecycleOwner, Observer {
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