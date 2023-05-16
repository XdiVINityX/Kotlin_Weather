package com.example.kotlin_weather

import android.content.ContentProvider
import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.kotlin_weather.MyApp.Companion.getHistoryDAO
import com.example.kotlin_weather.room.*


private const val ENTITY_PATH = "HistoryEntity"

private const val URI_ID = 0
private const val URI_ALL = 1


class EducationContentProvider : ContentProvider() {

    private val authorities = "com.example.kotlin_weather.provider"
    private lateinit var uriMatcher: UriMatcher

    private var entityContentType: String? = null
    private var entityContentItemType: String? = null
    private lateinit var contentUri: Uri

    override fun onCreate(): Boolean {
        uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        uriMatcher.addURI(authorities, "$ENTITY_PATH/#", URI_ID)
        uriMatcher.addURI(authorities, ENTITY_PATH, URI_ALL)

        entityContentType = "vnd.android.cursor.dir/vnd.$authorities.$ENTITY_PATH"
// Тип содержимого — один объект
        entityContentItemType = "vnd.android.cursor.item/vnd.$authorities.$ENTITY_PATH"
        contentUri = Uri.parse(("content://$authorities/$ENTITY_PATH"))
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val historyDAO: HistoryDAO = getHistoryDAO()
        val cursor = when (uriMatcher.match(uri)) {
            URI_ALL -> {
                historyDAO.getHistoryCursor()
            }
            URI_ID -> {
                val id = ContentUris.parseId(uri)
                historyDAO.getHistoryCursor(id)
            }
            else -> throw java.lang.IllegalStateException("Ошибка выбора в провайдере")
        }
        cursor.setNotificationUri(context?.contentResolver, contentUri)
        return cursor
    }

    override fun getType(uri: Uri): String? {
        return when (uriMatcher.match(uri)) {
            URI_ALL -> {
                entityContentType
            }
            URI_ID -> {
                entityContentItemType
            }
            else -> throw java.lang.IllegalStateException("Ошибка выбора в провайдере")
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        require(uriMatcher.match(uri) == URI_ALL) { "NEED entityContentType wrong $uri" }
        val historyDAO: HistoryDAO = getHistoryDAO()
        val entity = map(values)
        val id = entity.id
        historyDAO.insert(entity)
        val resultUri = ContentUris.withAppendedId(contentUri, id)
        // Уведомляем ContentResolver, что данные по адресу resultUri изменились
        context?.contentResolver?.notifyChange(resultUri, null)
        return resultUri
    }


    private fun map(values: ContentValues?): HistoryEntity {
        return if (values == null) {
            HistoryEntity()
        } else {
            val id = if (values.containsKey(ID)) values[ID] as Long else 0
            val name = if (values.containsKey(NAME)) values[NAME] as String else "Почему пусто?"
            val temperature = if (values.containsKey(TEMPERATURE)) values[TEMPERATURE] as Int else 0
            HistoryEntity(id, name, temperature)
        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        require(uriMatcher.match(uri) == URI_ALL) { "NEED entityContentType, wrong uri: $uri" }
        val historyDAO: HistoryDAO = getHistoryDAO()
        val id = ContentUris.parseId(uri)
        historyDAO.deleteByID(id)
        context?.contentResolver?.notifyChange(uri, null)
        return 1

    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        require(uriMatcher.match(uri) == URI_ID) { "NEED entityItemContentType, wrong uri: $uri" }
        val historyDAO: HistoryDAO = getHistoryDAO()
        val entity = map(values)
        val id = entity.id
        historyDAO.update(entity)
        context?.contentResolver?.notifyChange(uri, null)
        return 1


    }

}