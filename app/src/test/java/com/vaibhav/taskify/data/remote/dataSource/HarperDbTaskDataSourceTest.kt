package com.vaibhav.taskify.data.remote.dataSource

import com.vaibhav.taskify.data.models.remote.TaskDTO
import com.vaibhav.taskify.util.Resource
import org.junit.Test

import org.junit.Assert.*
import javax.inject.Inject


class HarperDbTaskDataSourceTest {

    @Inject
    lateinit var harperDbDataSource: HarperDbTaskDataSource


    @Test
    suspend fun insertTask() {
        val resource = harperDbDataSource.insertTask(
            TaskDTO(
                "",
                "", "", "", 0L, 0L, false, false, 0L
            )
        )
        assertTrue(resource is Resource.Success)
    }
}