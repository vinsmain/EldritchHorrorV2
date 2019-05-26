package ru.mgusev.eldritchhorror.database.userDB;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import ru.mgusev.eldritchhorror.database.dao.GameDAO;
import ru.mgusev.eldritchhorror.database.dao.ImageFileDAO;
import ru.mgusev.eldritchhorror.database.dao.InvestigatorDAO;
import ru.mgusev.eldritchhorror.model.Game;
import ru.mgusev.eldritchhorror.model.ImageFile;
import ru.mgusev.eldritchhorror.model.Investigator;

@Database(entities = {Game.class, Investigator.class, ImageFile.class}, version = 7)
public abstract class UserDataDB extends RoomDatabase {
    public abstract GameDAO gameDAO();
    public abstract InvestigatorDAO investigatorDAO();
    public abstract ImageFileDAO imageFileDAO();
}