package com.example.starsign.cardcreator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.starsign.R
import com.example.starsign.cardcreator.CardCreatorAdapter
import com.example.starsign.cardcreator.CardListener
import com.example.starsign.cardcreator.CardViewModel
import com.example.starsign.database.Card
import com.example.starsign.databinding.FragmentDeleteBinding
import org.koin.android.ext.android.inject

/**
 * A fragment representing a list of Items.
 */
class CarddeleteFragment : Fragment() {

    private lateinit var binding: FragmentDeleteBinding
    private val viewModel: CardViewModel by inject()
    private var deletionList = mutableListOf<Card>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_delete, container, false)
        val adapter = CardCreatorAdapter(CardListener {
            if(!deletionList.contains(it)){
                deletionList.add(it)
            } else{
                deletionList.remove(it)
            }
        })
        adapter.addHeaderAndSubmitList(viewModel.cards)
        binding.cardView.adapter = adapter
        binding.cardRemovalbutton.setOnClickListener {
            it -> viewModel.deleteCards(deletionList)
        }
        viewModel.cardResult.observe(viewLifecycleOwner, Observer{
            if(it.exception != null){
                Toast.makeText(context, it.exception.message, Toast.LENGTH_SHORT).show()
            }
            if(it.success != null){
                Toast.makeText(context, String.format("Succesful edit."), Toast.LENGTH_SHORT).show()
                getActivity()?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
            }
        })
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}