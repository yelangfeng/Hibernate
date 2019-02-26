package cn.csdas.yelf.day02;

import cn.csdas.yelf.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import java.io.Serializable;

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
}
