package com.bemo.medicalservices.classes

class personClass {
    var name:String? = null
    var phone :String? = null
    var city :String? = null
    var specialization :String? = null

    constructor()
    constructor(name:String?,specialization :String?,Phone :String?,city :String?){
        this.name=name
        this.city=city
        this.specialization=specialization
        this.phone=Phone


    }
}