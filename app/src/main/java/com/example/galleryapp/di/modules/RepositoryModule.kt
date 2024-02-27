package com.example.galleryapp.di.modules

import com.example.galleryapp.data.LSRepository
import com.example.galleryapp.data.RoomRepository
import com.example.galleryapp.di.LSUseCases
import com.example.galleryapp.di.RoomUseCases
import com.example.galleryapp.domain.usecases.CapturePhotoUseCase
import com.example.galleryapp.domain.usecases.DeleteImageFromDBUseCase
import com.example.galleryapp.domain.usecases.DeleteImageFromLSUseCase
import com.example.galleryapp.domain.usecases.GetAllImageUseCase
import com.example.galleryapp.domain.usecases.GetImageByIdUseCase
import com.example.galleryapp.domain.usecases.GetSizePhotoUseCase
import com.example.galleryapp.domain.usecases.SaveImageCopyInLS
import com.example.galleryapp.domain.usecases.SavePhotoInDBUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRoomUseCases(galleryRepository: RoomRepository) = RoomUseCases(
        GetAllImageUseCase(galleryRepository),
        GetImageByIdUseCase(galleryRepository),
        DeleteImageFromDBUseCase(galleryRepository),
        SavePhotoInDBUseCase(galleryRepository)
    )

    @Singleton
    @Provides
    fun provideLSUseCases(galleryRepository: LSRepository) = LSUseCases(
        CapturePhotoUseCase(galleryRepository),
        DeleteImageFromLSUseCase(galleryRepository),
        SaveImageCopyInLS(galleryRepository),
        GetSizePhotoUseCase(galleryRepository)
    )
}