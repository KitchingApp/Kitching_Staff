package com.kitching.main.view.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kitching.domain.entities.Order
import com.kitching.domain.entities.OrderCategory
import com.kitching.domain.repository.OrderRepository
import com.kitching.domain.util.AppResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class OrderViewModel(private val orderRepository: OrderRepository) : ViewModel() {

    private val _orderCategories = MutableStateFlow<AppResult<List<OrderCategory>>>(AppResult.Initial)
    val orderCategories get() = _orderCategories.asStateFlow()

    fun fetchOrderCategories(teamId: String) {
        viewModelScope.launch {
            orderRepository.getOrderCategories(teamId)
                .collectLatest {
                    _orderCategories.value = it
                }
        }
    }

    private val _orderItems = MutableStateFlow<AppResult<List<Order>>>(AppResult.Initial)
    val orderItems get() = _orderItems.asStateFlow()

    fun fetchOrderItems(teamId: String) {
        viewModelScope.launch {
            orderRepository.getOrderItems(teamId)
                .collectLatest {
                    _orderItems.value = it
                }
        }
    }
}