package com.example.ualaapp.presentation.viewmodel

import androidx.lifecycle.*
import com.example.ualaapp.data.models.DataCities
import com.example.ualaapp.domain.usecase.GetAllCitiesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author Axel Sanchez
 */
class CitiesViewModel(
    private val getAllCitiesUseCase: GetAllCitiesUseCase): ViewModel() {

    private var listData: MutableLiveData<DataCities> =
        MutableLiveData<DataCities>()

    private fun setListData(result: DataCities) {
        listData.postValue(result)
    }

    fun reset(){
        listData = MutableLiveData<DataCities>()
    }

    fun getCities() {
        viewModelScope.launch(Dispatchers.Main) {
            val allCities = getAllCitiesUseCase.call()
            setListData(allCities)
        }
    }

    fun getCitiesLiveData(): LiveData<DataCities> {
        return listData
    }

    class CitiesViewModelFactory(
        private val getAllCitiesUseCase: GetAllCitiesUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(GetAllCitiesUseCase::class.java)
                .newInstance(getAllCitiesUseCase)
        }
    }
}