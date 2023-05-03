    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.programa05;

import java.util.List;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author btoarriola
 */
public class DAOEmpleado implements IDAOGeneral<Empleado, Long>{

    @Override
    public Empleado create(Empleado p) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction t = session.beginTransaction();
            session.save(p);
            t.commit();
        }
        return p;
    }

    @Override
    public boolean delete(Long id) {
        Session session = HibernateUtil.getSession();
        Transaction t = session.beginTransaction();
        Empleado empleado = FindById(id);
        if (empleado == null) {
            t.rollback();   //revertir los cambios realizados en la trans y volver al anterior.
            session.close();
            return false;
        } else {
            session.delete(empleado);
            t.commit();
            session.close();
            return true;
        }
    }


    @Override
    public Empleado update(Empleado p, Long id) {
        Session session = HibernateUtil.getSession();
        Transaction t= session.beginTransaction();
        Empleado empleado = FindById(id);
        if(empleado == null){
            t.rollback();   //revertir los cambios realizados en la trans y volver al anterior.
            session.close();
            return null;
        }else{
            empleado.setNombre(p.getNombre());
            empleado.setDireccion(p.getDireccion());
            empleado.setTelefono(p.getTelefono());

            session.update(empleado);
            t.commit();
            session.close();
            return empleado;
        }

    }

    @Override
    public List<Empleado> findAll() {
        List<Empleado> empleados;
        try (Session session = HibernateUtil.getSession()) {
            Transaction t = session.beginTransaction();
            String sql = "SELECT * FROM empleados";
            Query query = session.createNativeQuery(sql, Empleado.class);
            empleados = query.getResultList(); //List<Empleado> empleados = session.createQuery("from Empleado").list();
            t.commit();
        }
        return empleados;
    }


    @Override
    public Empleado FindById(Long id) {
        Empleado empleado;
        try (Session session = HibernateUtil.getSession()) {
            empleado = session.get(Empleado.class, id);
        }
        return empleado;
    }

    
}
