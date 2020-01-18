package keibatool.dao;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.springframework.transaction.annotation.Transactional;

import keibatool.entity.RacePay;


@Dao
public interface RacePayDao {
	@Insert
    @Transactional
    int insert(RacePay racePay);
}
