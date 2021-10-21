package org.bedu.bedushop.classes

data class Product(
    val id:Int,
    val title:String,
    val price:Float,
    val description:String,
    val rating: Rating,
    val category:String,
    val image:String
)
