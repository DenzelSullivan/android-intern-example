package com.sullivan.example.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sullivan.example.R
import com.sullivan.example.model.data.Category
import com.sullivan.example.view.adapter.CategoryAdapter
import com.sullivan.example.view.adapter.OnItemClickListener
import com.sullivan.example.viewmodel.CategoryViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    private lateinit var viewModel: CategoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = CategoryViewModel()

        val layoutManager = LinearLayoutManager(context)
        dataRecyclerView.layoutManager = layoutManager
        viewModel.categories.observe(viewLifecycleOwner, {
            dataRecyclerView.adapter = CategoryAdapter(it.categories, object : OnItemClickListener {
                override fun onItemClick(data: Category) {
                    setFragmentResult(
                        DetailFragment.REQUEST_KEY,
                        bundleOf(DetailFragment.CATEGORY_KEY to data)
                    )
                    findNavController().navigate(R.id.action_homeFragment_to_detailFragment)
                }
            })
        })
    }
}