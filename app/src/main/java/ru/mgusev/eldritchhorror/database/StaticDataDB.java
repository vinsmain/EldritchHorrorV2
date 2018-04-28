package ru.mgusev.eldritchhorror.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import ru.mgusev.eldritchhorror.dao.AncientOneDAO;
import ru.mgusev.eldritchhorror.dao.ExpansionDAO;
import ru.mgusev.eldritchhorror.dao.InvestigatorDAO;
import ru.mgusev.eldritchhorror.dao.PreludeDAO;
import ru.mgusev.eldritchhorror.model.AncientOne;
import ru.mgusev.eldritchhorror.model.Expansion;
import ru.mgusev.eldritchhorror.model.Investigator;
import ru.mgusev.eldritchhorror.model.Prelude;

@Database(entities = {AncientOne.class, Expansion.class, Investigator.class, Prelude.class}, version = 1)
public abstract class StaticDataDB extends RoomDatabase {
    public abstract AncientOneDAO ancientOneDAO();
    public abstract ExpansionDAO expansionDAO();
    public abstract PreludeDAO preludeDAO();
    public abstract InvestigatorDAO investigatorDAO();
}