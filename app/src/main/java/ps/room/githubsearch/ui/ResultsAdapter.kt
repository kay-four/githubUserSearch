package ps.room.githubsearch.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ps.room.githubsearch.R
import ps.room.githubsearch.data.User
import ps.room.githubsearch.databinding.SingleRecyclerItemBinding

class ResultsAdapter:PagingDataAdapter<User, ResultsAdapter.ResultsViewHolder>(diffCallback) {
    inner class ResultsViewHolder(val binding: SingleRecyclerItemBinding): RecyclerView.ViewHolder(binding.root){

    }

    companion object {
        val diffCallback =object : DiffUtil.ItemCallback<User>(){
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.login == newItem.login
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: ResultsViewHolder, position: Int) {
        val user = getItem(position)

        holder.binding.apply {
            userLogin.text = user?.login
            userLoginType.text = user?.type

            userAvatarUrl.load(user?.avatar_url){
                crossfade(true)
                crossfade(1000)
                error(R.drawable.error)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultsViewHolder {
        return ResultsViewHolder(
            SingleRecyclerItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }





}