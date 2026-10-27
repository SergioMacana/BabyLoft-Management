package com.babyloft.management.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.babyloft.management.data.local.entity.AttributeEntity
import com.babyloft.management.data.local.entity.AttributeType
import kotlinx.coroutines.flow.Flow

@Dao
interface AttributeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(attribute: AttributeEntity): Long

    @Update
    suspend fun update(attribute: AttributeEntity)

    @Delete
    suspend fun delete(attribute: AttributeEntity)

    @Query("SELECT * FROM attributes WHERE type = :type ORDER BY displayOrder ASC, id ASC")
    fun getAttributesByType(type: AttributeType): Flow<List<AttributeEntity>>

    @Query("SELECT COUNT(*) FROM attributes")
    suspend fun count(): Int
}
