package com.kitching.data.di

import com.kitching.data.repository.FcmTokenRepositoryImpl
import com.kitching.data.repository.LoginRepositoryImpl
import com.kitching.data.repository.NotificationRepositoryImpl
import com.kitching.data.repository.OrderRepositoryImpl
import com.kitching.data.repository.PrepRepositoryImpl
import com.kitching.data.repository.RecipeRepositoryImpl
import com.kitching.data.repository.ScheduleRepositoryImpl
import com.kitching.data.repository.TeamRepositoryImpl
import com.kitching.domain.repository.FcmTokenRepository
import com.kitching.domain.repository.LoginRepository
import com.kitching.domain.repository.NotificationRepository
import com.kitching.domain.repository.OrderRepository
import com.kitching.domain.repository.PrepRepository
import com.kitching.domain.repository.RecipeRepository
import com.kitching.domain.repository.ScheduleRepository
import com.kitching.domain.repository.TeamRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindNotificationRepository(
        notificationRepositoryImpl: NotificationRepositoryImpl
    ): NotificationRepository

    @Binds
    @Singleton
    abstract fun bindLoginRepository(
        loginRepositoryImpl: LoginRepositoryImpl
    ): LoginRepository

    @Binds
    @Singleton
    abstract fun bindTeamRepository(
        teamRepositoryImpl: TeamRepositoryImpl
    ): TeamRepository

    @Binds
    @Singleton
    abstract fun bindFcmTokenRepository(
        fcmTokenRepositoryImpl: FcmTokenRepositoryImpl
    ): FcmTokenRepository

    @Binds
    @Singleton
    abstract fun bindPrepRepository(
        prepRepositoryImpl: PrepRepositoryImpl
    ): PrepRepository

    @Binds
    @Singleton
    abstract fun bindRecipeRepository(
        recipeRepositoryImpl: RecipeRepositoryImpl
    ): RecipeRepository

    @Binds
    @Singleton
    abstract fun bindScheduleRepository(
        scheduleRepositoryImpl: ScheduleRepositoryImpl
    ): ScheduleRepository

    @Binds
    @Singleton
    abstract fun bindOrderRepository(
        orderRepositoryImpl: OrderRepositoryImpl
    ): OrderRepository
}