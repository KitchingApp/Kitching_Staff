package com.kitching.data.repository

import com.kitching.data.datasource.OrderDataSource
import com.kitching.data.datasource.impl.OrderDataSourceImpl
import com.kitching.domain.repository.OrderRepository
import com.kitching.domain.util.AppResult
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class OrderRepositoryImpl(private val dataSource: OrderDataSource = OrderDataSourceImpl()) : OrderRepository {
    override suspend fun getOrderCategories(teamId: String) = flow {
        emit(AppResult.Loading)

        val orderCategories = dataSource.getOrderCategories(teamId)

        emit(AppResult.Success(orderCategories.map { it.toDomain() }))
    }.catch {
        emit(AppResult.Failure(it))
    }

    override suspend fun getOrderItems(teamId: String) = flow {
        emit(AppResult.Loading)

        val orderItems = dataSource.getOrderItems(teamId)

        emit(AppResult.Success(orderItems.map { it.toDomain() }))
    }.catch {
        emit(AppResult.Failure(it))
    }
}