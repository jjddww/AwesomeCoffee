package com.jjddww.awesomecoffee

object MemberInfo {
    var memberId: Long? = 0

    fun initialMemberInfo(){
        if(memberId != 0L)
            memberId = 0
    }
}