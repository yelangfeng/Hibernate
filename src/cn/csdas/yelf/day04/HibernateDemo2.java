package cn.csdas.yelf.day04;

import cn.csdas.yelf.domain.Customer;
import cn.csdas.yelf.domain.LinkMan;
import cn.csdas.yelf.utils.HibernateUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.*;
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

    @Test
    /**
     * 条件查询
     */
    public void demo4(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction tx = session.beginTransaction();

        // 条件查询
        Criteria criteria = session.createCriteria(Customer.class);
        // 设置条件:
        /**
         * =   eq
         * >   gt
         * >=  ge
         * <   lt
         * <=  le
         * <>  ne
         * like
         * in
         * and
         * or
         */
        criteria.add(Restrictions.ge("cust_id", 3));
		//criteria.add(Restrictions.or(Restrictions.like("cust_name", "林%")));
        criteria.add(Restrictions.like("cust_name", "叶%"));
        List<Customer> list = criteria.list();
        for (Customer customer : list) {
            System.out.println(customer);
        }
        tx.commit();
    }

    @Test
    /**
     * 统计查询
     */
    public void demo5(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction tx = session.beginTransaction();

        Criteria criteria = session.createCriteria(Customer.class);
        /**
         * add				:普通的条件。where后面条件
         * addOrder			:排序
         * setProjection	:聚合函数 和 group by having
         */
        criteria.setProjection(Projections.rowCount());
        Long num = (Long) criteria.uniqueResult();
        System.out.println(num);
        tx.commit();
    }

    @Test
    /**
     * 离线条件查询
     */
    public void demo6(){
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Customer.class);
        detachedCriteria.add(Restrictions.like("cust_name", "李%"));

        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Criteria criteria = detachedCriteria.getExecutableCriteria(session);
        List<Customer> list = criteria.list();
        for (Customer customer : list) {
            System.out.println(customer);
        }
        transaction.commit();
    }
}

