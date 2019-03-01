package cn.csdas.yelf.day03;

import cn.csdas.yelf.day03.domain.Customer;
import cn.csdas.yelf.day03.domain.LinkMan;
import cn.csdas.yelf.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class HibernateDemo {
    @Test
    public void demo1(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Customer customer1 = new Customer();
        Customer customer2 = new Customer();
        customer1.setCust_id(1L);
        customer2.setCust_id(2L);
        customer1.setCust_name("张三");
        customer2.setCust_name("李四");

        LinkMan linkMan1 = new LinkMan();
        LinkMan linkMan2 = new LinkMan();
        LinkMan linkMan3 = new LinkMan();
        linkMan1.setLkm_id(1L);
        linkMan2.setLkm_id(2L);
        linkMan3.setLkm_id(3L);
        linkMan1.setLkm_name("王五");
        linkMan2.setLkm_name("赵六");
        linkMan3.setLkm_name("王八");

        linkMan1.setCustomer(customer1);
        linkMan2.setCustomer(customer2);
        linkMan3.setCustomer(customer2);
        customer1.getLinkMans().add(linkMan1);
        customer2.getLinkMans().add(linkMan2);
        customer2.getLinkMans().add(linkMan3);

        session.save(customer1);
        session.save(customer2);
        session.save(linkMan1);
        session.save(linkMan2);
        session.save(linkMan3);

        transaction.commit();
    }
}
