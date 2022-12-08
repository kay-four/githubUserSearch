package ps.room.githubsearch.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ps.room.githubsearch.databinding.FragmentResultsBinding
import ps.room.githubsearch.repository.GithubRepository

class ResultsFragment : Fragment() {

    private var _binding: FragmentResultsBinding? = null
    private val binding get() = _binding!!

    private  val  viewModel:SearchViewModel by activityViewModels {
        SearchViewModelFactory(GithubRepository())}


    private lateinit var resultsRecycler: RecyclerView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentResultsBinding.inflate(inflater, container, false)

        resultsRecycler = binding.resultsRecyclerview
        val adapter = ResultsAdapter()



        resultsRecycler.layoutManager = LinearLayoutManager(requireContext())
        resultsRecycler.setHasFixedSize(true)
        resultsRecycler.adapter = adapter

        lifecycleScope.launch {
            viewModel.userPagingData.collectLatest {
                adapter.submitData(it)
            }
        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}