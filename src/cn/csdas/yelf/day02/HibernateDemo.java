package cn.csdas.yelf.day02;

import cn.csdas.yelf.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

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
    //测试持久化类
    public void demo2(){

    }
}
