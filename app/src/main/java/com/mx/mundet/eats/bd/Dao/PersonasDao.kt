package com.mx.mundet.eats.bd.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mx.mundet.eats.bd.Entity.PersonasEntity

@Dao
interface PersonasDao {

    @Insert
    fun insert(req: PersonasEntity)

    @Query("SELECT * FROM personas WHERE id=:userId")
    fun queryPersona(userId : Int): PersonasEntity

    @Query("SELECT * FROM personas ORDER BY id DESC")
    fun queryPersonas(): List<PersonasEntity>
}