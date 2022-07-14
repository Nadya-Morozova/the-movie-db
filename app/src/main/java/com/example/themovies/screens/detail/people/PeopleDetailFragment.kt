package com.example.themovies.screens.detail.people

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.themovies.database.data.Like
import com.example.themovies.databinding.FragmentDetailsPeopleBinding
import com.example.themovies.notifications.NotificationService
import com.example.themovies.screens.activities.Loading
import com.example.themovies.screens.settings.SettingsFragment
import com.like.LikeButton
import com.like.OnLikeListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PeopleDetailFragment : Fragment() {

    var loading: Loading? = null
    @Inject
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding: FragmentDetailsPeopleBinding
    private val viewModel by viewModels<PeopleDetailsViewModel>()
    private val id by navArgs<PeopleDetailFragmentArgs>()

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
        val value = sharedPreferences.getBoolean(SettingsFragment.NOTIFICATION_LIKE, false)
        val intent = Intent(requireContext(), NotificationService::class.java)

        initObservers(value, intent)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.getPeople(id.idPeople)
        viewModel.isLiked(id.idPeople)
    }

    private fun initObservers(value: Boolean?, intent: Intent) {
        viewModel.people.observe(viewLifecycleOwner) { people ->
            loading?.hideLoading()
            binding.btnLike.setOnLikeListener(object : OnLikeListener {
                override fun liked(likeButton: LikeButton?) {
                    viewModel.insertRecord(Like(idRecord = people.id, type = 2))

                    if (value == true) {
                        intent.putExtra(SettingsFragment.NAME, people.name)
                        this@PeopleDetailFragment.activity?.startForegroundService(intent)
                    }
                }

                override fun unLiked(likeButton: LikeButton?) {
                    viewModel.deleteRecord(people.id ?: 0)
                }

            })

        }
    }

}


