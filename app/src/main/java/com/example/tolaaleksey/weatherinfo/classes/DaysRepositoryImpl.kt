import com.example.tolaaleksey.weatherinfo.Interfaces.DayDataSource
import com.example.tolaaleksey.weatherinfo.Interfaces.DaysRepository
import com.example.tolaaleksey.weatherinfo.classes.Day
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.util.UUID

internal class DaysRepositoryImpl(private val dataSource: DayDataSource) : DaysRepository {


    override fun getDay(id: UUID?): Flow<Day?> {
        if (id == null) {
            return flowOf(null)
        }
        return dataSource.getDay(id)
    }

    override fun getDays(): Flow<List<Day>> {
        return dataSource.getDays()
    }

    override suspend fun upsert(day: Day) {
        dataSource.upsert(day)
    }

    override suspend fun delete(id: UUID) {
        dataSource.delete(id = id)
    }
}