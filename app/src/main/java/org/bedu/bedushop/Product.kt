package org.bedu.bedushop

data class Product(
    val id:Int,
    val title:String,
    val price:Float,
    val description:String,
    val valuation:Float,
    val calification:Int,
    val category:String,
    val image:String
)
