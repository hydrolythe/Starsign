package com.example.starsign.cardformulars

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.starsign.R

class MonsterCreatorFragment : Fragment() {

    companion object {
        fun newInstance() = MonsterCreatorFragment()
    }

    private lateinit var viewModel: MonsterCreatorViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.monster_creator_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MonsterCreatorViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
