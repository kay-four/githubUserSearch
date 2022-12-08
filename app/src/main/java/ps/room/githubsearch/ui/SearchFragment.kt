package ps.room.githubsearch.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import ps.room.githubsearch.R
import ps.room.githubsearch.databinding.FragmentSearchBinding
import ps.room.githubsearch.repository.GithubRepository

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by activityViewModels {
        SearchViewModelFactory(
            GithubRepository()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSearchBinding.inflate(inflater, container, false)



        binding.buttonSubmit.setOnClickListener {
            if (binding.enterLoginSearch.text!!.isNotBlank()) {
                val login = binding.enterLoginSearch.text.toString()
                lifecycleScope.launch {
                    viewModel.fetchUserLogins(login)
                }

                findNavController().navigate(R.id.action_searchFragment_to_resultsFragment)

            } else {
                binding.enterLoginSearch.error = "Please enter user login"
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

