package ru.mgusev.eldritchhorror.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

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
