package ru.mgusev.eldritchhorror.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import ru.mgusev.eldritchhorror.model.ImageFile;

@Dao
public interface ImageFileDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertImageFile(ImageFile file);

    @Delete
    int deleteImageFile(ImageFile file);

    @Query("DELETE FROM files WHERE user_id != null")
    void deleteAllImageFiles();

    @Query("SELECT * FROM files")
    List<ImageFile> getAllImageFiles();

    @Query("SELECT * FROM files WHERE name = :name LIMIT 1")
    ImageFile getImageFile(String name);

    @Query("SELECT * FROM files WHERE game_id = :gameId")
    List<ImageFile> getImageFileList(long gameId);
}
