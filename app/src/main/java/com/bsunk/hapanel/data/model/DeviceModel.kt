package com.bsunk.hapanel.data.model

/**
 * Created by Bharat on 3/10/2017.
 */
class DeviceModel {

    var entity_id: String? = null
    var state: String? = null
    var last_changed: String? = null
    var type: String? = null
    var attributes: String? = null

    constructor() {}

    constructor(entity_id: String, state: String,
                last_changed: String, attributes: String, type: String) {
        this.entity_id = entity_id
        this.state = state
        this.last_changed = last_changed
        this.attributes = attributes
        this.type = type
    }

}
