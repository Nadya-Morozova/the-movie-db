package com.example.themovies.screens.detail.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.themovies.databinding.FragmentDetailsBinding
import com.example.themovies.network.ConfigurationRepository
import com.example.themovies.screens.movie.MovieRepository
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    companion object {
        private const val MOVIE_ID = "movie_id"
        fun newInstance(id: Int): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = Bundle().apply {
                putInt(MOVIE_ID, id)
            }
            return fragment
        }
    }

    private var id: Int? = null
    private lateinit var binding: FragmentDetailsBinding
    private val viewModel by viewModels<DetailsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = arguments?.getInt(MOVIE_ID)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)

        showDetailsAboutMovie()

        return binding.root
    }

    private fun showDetailsAboutMovie() {
        viewModel.movie.observe(viewLifecycleOwner) { movie ->
            binding.apply {
                tvTitleOfTheMovie.text = movie.title
                tvOriginalTitle.text = movie.originalTitle
                Glide
                    .with(requireContext())
                    .load("${MovieRepository.URL}${ConfigurationRepository.sizeOfPoster}${movie.backdropPath}")
                    .into(image)
                when {
                    movie.budget.toString() == "0" && movie.revenue.toString() == "0" -> {
                        tvBudget.visibility = View.GONE
                        tvBudgetText.visibility = View.GONE
                        tvRevenue.visibility = View.GONE
                        tvRevenueText.visibility = View.GONE
                    }
                    movie.revenue.toString() == "0" -> {
                        tvRevenue.visibility = View.GONE
                        tvRevenueText.visibility = View.GONE
                        tvBudget.text = movie.budget.toString()
                    }
                    movie.budget.toString() == "0" -> {
                        tvBudget.visibility = View.GONE
                        tvBudgetText.visibility = View.GONE
                        tvRevenue.text = movie.revenue.toString()
                    }
                    else -> {
                        tvBudget.text = movie.budget.toString()
                        tvRevenue.text = movie.revenue.toString()
                    }
                }
                tvDescriptionMovie.text = movie.description
            }
        }

        viewModel.getMovie(id ?: 0)
    }
}