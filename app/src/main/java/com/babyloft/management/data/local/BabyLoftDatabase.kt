package com.babyloft.management.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.babyloft.management.data.local.dao.AttributeDao
import com.babyloft.management.data.local.dao.CategoryDao
import com.babyloft.management.data.local.dao.CustomerDao
import com.babyloft.management.data.local.dao.OrderDao
import com.babyloft.management.data.local.dao.OrderStatusHistoryDao
import com.babyloft.management.data.local.dao.ProductDao
import com.babyloft.management.data.local.entity.AttributeEntity
import com.babyloft.management.data.local.entity.CategoryEntity
import com.babyloft.management.data.local.entity.CustomerEntity
import com.babyloft.management.data.local.entity.OrderEntity
import com.babyloft.management.data.local.entity.OrderItemEntity
import com.babyloft.management.data.local.entity.OrderStatusHistoryEntity
import com.babyloft.management.data.local.entity.ProductEntity

@Database(
    entities = [
        CustomerEntity::class,
        CategoryEntity::class,
        ProductEntity::class,
        OrderEntity::class,
        OrderItemEntity::class,
        OrderStatusHistoryEntity::class,
        AttributeEntity::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class BabyLoftDatabase : RoomDatabase() {

    abstract fun customerDao(): CustomerDao
    abstract fun categoryDao(): CategoryDao
    abstract fun productDao(): ProductDao
    abstract fun orderDao(): OrderDao
    abstract fun orderStatusHistoryDao(): OrderStatusHistoryDao
    abstract fun attributeDao(): AttributeDao

    companion object {
        @Volatile
        private var INSTANCE: BabyLoftDatabase? = null

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `attributes` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `type` TEXT NOT NULL,
                        `name` TEXT NOT NULL,
                        `hexColor` TEXT,
                        `displayOrder` INTEGER NOT NULL DEFAULT 0
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_attributes_type_name` ON `attributes` (`type`, `name`)")
            }
        }

        fun getDatabase(context: Context): BabyLoftDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BabyLoftDatabase::class.java,
                    "babyloft_database"
                )
                .addMigrations(MIGRATION_1_2)
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
