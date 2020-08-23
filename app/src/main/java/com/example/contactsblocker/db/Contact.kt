package com.example.contactsblocker.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.PropertyKey

@Entity
data class Contact(
    @PrimaryKey(autoGenerate = true) var uid : Int = 0,
    @ColumnInfo(name = "number") var number: String?
)