package com.vaibhav.taskify.data.models.mappper

import com.vaibhav.taskify.data.models.entity.TaskEntity
import com.vaibhav.taskify.data.models.remote.TaskDTO
import com.vaibhav.taskify.util.TaskType
import java.util.*

class TaskMapper : Mapper<TaskDTO, TaskEntity> {

    override fun toDomainModel(network: TaskDTO) = TaskEntity(
        email = network.email,
        task_title = network.task_title,
        task_description = network.task_description,
        task_category = TaskType.valueOf(network.task_category.uppercase(Locale.getDefault())),
        start_time = network.start_time,
        end_time = network.end_time,
        started = network.started,
        completed = network.completed,
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
        start_time = domain.start_time,
        end_time = domain.end_time,
        started = domain.started,
        completed = domain.completed,
        created_time = domain.created_time,
        task_id = domain.task_id
    )
}