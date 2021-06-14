package com.vaibhav.taskify.data.models.mappper

import com.vaibhav.taskify.data.models.entity.TaskEntity
import com.vaibhav.taskify.data.models.remote.TaskDTO
import com.vaibhav.taskify.util.TaskState
import com.vaibhav.taskify.util.TaskType
import java.util.*

class TaskMapper : Mapper<TaskDTO, TaskEntity> {

    override fun toDomainModel(network: TaskDTO) = TaskEntity(
        email = network.email,
        task_title = network.task_title,
        task_description = network.task_description,
        task_category = TaskType.valueOf(network.task_category.uppercase(Locale.getDefault())),
        state = TaskState.valueOf(network.state.uppercase(Locale.getDefault())),
        duration = network.duration,
        timeLeft = network.time_left,
        created_time = network.created_time,
        task_id = network.task_id
    )

    override fun toDomainList(network: List<TaskDTO>) = network.map {
        toDomainModel(it)
    }

    override fun toNetwork(domain: TaskEntity) = TaskDTO(
        email = domain.email,
        task_title = domain.task_title,
        task_description = domain.task_description,
        task_category = domain.task_category.name,
        state = domain.state.name,
        duration = domain.duration,
        time_left = domain.timeLeft,
        created_time = domain.created_time,
        task_id = domain.task_id
    )
}