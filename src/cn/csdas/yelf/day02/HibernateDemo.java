package cn.csdas.yelf.day02;

import cn.csdas.yelf.utils.HibernateUtils;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;

import javax.sound.midi.Soundbank;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * hibernate框架测试类
 * @author yelf
 */
public class HibernateDemo {
    @Test
    //测试主键生成策略
    public void demo1(){
        Session session = HibernateUtils.openSession();
        // 手动开启事务：
        Transaction transaction = session.beginTransaction();

        //测试主键生成策略
        Account account = new Account();
        account.setId(3);
        account.setName("yelf");
        account.setMoney(5000);
        session.save(account);

        // 6.事务提交
        transaction.commit();
        // 7.资源释放
        session.close();
    }

    @Test
    //测试持久化类(三种状态)
    public void demo2(){
        Session session = HibernateUtils.openSession();
        // 手动开启事务：
        Transaction transaction = session.beginTransaction();

        Account account = new Account(); //瞬时态对象，没有唯一的OID，没有被session管理
        account.setId(10);
        account.setName("xuan");
        account.setMoney(9999);

        Serializable id = session.save(account); //持久态对象，有唯一标识OID，被session管理

        // 6.事务提交
        transaction.commit();
        // 7.资源释放
        session.close();

        System.out.println(account);  //脱管态对象，有唯一标识OID，没有被session管理

    }
    @Test
    //持久态对象特性测试
    public void demo3(){
        Session session = HibernateUtils.openSession();
        Transaction transaction = session.beginTransaction();

        Account account = session.get(Account.class,1);
        account.setMoney(555);//持久化对象即使数据有变化，即使后面无更新语句Hibernate也会发送更新语句到数据库

        transaction.commit();
        session.close();

    }

    @Test
    //证明一级缓存的存在
    public void demo4(){
        Session session = HibernateUtils.openSession();
        Transaction transaction = session.beginTransaction();

        /*
        //证明一
        Account account = session.get(Account.class,1);//发送sql
        System.out.println(account);
        Account account1 = session.get(Account.class,1);//不发送sql
        System.out.println(account1);
        System.out.println(account1==account);
        */
        //证明二
        Account account = new Account();
        account.setId(2);
        account.setName("yelf");
        account.setMoney(222);
        Serializable id = session.save(account);//发送sql
        Account account1 = session.get(Account.class, id);//不发送sql
        System.out.println(account1);

        transaction.commit();
        session.close();

        //一级缓存快照区：当事物要提交时，一级缓存的数据（持久化对象）与快照区的数据作对比，不一致时自动发送执行update
    }

    @Test
    //线程绑定
    public void demo5(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Account account = new Account();
        account.setName("lin");
        account.setMoney(6666);
        session.save(account);

        transaction.commit();
        //线程绑定Session无需关闭，线程结束自动关闭
    }

    @Test
    //Query对象测试
    public void demo6(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        /*
        //获取全部对象
        String hql = "from Account";
        Query query = session.createQuery(hql);
        */
        /*
        //按查询条件获取对象
        String hql = "from Account where name = ?";
        Query query = session.createQuery(hql);
        query.setParameter(0,"xuan");
        */

        //分页查询
        String hql = "from Account";
        Query query = session.createQuery(hql);
        query.setFirstResult(3);
        query.setMaxResults(2);

        List<Account> list = query.list();
        for (Account account : list) {
            System.out.println(account);
        }

        transaction.commit();
    }

    @Test
    //Criteria对象测试
    public void demo7(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        /*
        //获取全部对象
        Criteria criteria = session.createCriteria(Account.class);
        List<Account> list = criteria.list();
        */

        /*
        //按查询条件获取对象
        Criteria criteria = session.createCriteria(Account.class);
        criteria.add(Restrictions.like("name","%y%"));
        List<Account> list = criteria.list();
        */

        //分页查询
        Criteria criteria = session.createCriteria(Account.class);
        criteria.setFirstResult(2);
        criteria.setMaxResults(3);
        List<Account> list = criteria.list();

        for (Account account : list) {
            System.out.println(account);
        }

        transaction.commit();
    }

    @Test
    //SQLQuery对象测试
    public void demo8(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        String sql = "select * from account";
        SQLQuery sqlQuery = session.createSQLQuery(sql);
        List<Object[]> list = sqlQuery.list();

        for (Object[] o : list) {
            System.out.println(Arrays.toString(o));
        }


        transaction.commit();
    }
}
