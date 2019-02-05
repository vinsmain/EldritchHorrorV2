package ru.mgusev.eldritchhorror.database.staticDB;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import ru.mgusev.eldritchhorror.dao.AncientOneDAO;
import ru.mgusev.eldritchhorror.dao.EndingDAO;
import ru.mgusev.eldritchhorror.dao.ExpansionDAO;
import ru.mgusev.eldritchhorror.dao.InvestigatorDAO;
import ru.mgusev.eldritchhorror.dao.PreludeDAO;
import ru.mgusev.eldritchhorror.dao.RumorDAO;
import ru.mgusev.eldritchhorror.dao.SpecializationDAO;
import ru.mgusev.eldritchhorror.model.AncientOne;
import ru.mgusev.eldritchhorror.model.Ending;
import ru.mgusev.eldritchhorror.model.Expansion;
import ru.mgusev.eldritchhorror.model.Investigator;
import ru.mgusev.eldritchhorror.model.Prelude;
import ru.mgusev.eldritchhorror.model.Rumor;
import ru.mgusev.eldritchhorror.model.Specialization;

@Database(entities = {AncientOne.class, Expansion.class, Investigator.class, Prelude.class, Specialization.class, Rumor.class, Ending.class}, version = 8)
public abstract class StaticDataDB extends RoomDatabase {
    public abstract AncientOneDAO ancientOneDAO();
    public abstract ExpansionDAO expansionDAO();
    public abstract PreludeDAO preludeDAO();
    public abstract InvestigatorDAO investigatorDAO();
    public abstract SpecializationDAO specializationDAO();
    public abstract RumorDAO rumorDAO();
    public abstract EndingDAO endingDAO();
}