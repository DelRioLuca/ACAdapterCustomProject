package com.example.acadaptercustomproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.acadaptercustomproject.databinding.FragmentAcAdapteCustomProjectBinding

class ACAdapterCustomProjectFragment : Fragment(), ACAdapterCustomProjectListener {

    lateinit var binding: FragmentAcAdapteCustomProjectBinding

    private val viewModel: ACAdapterCustomProjectViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAcAdapteCustomProjectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()
        viewModel.loadList(requireContext())
    }

    private fun setupObserver() {
        viewModel.useCaseLiveData.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { useCase ->
                when (useCase) {
                    is ACAdapterCustomProjectViewModel.UseCaseLiveData.setListItems -> {
                        binding.fragmentAcAdapterCustomProjectTextview.setAdapter(
                            AutoCompleteAdapter(
                                context = requireContext(),
                                allTypes = useCase.items,
                                listener = this,
                                view = R.layout.simple_list_item
                            )
                        )
//                        binding.fragmentAcAdapterCustomProjectTextview.setOnItemClickListener { parent, view, position, id ->
//                            viewModel.setIcon(useCase.items[position])
//                        }
                    }
                    is ACAdapterCustomProjectViewModel.UseCaseLiveData.setIcon -> {
                        Glide.with(binding.fragmentAcAdapterCustomProjectIconIV)
                            .load(useCase.icon)
                            .into(binding.fragmentAcAdapterCustomProjectIconIV)
                    }
                    is ACAdapterCustomProjectViewModel.UseCaseLiveData.setText -> {
                        binding.fragmentAcAdapterCustomProjectTextview.setText(useCase.itemText)
                    }
                }
            }
        }
    }

    override fun onItemSelected(item: ACAdapterCustomProjectUIModel) {
        viewModel.onPositionTypeSelected(item)
    }
}