package Modele;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by arnold on 09/03/18.
 */
public class PersistenceManager {
    private EntityManagerFactory emf;
    private EntityManager em;

    public PersistenceManager (String unitName) {
        emf = Persistence.createEntityManagerFactory(unitName);
        em = emf.createEntityManager();
    }

    public void stop () {
        em.close();
        emf.close();
    }

    //@Transactional
    public void insert (Object o) {
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.persist(o);
        et.commit();
        em.flush();
        
    }

    /*public void delete (int key, int... oKeys) {
        if (oKeys.length == 0) {

        } else {

        }
    }*/

    public void delete (Object o) {
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.remove(em.contains(o) ? o : em.merge(o));
        et.commit();
        
    }

    public <T> List<T> getAll (Class<T> name) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(name);
        Root<T> rootEntry = cq.from(name);

        CriteriaQuery<T> all = cq.select(rootEntry);
        TypedQuery<T> allQuery = em.createQuery(all);
        return allQuery.getResultList();
    }

    public <T> T get (Class<T> name, int key, int... oKeys) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(name);
        Root<T> rootEntry = cq.from(name);

        CriteriaQuery<T> req = cq.where(rootEntry.get(name.getDeclaredFields()[0].getName()).in(key));
        for (int i = 1; i < oKeys.length; i++)
            req = cq.where(rootEntry.get(name.getDeclaredFields()[i].getName()).in(oKeys[i]));
        TypedQuery<T> query = em.createQuery(req);
        return query.getResultList().get(0);
    }

    public <T> T getByAttributes (Class<T> name, KeyValue... keyValues) throws Exception {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(name);
        Root<T> rootEntry = cq.from(name);

        CriteriaQuery<T> req = cq.where(rootEntry.get(keyValues[0].key).in(keyValues[0].value));
        for (int i = 1; i < keyValues.length; i++)
            req = cq.where(rootEntry.get(keyValues[i].key).in(keyValues[i].value));
        TypedQuery<T> query = em.createQuery(req);
        return query.getResultList().get(0);
    }

    public static class KeyValue {
        public String key;
        public Object value;

        public KeyValue(String key, Object value) {
            this.key = key;
            this.value = value;
        }
    }

}
