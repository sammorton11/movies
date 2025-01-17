package org.michaelbel.movies.common.coroutines.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.michaelbel.movies.common.coroutines.Dispatcher
import org.michaelbel.movies.common.coroutines.MoviesDispatchers

@Module
@InstallIn(SingletonComponent::class)
internal object CoroutinesDispatchersModule {

    @Provides
    @Dispatcher(MoviesDispatchers.Default)
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @Dispatcher(MoviesDispatchers.IO)
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Dispatcher(MoviesDispatchers.Main)
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    @Dispatcher(MoviesDispatchers.MainImmediate)
    fun provideMainImmediateDispatcher(): CoroutineDispatcher = Dispatchers.Main.immediate
}