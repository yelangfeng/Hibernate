package cn.csdas.yelf.day04;

import cn.csdas.yelf.domain.Customer;
import cn.csdas.yelf.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

public class HibernateDemo4 {
    @Test
    public void demo1(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Customer customer = session.get(Customer.class,1);
        System.out.println(1111);
        System.out.println(customer);

        transaction.commit();
    }
}
