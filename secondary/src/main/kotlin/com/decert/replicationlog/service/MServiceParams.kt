package com.decert.replicationlog.service

data class MServiceParams(val delay: Long) {

    companion object {

        val default = MServiceParams(0)
    }
}
