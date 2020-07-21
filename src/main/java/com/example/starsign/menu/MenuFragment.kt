package com.example.starsign.menu


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.starsign.R
import com.example.starsign.databinding.FragmentMenuBinding

/**
 * A simple [Fragment] subclass.
 */
class MenuFragment : Fragment() {

    private lateinit var binding: FragmentMenuBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_menu, container, false)
        binding.cardcreatorbutton.setOnClickListener{
            view:View -> view.findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToCardCreatorBox())
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }


}
