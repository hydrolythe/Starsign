package com.example.starsign.typeofcardmenu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController

import com.example.starsign.R
import com.example.starsign.databinding.TypeOfCardFragmentBinding

class TypeOfCardFragment : Fragment() {
    private lateinit var binding : TypeOfCardFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.type_of_card_fragment, container, false)
        binding.monstercreatorbutton.setOnClickListener {
            view: View -> view.findNavController().navigate(TypeOfCardFragmentDirections.actionTypeOfCardFragmentToMonsterCreatorFragment())
        }
        binding.spellcreatorbutton.setOnClickListener {
            view : View -> view.findNavController().navigate(TypeOfCardFragmentDirections.actionTypeOfCardFragmentToSpellCreator())
        }
        binding.sourcecreatorbutton.setOnClickListener {
            view : View -> view.findNavController().navigate(TypeOfCardFragmentDirections.actionTypeOfCardFragmentToSourceCreator())
        }
        return inflater.inflate(R.layout.type_of_card_fragment, container, false)
    }

}
