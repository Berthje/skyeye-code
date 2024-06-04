package com.howest.skyeye.workers

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.work.testing.TestWorkerBuilder
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ReminderWorkerTest {

    private lateinit var worker: ReminderWorker
    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        worker = TestWorkerBuilder.from(context, ReminderWorker::class.java).build()
    }

    @Test
    fun testDoWork() {
        val result = worker.doWork()
        Assert.assertEquals(result, androidx.work.ListenableWorker.Result.success())
    }

    @Test
    fun testNotificationSent() {
        worker.doWork()

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notifications = notificationManager.activeNotifications

        val notification = notifications[0].notification
        Assert.assertEquals("Hey!", notification.extras.getString(NotificationCompat.EXTRA_TITLE))
        Assert.assertEquals("Did you forget us? We are missing you!", notification.extras.getString(NotificationCompat.EXTRA_TEXT))
    }
}