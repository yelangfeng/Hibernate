package cn.csdas.yelf.day04;

import cn.csdas.yelf.domain.Customer;
import cn.csdas.yelf.domain.LinkMan;
import cn.csdas.yelf.utils.HibernateUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
 * HQL的查询方式的测试类
 *
 * @author yelf
 *
 */
public class HibernateDemo1 {
    @Test
    /**
     * 准备数据
     */
    public void demo1(){
        Session currentSession = HibernateUtils.getCurrentSession();
        Transaction transaction = currentSession.beginTransaction();

        Customer customer = new Customer();
        customer.setCust_name("石桂松");
        customer.setCust_level("4");

        for (int i = 0;i<10;i++){
            LinkMan linkMan = new LinkMan();
            linkMan.setLkm_name("zhangsan"+i);
            linkMan.setCustomer(customer);
            customer.getLinkMans().add(linkMan);
            currentSession.save(linkMan);
        }

        currentSession.save(customer);

        transaction.commit();
    }

    @Test
    /**
     * HQL的简单查询
     */
    public void demo2(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        //简单查询
        Query query = session.createQuery("from Customer");
        List<Customer> list = query.list();

        // sql中支持*号的写法：select * from cst_customer; 但是在HQL中不支持*号的写法。
         //Query query = session.createQuery("select * from Customer");// 报错
         //List<Customer> list = query.list();

        for (Customer customer : list) {
            System.out.println(customer);
        }
        transaction.commit();
    }

    @Test
    /**
     * 别名查询
     */
    public void demo3(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        //别名查询
        //Query query = session.createQuery("from Customer c");
        //List<Customer> list = query.list();

        Query query = session.createQuery("select c from Customer c");
        List<Customer> list = query.list();

        for (Customer customer : list) {
            System.out.println(customer);
        }
    }

    @Test
    /**
     * 排序查询
     */
    public void demo4(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction tx = session.beginTransaction();

        //排序查询
        //Query query = session.createQuery("from Customer c order by cust_id");
        //List<Customer> list = query.list();

        Query query = session.createQuery("from Customer c order by cust_id desc");
        List<Customer> list = query.list();

        for (Customer customer : list) {
            System.out.println(customer);
        }
        tx.commit();
    }

    @Test
    /**
     * 条件查询
     */
    public void demo5(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction tx = session.beginTransaction();

        //1、按位置绑定：根据参数的位置进行绑定
        //1个条件
        /*
        Query query = session.createQuery("from Customer where cust_name = ?");
        query.setParameter(0,"叶浪峰");
        */
        //2两个条件
        /*
        Query query = session.createQuery("from Customer where cust_id = ? or cust_name = ?");
        query.setParameter(0,1);
        query.setParameter(1,"叶浪峰");
        */

        //2、按名称绑定
        Query query = session.createQuery("from Customer where cust_id = :aaa or cust_name = :bbb");
        //设置参数
        query.setParameter("aaa",1);
        query.setParameter("bbb","林志文");

        List<Customer> list = query.list();
        for (Customer customer : list) {
            System.out.println(customer);
        }
        tx.commit();
    }


    @Test
    /**
     * 投影查询
     */
    public void demo6(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        //单个属性
        /*Query query = session.createQuery("select c.cust_name from Customer c" );
        List<Object> list = query.list();
        for (Object o : list) {
            System.out.println(o);
        }*/
        //多个属性
        /*Query query = session.createQuery("select c.cust_id,c.cust_name from Customer c");
        List<Object[]> list = query.list();
        for (Object[] objects : list) {
            System.out.println(Arrays.toString(objects));
        }*/

        //查询多个属性，封装到对象中(POJO类中要有对应属性的构造方法，否则会报错)
        Query query = session.createQuery("select new Customer(cust_name,cust_source) from Customer");
        List<Customer> list = query.list();
        for (Customer customer : list) {
            System.out.println(customer);
        }
        transaction.commit();
    }

    @Test
    /**
     *分页查询
     */
    public void demo7(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("from Customer");
        //分页查询设置
        query.setFirstResult(3);
        query.setMaxResults(3);
        List<Customer> list = query.list();

        for (Customer customer : list) {
            System.out.println(customer);
        }

        transaction.commit();
    }

    @Test
    /**
     * 分组统计查询
     */
    public void demo8(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        //聚合函数的使用：count(),max(),min(),avg(),sum()
        //返回结果为唯一值，用uniqueResult()，返回集合用list()
        Object object = session.createQuery("select count(*) from Customer").uniqueResult();
        System.out.println(object);

        //分组统计
        Query query = session.createQuery("select cust_level,count(*) from Customer group by cust_level");
        List<Object[]> list = query.list();
        for (Object[] objects : list) {
            System.out.println(Arrays.toString(objects));
        }

        transaction.commit();
    }
}
