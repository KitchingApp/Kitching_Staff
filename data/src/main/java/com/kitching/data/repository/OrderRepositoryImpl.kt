package com.kitching.data.repository

import com.kitching.data.datasource.OrderDataSource
import com.kitching.data.datasource.impl.OrderDataSourceImpl
import com.kitching.domain.entities.Order
import com.kitching.domain.entities.OrderCategory
import com.kitching.domain.repository.OrderRepository
import com.kitching.domain.util.AppResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class OrderRepositoryImpl(private val dataSource: OrderDataSource = OrderDataSourceImpl()) : OrderRepository {
    override fun getOrderCategories(teamId: String): Flow<AppResult<List<OrderCategory>>> = flow {
        emit(AppResult.Loading)
        try {
            val orderCategories = dataSource.getOrderCategories(teamId)
            emit(AppResult.Success(orderCategories.map { it.toDomain() }))
        } catch (e: Exception) {
            emit(AppResult.Failure(e))
        }
    }

    override fun getOrderItems(teamId: String): Flow<AppResult<List<Order>>> = flow {
        emit(AppResult.Loading)
        try {
            val orderItems = dataSource.getOrderItems(teamId)
            emit(AppResult.Success(orderItems.map { it.toDomain() }))
        } catch (e: Exception) {
            emit(AppResult.Failure(e))
        }
    }
}