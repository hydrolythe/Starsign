package com.example.starsign.cardformulars

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.starsign.R

class SpellCreator : Fragment() {

    companion object {
        fun newInstance() = SpellCreator()
    }

    private lateinit var viewModel: SpellCreatorViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.spell_creator_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SpellCreatorViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
