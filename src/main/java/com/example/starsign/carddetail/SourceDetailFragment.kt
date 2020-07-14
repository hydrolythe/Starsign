package com.example.starsign.carddetail

import android.os.Bundle
import android.provider.ContactsContract
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
import com.example.starsign.database.Source
import com.example.starsign.databinding.SourceCreatorFragmentBinding
import com.example.starsign.observers.Observer
import org.koin.android.ext.android.inject

/**
 * A simple [Fragment] subclass.
 * Use the [SourceDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SourceDetailFragment : Fragment(), Observer {
    private lateinit var binding : SourceCreatorFragmentBinding
    private val viewModel: EditorViewModel by inject()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_source_detail, container, false)
        val args = arguments?.let{SourceDetailFragmentArgs.fromBundle(it)}
        val source = args?.card!!
        createView(source)
        return inflater.inflate(R.layout.fragment_source_detail, container, false)
    }

    override fun update(card: Card) {
        if(card is Source){
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

    private fun createView(source:Source){
        binding.sourcetitlelabel.text = source.title
        val manaAdapter = source.source.let{ManaDetailAdapter(it)}
        manaAdapter.submitList(source.source.keys.toList())
        binding.sourcetypes.adapter = manaAdapter
        binding.sourcecreatorbutton.setOnClickListener { SourceDetailFragmentDirections.actionSourceDetailFragmentToSourceEditorFragment(source) }
    }

}