package com.example.themovies.screens.detail.people

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.themovies.database.data.Like
import com.example.themovies.databinding.FragmentDetailsPeopleBinding
import com.example.themovies.screens.activities.Loading
import com.like.LikeButton
import com.like.OnLikeListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PeopleDetailFragment : Fragment() {

    var loading: Loading? = null
    private lateinit var binding: FragmentDetailsPeopleBinding
    private val viewModel by viewModels<PeopleDetailsViewModel>()
    private val args by navArgs<PeopleDetailFragmentArgs>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loading = context as Loading
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsPeopleBinding.inflate(inflater, container, false)

        showDetailsAboutMovie()

        return binding.root
    }

    override fun onDetach() {
        super.onDetach()
        loading = null
    }

    private fun showDetailsAboutMovie() {
        initObservers()
        swipePager()

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.getPeople(args.idPeople)
        viewModel.getCastOfMovie(args.idPeople)
        viewModel.getCastOfTv(args.idPeople)
        viewModel.getImages(args.idPeople)
        viewModel.isLiked(args.idPeople)
    }

    private fun swipePager() {
        binding.apply {
            btnLeft.setOnClickListener {
                if (pagerForKnownPerson.currentItem > pagerForKnownPerson.left) {
                    pagerForKnownPerson.setCurrentItem(pagerForKnownPerson.currentItem - 1, true)
                }
            }
            btnRight.setOnClickListener {
                if (pagerForKnownPerson.currentItem < pagerForKnownPerson.right) {
                    pagerForKnownPerson.setCurrentItem(pagerForKnownPerson.currentItem + 1, true)
                }
            }
        }
    }

    private fun initObservers() {
        viewModel.person.observe(viewLifecycleOwner) { people ->
            loading?.hideLoading()

            (requireActivity() as AppCompatActivity).supportActionBar?.title = people.name

            binding.btnLike.setOnLikeListener(object : OnLikeListener {
                override fun liked(likeButton: LikeButton?) {
                    viewModel.insertRecord(Like(idRecord = people.id, type = 2))
                }

                override fun unLiked(likeButton: LikeButton?) {
                    viewModel.deleteRecord(people.id ?: 0)
                }

            })

        }

    }

}


