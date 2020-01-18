package keibatool.dao;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.springframework.transaction.annotation.Transactional;

import keibatool.entity.RaceData;


@Dao
public interface RaceDataDao {
	@Insert
    @Transactional
    int insert(RaceData raceData);
}
