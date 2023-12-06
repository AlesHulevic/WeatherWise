package com.example.tolaaleksey.weatherinfo

import DaysRepositoryImpl
import RemoteDaysDataSourceImpl
import com.example.tolaaleksey.weatherinfo.Database.Core.AppDatabase
import com.example.tolaaleksey.weatherinfo.Database.daos.DayDao
import com.example.tolaaleksey.weatherinfo.Interfaces.DayDataSource
import com.example.tolaaleksey.weatherinfo.Interfaces.DaysRepository
import com.example.tolaaleksey.weatherinfo.Interfaces.RemoteDaysDataSource
import com.example.tolaaleksey.weatherinfo.classes.DaysDatasourceImpl
import com.example.tolaaleksey.weatherinfo.classes.EditViewModule
import com.example.tolaaleksey.weatherinfo.classes.HomeViewModel
import com.example.tolaaleksey.weatherinfo.classes.RemoteDaysRepository
import com.example.tolaaleksey.weatherinfo.classes.RemoteDaysRepositoryImpl
import com.example.tolaaleksey.weatherinfo.classes.RemoteDaysViewModel
import io.ktor.client.HttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val databaseModule = module {
    single<AppDatabase> { AppDatabase(context = get()) }
    single<DayDao> { get<AppDatabase>().dayDao() }
}

val RemoteDataBaseModule = module {
    single<HttpClient> { httpClientBuilder(json) }
}

val daysModule = module {
    includes(databaseModule)

    single<DayDataSource> { DaysDatasourceImpl(get<DayDao>()) }
    single<DaysRepository> { DaysRepositoryImpl(get<DayDataSource>()) }
}

val remoteDaysModule = module {
    includes(RemoteDataBaseModule)

    single<RemoteDaysDataSource> { RemoteDaysDataSourceImpl(get<HttpClient>()) }
    single<RemoteDaysRepository> { RemoteDaysRepositoryImpl(get<RemoteDaysDataSource>()) }

}

val appModule = module {
    includes(daysModule)
    includes(remoteDaysModule)

    viewModel { HomeViewModel(get<DaysRepository>()) }
    viewModel { EditViewModule(get<DaysRepository>()) }
    viewModel { RemoteDaysViewModel(get<RemoteDaysRepository>(), get<DaysRepository>()) }
}
