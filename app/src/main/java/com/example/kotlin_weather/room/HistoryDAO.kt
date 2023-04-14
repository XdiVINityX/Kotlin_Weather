package com.example.kotlin_weather.room

import androidx.room.*
@Dao
interface HistoryDAO {
    @Query("SELECT * FROM HistoryEntity")
    fun all():List<HistoryEntity>

    @Query("SELECT * FROM HistoryEntity WHERE name LIKE:name")
    fun getDataByWord(name:String):List<HistoryEntity>

    @Delete
    fun delete(entity: HistoryEntity)

    @Update
    fun update(entity: HistoryEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: HistoryEntity)

}