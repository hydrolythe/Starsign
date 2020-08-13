package com.example.starsign.cardcreator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.starsign.R
import com.example.starsign.database.Magic
import com.example.starsign.database.Monster
import com.example.starsign.database.Source
import com.example.starsign.databinding.FragmentCardCreatorBinding
import org.koin.android.ext.android.inject

/**
 * A simple [Fragment] subclass.
 */
class CardCreatorFragment : Fragment() {

    private lateinit var binding: FragmentCardCreatorBinding
    private val viewModel : CardViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_card_creator, container, false)
        binding.createcardButton.setOnClickListener{
            it.findNavController().navigate(CardCreatorFragmentDirections.actionCardCreatorBoxToTypeOfCardFragment())
        }
        val adapter = CardCreatorAdapter(CardListener
        { card ->
            when(card) {
                is Monster -> binding.root.findNavController().navigate(CardCreatorFragmentDirections.actionCardCreatorBoxToMonsterDetailFragment(card))
                is Magic -> binding.root.findNavController().navigate(CardCreatorFragmentDirections.actionCardCreatorBoxToSpellDetailFragment(card))
                is Source -> binding.root.findNavController().navigate(CardCreatorFragmentDirections.actionCardCreatorBoxToSourceDetailFragment(card))
            }
        })
        val layoutManager = LinearLayoutManager(this.context)
        binding.cardlist.layoutManager = layoutManager
        viewModel.cardList.observe(viewLifecycleOwner, Observer{
            adapter.addHeaderAndSubmitList(it)
        })
        binding.cardlist.adapter = adapter
        return binding.root
    }
}