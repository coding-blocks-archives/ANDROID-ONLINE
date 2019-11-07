package com.codingblocks.roomdb

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    @Insert
    suspend fun insert(user:User)

    @Insert
    fun insertAll(list:List<User>)

    @Delete
    fun delete(user: User)

    @Query("SELECT * FROM User")
    fun getAllUser() : LiveData<List<User>>

    @Query("Select * From User WHERE age >=:age")
    fun getUserWithAge(age:Int) : List<User>

}