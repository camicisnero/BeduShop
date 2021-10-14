package org.bedu.bedushop

data class Product(
    val id:Int,
    val title:String,
    val price:Float,
    val description:String,
    val rating: Rating,
    val category:String,
    val image:String
)
class Rating(
    val rate:Float,
    val count:Int
)