package cn.csdas.yelf.day03;

import cn.csdas.yelf.day03.domain.Customer;
import cn.csdas.yelf.day03.domain.LinkMan;
import cn.csdas.yelf.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

/**
 * 一对多测试类
 */
public class HibernateDemo {
    @Test
    //保存2个客户和3个联系人
    public void demo1(){
        //创建session和开启事务
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        //创建两个客户
        Customer customer1 = new Customer();
        Customer customer2 = new Customer();
        customer1.setCust_id(1);
        customer2.setCust_id(2);
        customer1.setCust_name("张三");
        customer2.setCust_name("李四");

        //创建三个联系人
        LinkMan linkMan1 = new LinkMan();
        LinkMan linkMan2 = new LinkMan();
        LinkMan linkMan3 = new LinkMan();
        linkMan1.setLkm_id(1);
        linkMan2.setLkm_id(2);
        linkMan3.setLkm_id(3);
        linkMan1.setLkm_name("王五");
        linkMan2.setLkm_name("赵六");
        linkMan3.setLkm_name("王八");

        //设置关系
        linkMan1.setCustomer(customer1);
        linkMan2.setCustomer(customer2);
        linkMan3.setCustomer(customer2);
        customer1.getLinkMans().add(linkMan1);
        customer2.getLinkMans().add(linkMan2);
        customer2.getLinkMans().add(linkMan3);

        //保存数据
        session.save(customer1);
        session.save(customer2);
        session.save(linkMan1);
        session.save(linkMan2);
        session.save(linkMan3);

        transaction.commit();
    }

    @Test
    /*
     **测试一对多是否可以只保存一边
     */
    public void demo2(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        //构建对象
        Customer customer = new Customer();
        customer.setCust_name("yexuan");
        LinkMan linkMan = new LinkMan();
        linkMan.setLkm_name("xuanye");

        //建立联系
        customer.getLinkMans().add(linkMan);
        linkMan.setCustomer(customer);

        // 只保存一边是否可以：不可以，报一个瞬时对象异常：持久态对象关联了一个瞬时态对象。
        //session.save(customer);
        session.save(linkMan);

        transaction.commit();
    }

    @Test
    /**
    *  级联保存或更新操作：
    *  * 保存客户级联联系人，操作的主体是客户对象，需要在Customer.hbm.xml中进行配置*  * <set name="linkMans" cascade="save-update">
     */
    public void demo3(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        //构建对象
        Customer customer = new Customer();
        LinkMan linkMan = new LinkMan();

        customer.setCust_name("lang");
        linkMan.setLkm_name("xuan");

        //建立联系
        customer.getLinkMans().add(linkMan);
        linkMan.setCustomer(customer);


        session.save(customer);

        transaction.commit();
    }

    @Test
    /**
     *  级联保存或更新操作：
     *  * 保存联系人级联客户，操作的主体是联系人对象，需要在Linkman.hbm.xml中进行配置  cascade="save-update"*
     */
    public void demo4(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        //构建对象
        Customer customer = new Customer();
        customer.setCust_name("lin");
        LinkMan linkMan = new LinkMan();
        linkMan.setLkm_name("shi");

        //建立联系
        customer.getLinkMans().add(linkMan);
        linkMan.setCustomer(customer);

        session.save(linkMan);

        transaction.commit();
    }


    @Test
    /**
     * 测试对象的导航
     * * 前提：一对多的双方都设置cascade="save-update"
     */
    public void demo5(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        //构建对象
        Customer customer = new Customer();
        customer.setCust_name("sanmao");
        LinkMan linkMan1 = new LinkMan();
        linkMan1.setLkm_name("zugu");
        LinkMan linkMan2 = new LinkMan();
        linkMan2.setLkm_name("guodong");
        LinkMan linkMan3 = new LinkMan();
        linkMan3.setLkm_name("xiebo");

        //建立联系
        customer.getLinkMans().add(linkMan2);
        customer.getLinkMans().add(linkMan3);
        linkMan1.setCustomer(customer);

        session.save(linkMan1);//发送4条insert语句
        //session.save(customer);//发送3条insert语句
        //session.save(linkMan2);//发送1条insert语句

        transaction.commit();
    }

    @Test
    /**
     * 级联删除：
     * * 删除客户级联删除联系人，删除的主体是客户，需要在Customer.hbm.xml中配置
     * * <set name="linkMans" cascade="delete"
     */
    public void demo6(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        // 没有设置级联删除，默认情况:修改了联系人的外键为空值，删除客户
        //Customer customer = session.get(Customer.class,1);
        //session.delete(customer);

        // 删除客户，同时删除联系人
        Customer customer = session.get(Customer.class, 1);
        session.delete(customer);

        transaction.commit();
    }

    @Test
    /**
     * 级联删除：
     * * 删除联系人级联删除客户，删除的主体是联系人，需要在LinkMan.hbm.xml中配置
     * * <many-to-one name="customer" cascade="delete">
     */
    public void demo7(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        // 没有设置级联删除，默认情况:只删除当前联系人
        //LinkMan linkMan = session.get(LinkMan.class, 1);
        //session.delete(linkMan);

        LinkMan linkMan = session.get(LinkMan.class, 4);
        session.delete(linkMan);

        transaction.commit();
    }
}
