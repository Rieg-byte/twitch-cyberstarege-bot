package org.rieg.database

import org.jetbrains.exposed.sql.Database
import java.lang.System.getenv

fun connectDatabase() {
    Database.connect(
        url = getenv("URL_DB"),
        driver = getenv("DRIVER_DB"),
        user = getenv("USER_DB"),
        password = getenv("PASSWORD_DB")
    )
}