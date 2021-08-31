package com.app.codehero.utils

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test


/**
 * FELIPE
 */
class ToolsTest {

    @Test
    fun `when calling generateHashMarvelApi then it should return the md5 hash of marvelapikeys concatenated to the timestamp`() {
        val t = 123456
        val finalHash = "bd68d0d5cda9d525fa2d1fe97a4c7088"

        assertThat(generateHashMarvelApi(t), `is`(finalHash))
    }
}