package src.main.java.keibatool.dao;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.springframework.transaction.annotation.Transactional;

import src.main.java.keibatool.entity.RaceData;

@Dao
public interface RaceDataDao {
	@Insert
    @Transactional
    int insert(RaceData raceData);
}
