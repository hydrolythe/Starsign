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
import com.example.starsign.R
import com.example.starsign.cardformulars.EditorViewModel
import com.example.starsign.database.*
import com.example.starsign.databinding.SourceCreatorFragmentBinding
import org.koin.android.ext.android.inject

/**
 * A simple [Fragment] subclass.
 * Use the [SourceDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SourceDetailFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_source_detail, container, false)
    }

    private fun createView(source:Source){
        binding.sourcetitlelabel.text = source.title
        val manaAdapter = source.source.let{ManaDetailAdapter(it)}
        manaAdapter.submitList(source.source.keys.toList())
        binding.sourcetypes.adapter = manaAdapter
        val dbSource = viewModel.getDbCard<DatabaseSource>(source.title)
        binding.sourcecreatorbutton.setOnClickListener {
            if(dbSource!=null){
                SourceDetailFragmentDirections.actionSourceDetailFragmentToSourceEditorFragment(dbSource)
            }
            else{
                Toast.makeText(context, String.format("Error: The name of the monster got modified while you tried to modify it."), Toast.LENGTH_SHORT)
                    .show()
                getActivity()?.supportFragmentManager?.beginTransaction()?.remove(this)
                    ?.commit()
            }
        }
    }

}