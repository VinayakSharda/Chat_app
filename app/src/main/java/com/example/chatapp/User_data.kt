package com.example.chatapp

class User{
    var uid:String? =null
    var User_Name:String? =null
    var ImageUrl:String? =null
    constructor(){}
    constructor(uid:String?, User_Name:String?,ImageUrl:String?){
        this.uid=uid
        this.User_Name=User_Name
        this.ImageUrl=ImageUrl
    }
}