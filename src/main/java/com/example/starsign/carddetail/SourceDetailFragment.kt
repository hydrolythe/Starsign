package com.example.starsign.carddetail

import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.starsign.R
import com.example.starsign.cardformulars.EditorViewModel
import com.example.starsign.database.*
import com.example.starsign.databinding.FragmentSourceDetailBinding
import com.example.starsign.databinding.SourceCreatorFragmentBinding
import org.koin.android.ext.android.inject

/**
 * A simple [Fragment] subclass.
 * Use the [SourceDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SourceDetailFragment : Fragment() {
    private lateinit var binding : FragmentSourceDetailBinding
    private val viewModel: EditorViewModel by inject()
    private lateinit var dbSource : DatabaseSource
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_source_detail, container, false)
        val args = arguments?.let{SourceDetailFragmentArgs.fromBundle(it)}
        val source = args?.card!!
        createView(source)
        viewModel.newCard.observe(viewLifecycleOwner, Observer{
            if(it is Source){
                createView(it)
            }
            else{
                Toast.makeText(context, String.format("Error: The name of the spell got modified while you tried to modify it."), Toast.LENGTH_SHORT)
                    .show()
                getActivity()?.supportFragmentManager?.beginTransaction()?.remove(this)
                    ?.commit()
            }
        })
        return binding.root
    }

    private fun createView(source:Source){
        binding.manatitletext.text = source.title
        val layoutManager = LinearLayoutManager(this.context)
        binding.manageneratorlist.layoutManager = layoutManager
        val manaAdapter = source.manas.let{ManaDetailAdapter(it)}
        manaAdapter.submitList(source.manas.keys.toList())
        binding.manageneratorlist.adapter = manaAdapter
        viewModel.dbCardResult.observe(viewLifecycleOwner, Observer{
            if(it.success!=null && it.success is DatabaseSource){
                dbSource = it.success
            }
            else{
                Toast.makeText(context, String.format("Error: The name of the monster got modified while you tried to modify it."), Toast.LENGTH_SHORT)
                    .show()
                getActivity()?.supportFragmentManager?.beginTransaction()?.remove(this)
                    ?.commit()
            }
        })
        binding.editbutton.setOnClickListener {
            it.findNavController().navigate(SourceDetailFragmentDirections.actionSourceDetailFragmentToSourceEditorFragment(dbSource))
        }
    }

}