package com.sullivan.example.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sullivan.example.R
import com.sullivan.example.model.data.Category
import com.sullivan.example.view.adapter.DataAdapter
import com.sullivan.example.view.adapter.OnItemClickListener
import com.sullivan.example.viewmodel.FoodViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    private lateinit var viewModel: FoodViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = FoodViewModel()

        val layoutManager = LinearLayoutManager(context)
        dataRecyclerView.layoutManager = layoutManager
        viewModel.categories.observe(viewLifecycleOwner, Observer {
            dataRecyclerView.adapter = DataAdapter(it.categories, object : OnItemClickListener {
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment()
    }
}