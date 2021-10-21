package org.bedu.bedushop.classes

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class ProductR: RealmObject() {

    @PrimaryKey
    var id: Int? = null
    var title: String? = null
    var price: Float? = null
    var description: String? = null
    var rate: Float? = null
    var count: Int? = null
    var category: String? = null
    var image: String? = null

}