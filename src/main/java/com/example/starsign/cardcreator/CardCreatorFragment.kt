package com.example.starsign.cardcreator


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.starsign.R
import com.example.starsign.database.Magic
import com.example.starsign.database.Monster
import com.example.starsign.database.Source
import com.example.starsign.databinding.FragmentCardCreatorBoxBinding
import com.example.starsign.menu.MenuFragmentDirections
import com.example.starsign.repository.CardRepository
import org.koin.android.ext.android.inject

/**
 * A simple [Fragment] subclass.
 */
class CardCreatorFragment : Fragment() {

    private lateinit var binding: FragmentCardCreatorBoxBinding
    private val viewModel : CardViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_card_creator_box, container, false)
        binding.createcardButton.setOnClickListener{
            CardCreatorFragmentDirections.actionCardCreatorBoxToTypeOfCardFragment()
        }
        val adapter = CardCreatorAdapter(CardListener
        { card ->
            when(card) {
                is Monster -> CardCreatorFragmentDirections.actionCardCreatorBoxToMonsterDetailFragment(card)
                is Magic -> CardCreatorFragmentDirections.actionCardCreatorBoxToSpellDetailFragment(card)
                is Source -> CardCreatorFragmentDirections.actionCardCreatorBoxToSourceDetailFragment(card)
            }
        });
        adapter.addHeaderAndSubmitList(viewModel.cards)
        binding.cardlist.adapter = adapter
        binding.deletecardsbutton.setOnClickListener {
            CardCreatorFragmentDirections.actionCardCreatorBoxToCarddeleteFragment()
        }
        return inflater.inflate(R.layout.fragment_card_creator_box, container, false)
    }

}
