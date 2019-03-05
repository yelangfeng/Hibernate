package cn.csdas.yelf.day04;

import cn.csdas.yelf.domain.Customer;
import cn.csdas.yelf.domain.LinkMan;
import cn.csdas.yelf.utils.HibernateUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.junit.Test;

import java.util.List;

/**
 * QBC查询测试
 * @author yelf
 */
public class HibernateDemo2 {
    @Test
    /**
     * 简单查询
     */
    public void demo1(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        //获得Criteria对象
        Criteria criteria = session.createCriteria(Customer.class);
        List<Customer> list = criteria.list();

        for (Customer customer : list) {
            System.out.println(customer);
        }

        transaction.commit();
    }

    @Test
    /**
     * 排序查询
     */
    public void demo2(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        //排序查询
        Criteria criteria = session.createCriteria(Customer.class);
        criteria.addOrder(Order.desc("cust_id"));//降序
        //criteria.addOrder(Order.asc("cust_id"));//升序
        List<Customer> list = criteria.list();

        for (Customer customer : list) {
            System.out.println(customer);
        }

        transaction.commit();
    }

    @Test
    /**
     * 分页查询
     */
    public void demo3(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        //分页查询
        Criteria criteria = session.createCriteria(LinkMan.class);
        criteria.setFirstResult(8);
        criteria.setMaxResults(5);
        List<LinkMan> list = criteria.list();

        for (LinkMan linkMan : list) {
            System.out.println(linkMan);
        }

        transaction.commit();
    }
}
