package com.example.ualaapp.presentation.viewmodel

import androidx.lifecycle.*
import com.example.ualaapp.data.models.DataCities
import com.example.ualaapp.domain.usecase.GetFavCitiesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author Axel Sanchez
 */
class FavViewModel(
    private val getFavCitiesUseCase: GetFavCitiesUseCase
) : ViewModel() {

    private var listData: MutableLiveData<DataCities> =
        MutableLiveData<DataCities>()

    private fun setListData(result: DataCities) {
        listData.postValue(result)
    }

    fun reset() {
        listData = MutableLiveData<DataCities>()
    }

    fun getFavCities() {
        viewModelScope.launch(Dispatchers.Main) {
            setListData(getFavCitiesUseCase.call())
        }
    }

    fun getFavCitiesLiveData(): LiveData<DataCities> {
        return listData
    }

    class FavViewModelFactory(
        private val getFavCitiesUseCase: GetFavCitiesUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(GetFavCitiesUseCase::class.java)
                .newInstance(getFavCitiesUseCase)
        }
    }
}