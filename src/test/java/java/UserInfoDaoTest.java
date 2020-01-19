package java;

import org.junit.Before;
import org.junit.Test;
import org.seasar.doma.jdbc.tx.TransactionManager;

import keibatool.dao.UserInfoDao;
import keibatool.AppConfig;

public class UserInfoDaoTest {
	  private final UserInfoDao dao = new UserInfoDaoImpl();
	    private TransactionManager tm;

	    @Before
	    public void setUp() {
	        tm = AppConfig.singleton().getTransactionManager();
	    }

	    @Test
	    public void test_save() {
	        tm.required(() -> {
	            UserInfo sample = new UserInfo("hoge","fuga");
	            dao.insert(sample);
	        });
	    }
}
