package com.abhisek.project.bookshelf.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.abhisek.project.bookshelf.data.local.dao.BookShelfDao
import com.abhisek.project.bookshelf.data.local.entity.UserEntity
import javax.inject.Singleton

@Singleton
@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class BookShelfDatabase : RoomDatabase() {

    abstract val bookShelfDao: BookShelfDao

    companion object {
        @Volatile
        private var INSTANCE: BookShelfDatabase? = null
        fun getInstance(context: Context): BookShelfDatabase {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    BookShelfDatabase::class.java,
                    "book_shelf_db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}