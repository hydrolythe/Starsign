package com.example.starsign.cardcreator


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.starsign.R
import com.example.starsign.databinding.FragmentCardCreatorBoxBinding
import com.example.starsign.repository.CardRepository

/**
 * A simple [Fragment] subclass.
 */
class CardCreatorFragment : Fragment() {

    private lateinit var viewModel: CardCreatorViewModel
    private lateinit var binding: FragmentCardCreatorBoxBinding
    val viewModelRepository = CardRepository()
    val viewModelFactory = CardCreatorViewModelFactory(viewModelRepository)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CardCreatorViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_card_creator_box, container, false)
        binding.createcardButton.setOnClickListener{
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_card_creator_box, container, false)
    }


}
