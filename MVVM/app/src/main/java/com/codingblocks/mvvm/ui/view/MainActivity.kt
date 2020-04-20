package com.codingblocks.mvvm.ui.view

import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.codingblocks.mvvm.R
import com.codingblocks.mvvm.data.models.User
import com.codingblocks.mvvm.ui.adapter.UsersAdapter
import com.codingblocks.mvvm.ui.viewmodel.GithubViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val vm by lazy {
        ViewModelProvider(this).get(GithubViewModel::class.java)
    }
    val list = arrayListOf<User>()
    val originalList = arrayListOf<User>()
    val adapter = UsersAdapter(list)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usersRv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    findUsers(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    findUsers(it)
                }
                return true
            }

        })
        searchView.setOnCloseListener {
            list.clear()
            list.addAll(originalList)
            adapter.notifyDataSetChanged()

//            vm.users.value?.let { list.addAll(it) }
            return@setOnCloseListener true
        }

        vm.fetchUsers()

        vm.users.observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                list.addAll(it)
                originalList.addAll(it)
                adapter.notifyDataSetChanged()
            }
        })
    }

    private fun findUsers(query: String) {
        vm.searchUsers(query).observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                list.clear()
                list.addAll(it)
                adapter.notifyDataSetChanged()
            }
        })
    }
}
