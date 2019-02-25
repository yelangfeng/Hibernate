package cn.csdas.yelf.day01;

import cn.csdas.yelf.utils.HibernateUtils;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class HibernateDemo {
    @Test
    public void demo1(){
        Session session = HibernateUtils.openSession();
        // 手动开启事务：
        Transaction transaction = session.beginTransaction();

        /*
        // 编写代码  增删改查
        Account account = new Account();
        account.setName("王八");
        account.setMoney(9999);
        //保存
        session.save(account);

        //查询
        // get方法，采用立即加载，执行到这行代码时，立即发送SQL语句至数据库
        Account account1 = session.get(Account.class,3);
        System.out.println(account1);
        // load方法，执行到这行代码的时候不发送SQL语句到数据库，使用到该对象时才发送SQL语句
        Account account2 = session.load(Account.class,2);
        System.out.println(account2);

        //查询对象集合
        //方式1 SQL语句
        SQLQuery query = session.createSQLQuery("select * from account");
        List<Object[]> list = query.list();
        for (Object[] objects : list) {
            System.out.println(Arrays.toString(objects));
        }
        //方式二 HQL语言
        Query query1 = session.createQuery("from Account");
        List<Account> list1 = query1.list();
        for (Account account5 : list1) {
            System.out.println(account5);
        }
        */

        //更新
        //方式1，直接进行更新
        /*
        Account account3 =  new Account();
        account3.setId(1);
        account3.setName("李四");
        session.update(account3);

        //方式二 先查询后更新（推荐）
        Account account3 = session.get(Account.class,2);
        account3.setName("赵武");
        session.update(account3);
        */

        //删除
        //方式1，直接进行删除
        /*
        Account account4 =  new Account();
        account4.setId(10);
        session.delete(account4);

        //方式二 先查询后删除（推荐）
        Account account4 = session.get(Account.class,5);
        session.delete(account4);
        */

        //保存或更新,对象主键为空执行保存，不为空执行更新，主键不为空也不存在，报错！
        //保存
        /*
        Account account6 = new Account();
        account6.setName("王小贱");
        account6.setMoney(100);
        session.saveOrUpdate(account6);
        //更新
        Account account6 = session.get(Account.class,28);
        account6.setMoney(1000000);
        session.saveOrUpdate(account6);
        */

        // 6.事务提交
        transaction.commit();
        // 7.资源释放
        session.close();
    }
}
