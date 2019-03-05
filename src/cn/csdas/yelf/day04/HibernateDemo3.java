package cn.csdas.yelf.day04;

import cn.csdas.yelf.domain.Customer;
import cn.csdas.yelf.utils.HibernateUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import java.util.List;

/**
 * SQL查询
 * @author yelf
 */
public class HibernateDemo3 {
    @Test
    public void demo1(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction tx = session.beginTransaction();

		/*SQLQuery sqlQuery = session.createSQLQuery("select * from cst_customer");
		List<Object[]> list = sqlQuery.list();
		for (Object[] objects : list) {
			System.out.println(Arrays.toString(objects));
		}*/

        SQLQuery sqlQuery = session.createSQLQuery("select * from cst_customer");
        sqlQuery.addEntity(Customer.class);
        List<Customer> list = sqlQuery.list();
        for (Customer customer : list) {
            System.out.println(customer);
        }
        tx.commit();
    }
}

