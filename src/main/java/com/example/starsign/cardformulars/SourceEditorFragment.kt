package com.example.starsign.cardformulars

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.starsign.R
import com.example.starsign.database.DatabaseSource
import com.example.starsign.database.Mana
import com.example.starsign.database.Source
import com.example.starsign.databinding.SourceCreatorFragmentBinding
import org.koin.android.ext.android.inject

class SourceEditorFragment : Fragment() {
    private lateinit var binding: SourceCreatorFragmentBinding
    private val viewModel: EditorViewModel by inject()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.source_creator_fragment, container, false)
        val args = arguments?.let{SourceEditorFragmentArgs.fromBundle(it)}
        val source = args?.card!!
        val requirementAdapter = AttributeAdapter(source.source)
        requirementAdapter.submitList(Mana.values().asList())
        binding.sourcetypes.adapter = requirementAdapter
        binding.sourcetitletext.text.insert(0, source.title)
        binding.sourcecreatorbutton.setOnClickListener {
            val attributeRequirements = mutableMapOf<Mana, Int>()
            for(index in 0 until (binding.sourcetypes.adapter as AttributeAdapter).itemCount){
                val viewHolder = binding.sourcetypes.findViewHolderForAdapterPosition(index) as AttributeViewHolder
                if(viewHolder.getMana()!=null) {
                    attributeRequirements[viewHolder.getMana()?: Mana.APEIRON] = viewHolder.getManaAmount()
                }
            }
            viewModel.updateCard(DatabaseSource(viewModel.getDbCard(source)?.cardid!!,binding.sourcetitletext.text.toString(), attributeRequirements))
        }
        viewModel.cardEditResult.observe(viewLifecycleOwner, Observer{
            if(it.exception != null){
                Toast.makeText(context, it.exception.message, Toast.LENGTH_SHORT).show()
            }
            if(it.success != null){
                Toast.makeText(context, String.format("Succesful edit."), Toast.LENGTH_SHORT).show()
                getActivity()?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
            }
        })
        return inflater.inflate(R.layout.source_creator_fragment, container, false)
    }
}