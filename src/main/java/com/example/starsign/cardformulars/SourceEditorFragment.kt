package com.example.starsign.cardformulars

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
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
        binding.source = source
        val layoutManager = LinearLayoutManager(this.context)
        binding.sourcetypes.layoutManager = layoutManager
        val requirementAdapter = AttributeAdapter(source.manas)
        requirementAdapter.submitList(Mana.values().asList())
        binding.sourcetypes.adapter = requirementAdapter
        binding.sourcecreatorbutton.setOnClickListener {
            val attributeRequirements = mutableMapOf<Mana, Int>()
            for (index in 0 until (binding.sourcetypes.adapter as AttributeAdapter).itemCount) {
                val viewHolder =
                    binding.sourcetypes.findViewHolderForAdapterPosition(index) as AttributeViewHolder
                if (viewHolder.getMana() != null && viewHolder.getManaAmount()!=0) {
                    attributeRequirements[viewHolder.getMana() ?: Mana.APEIRON] =
                        viewHolder.getManaAmount()
                }
            }
            viewModel.updateCard(
                DatabaseSource(
                    source.cardid,
                    binding.sourcetitletext.text.toString(),
                    attributeRequirements
                )
            )
        }
        viewModel.cardEditResult.observe(viewLifecycleOwner, Observer {
            if (it.exception != null) {
                Toast.makeText(context, it.exception.message, Toast.LENGTH_SHORT).show()
            }
            if (it.success != null) {
                Toast.makeText(context, String.format("Successful edit."), Toast.LENGTH_SHORT)
                    .show()
            }
        })
        return binding.root
    }
}