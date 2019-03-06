package cn.csdas.yelf.day04;

import cn.csdas.yelf.domain.Customer;
import cn.csdas.yelf.domain.LinkMan;
import cn.csdas.yelf.utils.HibernateUtils;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import java.util.List;

/**
 * 延迟加载lazy（类、关联级别）和抓取策略fetch
 * @author yelf
 */
public class HibernateDemo4 {
    @Test
    /**
     * 类级别的延迟加载
     * * 在<class>的标签上配置的lazy
     */
    public void demo1(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction tx = session.beginTransaction();

        Customer customer = session.load(Customer.class, 10);
        Hibernate.initialize(customer);
        System.out.println(customer);

        tx.commit();
    }


/**************************关联级别的延迟加载(一的一方<Set>标签设置)*****************************************/
    @Test
    /**
     * 默认情况：
     */
    public void demo2(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction tx = session.beginTransaction();

        // 查询1号客户
        Customer customer = session.get(Customer.class, 1);// 发送一条查询客户的SQL
        System.out.println(customer.getCust_name());
        // 查看1号客户的每个联系人的信息
        for (LinkMan linkMan : customer.getLinkMans()) {// 发送一条根据客户ID查询联系人的SQL
            System.out.println(linkMan.getLkm_name());
        }
        tx.commit();
    }

    @Test
    /**
     * 设置fetch="select" lazy="true" 默认配置
     */
    public void demo3(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction tx = session.beginTransaction();

        // 查询1号客户
        Customer customer = session.get(Customer.class, 1l);// 发送一条查询客户的SQL
        System.out.println(customer.getCust_name());
        // 查看1号客户的每个联系人的信息
        for (LinkMan linkMan : customer.getLinkMans()) {// 发送一条根据客户ID查询联系人的SQL
            System.out.println(linkMan.getLkm_name());
        }
        tx.commit();
    }

    @Test
    /**
     * 设置	fetch="select" lazy="false"
     */
    public void demo4(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction tx = session.beginTransaction();

        // 查询3号客户
        Customer customer = session.get(Customer.class, 3);// 发送两条SQL语句：查询客户的名称，查询客户关联联系人
        System.out.println(customer.getCust_name());
		/*// 查看1号客户的每个联系人的信息
		for (LinkMan linkMan : customer.getLinkMans()) {//
			System.out.println(linkMan.getLkm_name());
		}*/

        System.out.println(customer.getLinkMans().size());
        tx.commit();
    }

    @Test
    /**
     * 设置fetch="select" lazy="extra"
     */
    public void demo5(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction tx = session.beginTransaction();

        // 查询1号客户
        Customer customer = session.get(Customer.class, 4);// 发送一条查询1号客户的SQL语句
        System.out.println(customer.getCust_name());

        System.out.println(customer.getLinkMans().size());// 发送一条select count() from ...;
        tx.commit();
    }

    @Test
    /**
     * 设置fetch="join" lazy=失效
     */
    public void demo6(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction tx = session.beginTransaction();

        // 查询1号客户
        Customer customer = session.get(Customer.class, 5);// 发送一条迫切左外连接查询记录
        System.out.println(customer.getCust_name());

        System.out.println(customer.getLinkMans().size());// 不发送
        tx.commit();
    }

    @SuppressWarnings("unchecked")
    @Test
    /**
     * 设置fetch="subselect" lazy="true"
     */
    public void demo7(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction tx = session.beginTransaction();

        List<Customer> list = session.createQuery("from Customer").list();// 发送查询所有客户的SQL
        for (Customer customer : list) {
            System.out.println(customer.getLinkMans().size());// 发送一条子查询
            System.out.println(customer.getCust_id());
        }

        tx.commit();
    }

    @SuppressWarnings("unchecked")
    @Test
    /**
     * 设置fetch="subselect" lazy="false"
     */
    public void demo8(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction tx = session.beginTransaction();

        List<Customer> list = session.createQuery("from Customer").list();// 发送查询所有客户的SQL，发送一条子查询
        for (Customer customer : list) {
            System.out.println(customer.getLinkMans().size());//
            System.out.println(customer.getCust_name());
        }

        tx.commit();
    }

    /**************************关联级别的延迟加载(多的一方<many-to-one>标签设置)*****************************************/

    @Test
    /**
     * 默认值
     */
    public void demo9(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction tx = session.beginTransaction();

        LinkMan linkMan = session.get(LinkMan.class, 1);// 发送一条查询联系人语句
        System.out.println(linkMan.getLkm_name());
        System.out.println(linkMan.getCustomer().getCust_name());// 发送一条select语句查询联系人所关联的客户

        tx.commit();
    }

    @Test
    /**
     * fetch="select" lazy="proxy"
     */
    public void demo10(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction tx = session.beginTransaction();

        LinkMan linkMan = session.get(LinkMan.class, 2);// 发送一条查询联系人语句
        System.out.println(linkMan.getLkm_name());
        System.out.println(linkMan.getCustomer().getCust_name());// 发送一条select语句查询联系人所关联的客户

        tx.commit();
    }

    @Test
    /**
     * fetch="select" lazy="false"
     */
    public void demo11(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction tx = session.beginTransaction();

        LinkMan linkMan = session.get(LinkMan.class, 3);// 发送一条查询联系人语句,发送一条select语句查询联系人所关联的客户
        System.out.println(linkMan.getLkm_name());
        System.out.println(linkMan.getCustomer().getCust_name());//

        tx.commit();
    }

    @Test
    /**
     * fetch="join" lazy=失效
     */
    public void demo12(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction tx = session.beginTransaction();

        LinkMan linkMan = session.get(LinkMan.class, 4);// 发送一条迫切左外连接查询联系人所关联的客户。
        System.out.println(linkMan.getLkm_name());
        System.out.println(linkMan.getCustomer().getCust_name());//

        tx.commit();
    }

/***************************** 批量抓取**************************************/

    @SuppressWarnings("unchecked")
    @Test
    /**
     * 获取客户的时候，批量抓取联系人
     * 在Customer.hbm.xml中set上配置batch-size
     */
    public void demo13(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction tx = session.beginTransaction();

        List<Customer> list = session.createQuery("from Customer").list();
        for (Customer customer : list) {
            System.out.println(customer.getCust_name());
            for (LinkMan linkMan : customer.getLinkMans()) {
                System.out.println(linkMan.getLkm_name());
            }
        }
        tx.commit();
    }

    @SuppressWarnings("unchecked")
    @Test
    /**
     * 获取联系人的时候，批量抓取客户
     * * 在Customer.hbm.xml中<class>上配置
     */
    public void demo14(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction tx = session.beginTransaction();

        List<LinkMan> list = session.createQuery("from LinkMan").list();
        for (LinkMan linkMan : list) {
            System.out.println(linkMan.getLkm_name());
            System.out.println(linkMan.getCustomer().getCust_name());
        }
        tx.commit();
    }
}
