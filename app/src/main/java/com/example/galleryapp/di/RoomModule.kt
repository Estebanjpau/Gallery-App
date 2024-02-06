package com.example.galleryapp.di

import android.content.Context
import androidx.room.Room
import com.example.galleryapp.data.dao.ImageDao
import com.example.galleryapp.data.db.ImageDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val RECIPE_DATABASE_NAME = "gallery_database"

    @Singleton
    @Provides
    fun providesRoom(@ApplicationContext context: Context): ImageDB =
        Room.databaseBuilder(context, ImageDB::class.java, RECIPE_DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun getImageDao(database: ImageDB): ImageDao{
        return database.getImageDao()
    }

}