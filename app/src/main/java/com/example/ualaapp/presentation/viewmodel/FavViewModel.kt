package com.example.ualaapp.presentation.viewmodel

import androidx.lifecycle.*
import com.example.ualaapp.data.models.City
import com.example.ualaapp.domain.usecase.GetAllCitiesUseCase
import com.example.ualaapp.domain.usecase.GetFavCitiesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author Axel Sanchez
 */
class FavViewModel(
    private val getFavCitiesUseCase: GetFavCitiesUseCase
) : ViewModel() {

    private var listData: MutableLiveData<List<City>> =
        MutableLiveData<List<City>>()

    fun setListData(result: List<City>) {
        listData.postValue(result)
    }

    fun reset() {
        listData = MutableLiveData<List<City>>()
    }

    fun getFavCities() {
        viewModelScope.launch(Dispatchers.IO) {
            setListData(getFavCitiesUseCase.call())
        }
    }

    fun getFavCitiesLiveData(): LiveData<List<City>> {
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