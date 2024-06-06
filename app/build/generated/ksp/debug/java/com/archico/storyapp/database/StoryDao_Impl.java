package com.archico.storyapp.database;

import android.database.Cursor;
import androidx.paging.PagingSource;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.paging.LimitOffsetPagingSource;
import androidx.room.util.CursorUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.archico.storyapp.data.response.Story;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class StoryDao_Impl implements StoryDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Story> __insertionAdapterOfStory;

  private final SharedSQLiteStatement __preparedStmtOfDeleteStory;

  public StoryDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfStory = new EntityInsertionAdapter<Story>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `story` (`photoUrl`,`createdAt`,`name`,`description`,`lon`,`id`,`lat`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Story value) {
        if (value.getPhotoUrl() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getPhotoUrl());
        }
        if (value.getCreatedAt() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getCreatedAt());
        }
        if (value.getName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getName());
        }
        if (value.getDescription() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getDescription());
        }
        stmt.bindDouble(5, value.getLon());
        if (value.getId() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getId());
        }
        stmt.bindDouble(7, value.getLat());
      }
    };
    this.__preparedStmtOfDeleteStory = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM story";
        return _query;
      }
    };
  }

  @Override
  public Object insertStory(final List<Story> quote,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfStory.insert(quote);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object deleteStory(final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteStory.acquire();
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfDeleteStory.release(_stmt);
        }
      }
    }, continuation);
  }

  @Override
  public PagingSource<Integer, Story> getAllStory() {
    final String _sql = "SELECT * FROM story";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new LimitOffsetPagingSource<Story>(_statement, __db, "story") {
      @Override
      protected List<Story> convertRows(Cursor cursor) {
        final int _cursorIndexOfPhotoUrl = CursorUtil.getColumnIndexOrThrow(cursor, "photoUrl");
        final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(cursor, "createdAt");
        final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(cursor, "name");
        final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(cursor, "description");
        final int _cursorIndexOfLon = CursorUtil.getColumnIndexOrThrow(cursor, "lon");
        final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(cursor, "id");
        final int _cursorIndexOfLat = CursorUtil.getColumnIndexOrThrow(cursor, "lat");
        final List<Story> _result = new ArrayList<Story>(cursor.getCount());
        while(cursor.moveToNext()) {
          final Story _item;
          final String _tmpPhotoUrl;
          if (cursor.isNull(_cursorIndexOfPhotoUrl)) {
            _tmpPhotoUrl = null;
          } else {
            _tmpPhotoUrl = cursor.getString(_cursorIndexOfPhotoUrl);
          }
          final String _tmpCreatedAt;
          if (cursor.isNull(_cursorIndexOfCreatedAt)) {
            _tmpCreatedAt = null;
          } else {
            _tmpCreatedAt = cursor.getString(_cursorIndexOfCreatedAt);
          }
          final String _tmpName;
          if (cursor.isNull(_cursorIndexOfName)) {
            _tmpName = null;
          } else {
            _tmpName = cursor.getString(_cursorIndexOfName);
          }
          final String _tmpDescription;
          if (cursor.isNull(_cursorIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = cursor.getString(_cursorIndexOfDescription);
          }
          final float _tmpLon;
          _tmpLon = cursor.getFloat(_cursorIndexOfLon);
          final String _tmpId;
          if (cursor.isNull(_cursorIndexOfId)) {
            _tmpId = null;
          } else {
            _tmpId = cursor.getString(_cursorIndexOfId);
          }
          final float _tmpLat;
          _tmpLat = cursor.getFloat(_cursorIndexOfLat);
          _item = new Story(_tmpPhotoUrl,_tmpCreatedAt,_tmpName,_tmpDescription,_tmpLon,_tmpId,_tmpLat);
          _result.add(_item);
        }
        return _result;
      }
    };
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
