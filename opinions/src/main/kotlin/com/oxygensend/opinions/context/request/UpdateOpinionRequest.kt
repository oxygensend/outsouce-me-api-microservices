package com.oxygensend.opinions.context.request

import com.oxygensend.opinions.context.command.UpdateOpinionCommand
import com.oxygensend.opinions.utils.PathChanges
import com.oxygensend.opinions.utils.PathRequest
import com.oxygensend.opinions.utils.Undefined
import jakarta.validation.constraints.Positive
import kotlin.reflect.KProperty

class UpdateOpinionRequest : PathRequest {
    override val changes: MutableSet<KProperty<*>> = mutableSetOf()

    var text: String? by PathChanges(changes)

    @get:Positive(message = "scale must be larger than 0")
    var scale: Int? by PathChanges(changes)

    fun toCommand(): UpdateOpinionCommand {
        return UpdateOpinionCommand(
            text = Undefined(text, hasChanged(UpdateOpinionRequest::text)),
            scale = Undefined(scale, hasChanged(UpdateOpinionRequest::scale))
        )
    }
}
