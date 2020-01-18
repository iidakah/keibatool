package keibatool.dao;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.springframework.transaction.annotation.Transactional;

import keibatool.entity.HorseMst;


@Dao
public interface HorseMstDao {
	@Insert
    @Transactional
    int insert(HorseMst horseMst);
}
