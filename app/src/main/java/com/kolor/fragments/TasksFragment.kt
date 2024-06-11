package com.kolor.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavGraph
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kolor.R
import com.kolor.data.entities.TaskEntity
import com.kolor.databinding.FragmentTasksBinding
import com.kolor.databinding.TaskRowBinding
import com.kolor.viewmodels.TasksViewModel

class TasksFragment : Fragment() {


    private val viewModel by activityViewModels<TasksViewModel>()


    @SuppressLint("RestrictedApi")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentTasksBinding.inflate(inflater).apply {
            this.vm = viewModel
            this.tasksRecycleView.adapter = TasksViewAdapter(emptyList())
        }


        viewModel.tasks.observe(viewLifecycleOwner){tasks ->
            (binding.tasksRecycleView.adapter as TasksViewAdapter).updateTasks(tasks)
        }


        return binding.root
    }


    @SuppressLint("RestrictedApi")
    fun printBackStack(){
        val breadcrumb = findNavController()
            .currentBackStack
            .value
            .map {
                it.destination
            }
            .filterNot {
                it is NavGraph
            }
            .joinToString(" > ") {
                it.displayName.split('/')[1]
            }
        println(breadcrumb)
    }




    inner class TasksViewAdapter(private var tasks  : List<TaskEntity>): RecyclerView.Adapter<TasksViewAdapter.TasksViewHolder>() {

        inner class TasksViewHolder(val binding: TaskRowBinding ) : RecyclerView.ViewHolder(binding.root){
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = TaskRowBinding.inflate(inflater, parent, false)
            return TasksViewHolder(binding)
        }

        override fun getItemCount(): Int {
                return tasks.size
        }

        override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {


            val task = tasks[position]
            holder.binding.taskReward.amount =  task.reward.toString()
            holder.binding.taskName.text = task.name
            holder.binding.taskProgressBar.progress = task.progress
            holder.binding.taskDescription.text = task.description
            val collectButton = holder.binding.collectButton


            fun setButtonColorGrey(){
                collectButton.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.unavalable_grey
                    )
                )
            }

            if(task.collected) {
                setButtonColorGrey()
                collectButton.text = getString(R.string.collected_button_text)
            }
            else{
                if(task.progress != 100){
                    setButtonColorGrey()
                }
            }

            collectButton.setOnClickListener(){
                viewModel.onCollect(task.id)
            }
        }


        fun updateTasks(newTasks : List<TaskEntity>){
            tasks = newTasks
            notifyDataSetChanged()
        }
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TasksFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}