package cn.csdas.yelf.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * hibernate的Session工具类
 *
 */
public class HibernateUtils {
    private static final Configuration cfg;
    private static final SessionFactory sf;

    static {
        cfg = new Configuration().configure();
        sf = cfg.buildSessionFactory();
    }

    /**
     * 常规获取Session对象
     * @return Session对象
     */
    public static Session openSession(){
        return sf.openSession();
    }

    /**
     * 获取线程绑定的Session对象
     * @return 线程绑定的Session对象
     */
    public static Session getCurrentSession(){
        return sf.getCurrentSession();
    }
}
