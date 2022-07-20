package com.example.themovies.screens

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.themovies.databinding.ListItemBinding
import com.example.themovies.databinding.ViewFakeItemBinding
import com.example.themovies.network.data.*
import com.example.themovies.screens.movie.data.FakeAdHolder
import com.example.themovies.screens.movie.data.MovieHolder
import com.example.themovies.screens.people.PeopleFragment
import com.example.themovies.screens.people.data.PeopleHolder
import com.example.themovies.screens.tv.data.TvHolder

class RecordAdapter(
    private val recordClick: RecordClick
) : PagingDataAdapter<RecordType, RecyclerView.ViewHolder>(AnyDiffUtil()) {

    companion object {
        const val TYPE_FAKE_ITEM = 0
        const val TYPE_MOVIE_ITEM = 1
        const val TYPE_TV_ITEM = 2
        const val TYPE_PEOPLE_ITEM = 3
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is FakeAd -> TYPE_FAKE_ITEM
            is Movie -> TYPE_MOVIE_ITEM
            is TV -> TYPE_TV_ITEM
            else -> TYPE_PEOPLE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TYPE_FAKE_ITEM -> {
                return FakeAdHolder(
                    ViewFakeItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            TYPE_MOVIE_ITEM -> {
                return MovieHolder(
                    setListItemBinding(parent)
                )
            }
            TYPE_TV_ITEM -> {
                return TvHolder(
                    setListItemBinding(parent)
                )
            }
            else -> {
                return PeopleHolder(
                    setListItemBinding(parent)
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FakeAdHolder -> {
                holder.bind(getItem(position) as FakeAd)
            }
            is MovieHolder -> {
                val movie = getItem(position) as Movie
                movie.let { movieItem ->
                    holder.bind(movieItem)
                    holder.itemView.setOnClickListener {
                        recordClick.onRecordClickListener(movie.id ?: 0, Record.Movie)
                    }
                }
            }
            is TvHolder -> {
                val tv = getItem(position) as TV
                tv.let { tvItem ->
                    holder.bind(tvItem)
                    holder.itemView.setOnClickListener {
                        recordClick.onRecordClickListener(tv.id ?: 0, Record.TV)
                    }
                }
            }
            is PeopleHolder -> {
                val person = getItem(position) as Person
                person.let { peopleItem ->
                    holder.bind(peopleItem)
                    holder.itemView.setOnClickListener {
                        recordClick.onRecordClickListener(
                            person.id ?: 0,
                            Record.People,
                            PeopleFragment.CustomParameters(person.knownFor ?: listOf())
                        )
                    }
                }
            }
        }
    }

    private fun setListItemBinding(parent: ViewGroup): ListItemBinding {
        return ListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    }

    class AnyDiffUtil : DiffUtil.ItemCallback<RecordType>() {
        override fun areItemsTheSame(oldRecord: RecordType, newRecord: RecordType): Boolean {
            return when {
                oldRecord is FakeAd && newRecord is FakeAd -> {
                    oldRecord == newRecord
                }
                oldRecord is Movie && newRecord is Movie -> {
                    oldRecord.id == newRecord.id
                }
                oldRecord is TV && newRecord is TV -> {
                    oldRecord.id == newRecord.id
                }
                oldRecord is Person && newRecord is Person -> {
                    oldRecord.id == newRecord.id
                }
                else -> {
                    false
                }
            }
        }

        override fun areContentsTheSame(oldRecord: RecordType, newRecord: RecordType): Boolean {
            return when {
                oldRecord is FakeAd && newRecord is FakeAd -> {
                    oldRecord == newRecord
                }
                oldRecord is Movie && newRecord is Movie -> {
                    oldRecord == newRecord
                }
                oldRecord is TV && newRecord is TV -> {
                    oldRecord == newRecord
                }
                oldRecord is Person && newRecord is Person -> {
                    oldRecord == newRecord
                }
                else -> {
                    false
                }
            }
        }

    }
}
