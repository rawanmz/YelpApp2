package com.bignerdranch.android.yelpapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.bignerdranch.android.yelpapp.data.YelpRestaurant

@Database(
    entities = [YelpRestaurant::class, DayPlan::class],
    version = 1
)
@TypeConverters(YelpTypeConverter::class)
abstract class YelpDatabase : RoomDatabase() {
    abstract fun dao(): YelpDao
    abstract fun dayPlanDao(): DayPlanDao

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: YelpDatabase? = null
//todo add encryption to db

        fun getInstance(
            context: Context,
        ): YelpDatabase {
            return instance ?: synchronized(this) {
                instance
                    ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun createDbMigrations(context: Context): Array<Migration> {
            val migrations = mutableListOf<Migration>()
            // Each update will have migration

            val MIGRATION_0_1 = object : Migration(0, 1) {
                override fun migrate(database: SupportSQLiteDatabase) {
//                    Timber.d("mirgrate 0 ----> 1")
//                    database.execSQL(
//                        "ALTER TABLE ${UserCardImageConstants.TABLE_NAME} ADD COLUMN ${UserCardImageConstants.COLUMN_QR_CODE} TEXT NOT NULL default '' "
//                    )
                }
            }
            migrations.add(MIGRATION_0_1)
            return migrations.toTypedArray()
        }

        private fun createDatabase(
            context: Context
            //supportFactory: SafeHelperFactory
        ) = Room.databaseBuilder(context, YelpDatabase::class.java, "db")
            .addMigrations(*createDbMigrations(context))
            .fallbackToDestructiveMigration()
            //.openHelperFactory(supportFactory)
            .build()

        /* Passphrase validation: */
        //todo add authentication
//        private fun isPassphraseValid(db: YelpDatabase): Boolean {
//            return try {
//                db.openHelper.readableDatabase
//                db.openHelper.close()
//                true
//            } catch (e: net.sqlcipher.database.SQLiteException) {
//                e.printStackTrace()
//                false
//            }
//        }
//todo

//        private fun buildDatabase(
//            context: Context,
//            dbKey: ByteArray
//          //  databaseSecretProvider: DatabaseSecretProvider
//        ): YelpDatabase {
//            val supportFactory = SafeHelperFactory(
//                dbKey,
//                SafeHelperFactory
//                    .Options.builder()
//                    .setClearPassphrase(false)
//                    .build()
//            )
//            val db = createDatabase(context, supportFactory)
//            return if (isPassphraseValid(db)) {
//                createDatabase(context, supportFactory)
//            } else {
//                context.deleteDatabase(dbName)
//                val newDbKey = databaseSecretProvider.get()
//                buildDatabase(context, newDbKey, databaseSecretProvider)
//            }
//
//        }
private fun buildDatabase(
    context: Context,
): YelpDatabase {

    val db = createDatabase(context)
        createDatabase(context)
        buildDatabase(context)
    return db

}
    }
}