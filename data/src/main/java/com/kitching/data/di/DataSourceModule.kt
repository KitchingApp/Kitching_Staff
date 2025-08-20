package com.kitching.data.di

import com.kitching.data.datasource.FcmTokenDataSource
import com.kitching.data.datasource.LoginDataSource
import com.kitching.data.datasource.OrderDataSource
import com.kitching.data.datasource.PrepDataSource
import com.kitching.data.datasource.RecipeDataSource
import com.kitching.data.datasource.ScheduleDataSource
import com.kitching.data.datasource.TeamDataSource
import com.kitching.data.datasource.UserTeamDataSource
import com.kitching.data.datasource.impl.FcmTokenDataSourceImpl
import com.kitching.data.datasource.impl.LoginDataSourceImpl
import com.kitching.data.datasource.impl.OrderDataSourceImpl
import com.kitching.data.datasource.impl.PrepDataSourceImpl
import com.kitching.data.datasource.impl.RecipeDataSourceImpl
import com.kitching.data.datasource.impl.ScheduleDataSourceImpl
import com.kitching.data.datasource.impl.TeamDataSourceImpl
import com.kitching.data.datasource.impl.UserTeamDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindLoginDataSource(
        loginDataSourceImpl: LoginDataSourceImpl
    ): LoginDataSource

    @Binds
    @Singleton
    abstract fun bindTeamDataSource(
        teamDataSourceImpl: TeamDataSourceImpl
    ): TeamDataSource

    @Binds
    @Singleton
    abstract fun bindUserTeamDataSource(
        userTeamDataSourceImpl: UserTeamDataSourceImpl
    ): UserTeamDataSource

    @Binds
    @Singleton
    abstract fun bindFcmTokenDataSource(
        fcmTokenDataSourceImpl: FcmTokenDataSourceImpl
    ): FcmTokenDataSource

    @Binds
    @Singleton
    abstract fun bindPrepDataSource(
        prepDataSourceImpl: PrepDataSourceImpl
    ): PrepDataSource

    @Binds
    @Singleton
    abstract fun bindRecipeDataSource(
        recipeDataSourceImpl: RecipeDataSourceImpl
    ): RecipeDataSource

    @Binds
    @Singleton
    abstract fun bindScheduleDataSource(
        scheduleDataSourceImpl: ScheduleDataSourceImpl
    ): ScheduleDataSource

    @Binds
    @Singleton
    abstract fun bindOrderDataSource(
        orderDataSourceImpl: OrderDataSourceImpl
    ): OrderDataSource
}