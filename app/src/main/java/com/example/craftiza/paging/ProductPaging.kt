package com.example.craftiza.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.craftiza.data.Product
import com.example.craftiza.repository.HomeRepository

class ProductPaging(
    private val homeRepo: HomeRepository
) : PagingSource<Int, Product>() {
    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition;
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        return try {
            val page = params.key ?: 1
            val limit = 20;
            val offset = (page -1) * limit;
            val response =  homeRepo.getProducts(offset,limit);
            if (response.isSuccessful) {
                val products = response.body() ?: emptyList()
                LoadResult.Page(
                    data = products,
                    prevKey = if (page == 0) null else page - params.loadSize,
                    nextKey = if (products.isEmpty()) null else page + params.loadSize
                )
            } else {
                LoadResult.Error(Exception("Error fetching data"))
            }
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }
}